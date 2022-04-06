package com.me.game.common.manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
@Component
public class ZooKeeperManager implements InitializingBean {

    @Autowired
    Environment environment;
    /**
     *
     */
    private CuratorFramework client;

    @Override
    public void afterPropertiesSet() throws Exception {

        final String curServerPath = environment.getProperty("curServerPath", "/hall/server");

        //1创建重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);

        //2创建zookeeper客户端
        client = CuratorFrameworkFactory.builder().connectString(environment.getProperty("ZOOKEEPER_SERVER"))
                .sessionTimeoutMs(10000)
                .retryPolicy(retryPolicy)
                .namespace("admin")
                .build();

        //3简历连接
        client.start();
        /**
         * 想要实现watch一次注册n次监听的话，我们需要使用到curator里的一个NodeCache对象。
         * 这个对象可以用来缓存节点数据，并且可以给节点添加nodeChange事件，当节点的数据发生变化就会触发这个事件
         */
        //4 建立一个cache缓存    Curator之nodeCache一次注册，N次监听
        final NodeCache cache = new NodeCache(client, curServerPath);
        cache.start(true);
        cache.getListenable().addListener(() -> {
            String data = new String(cache.getCurrentData().getData());

        });

        try {
            if (client.checkExists().forPath(curServerPath) == null) {

                client.create().creatingParentContainersIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(curServerPath);
                log.info("zookeeper初始化成功");

            }
        } catch (Exception e) {
            log.error("zookeeper初始化失败", e);
            e.printStackTrace();
        }
    }
}
