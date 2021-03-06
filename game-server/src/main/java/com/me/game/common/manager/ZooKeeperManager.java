package com.me.game.common.manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.TreeCache;
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
//@Component
public class ZooKeeperManager implements InitializingBean {

    @Autowired
    Environment environment;
    /**
     * 客户端
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

        //4 建立一个cache缓存    Curator之nodeCache一次注册，N次监听
        final NodeCache cache = new NodeCache(client, curServerPath);
        cache.start(true);
        cache.getListenable().addListener(() -> {
            String data = new String(cache.getCurrentData().getData());

        });

        //5节点变化
        final TreeCache treeCache = new TreeCache(client, curServerPath);
        treeCache.getListenable().addListener((cf, event) -> {
            ChildData eventData = event.getData();
            switch (event.getType()) {
                case NODE_ADDED:
                    log.warn(curServerPath + "节点添加" + eventData.getPath() + "\t添加数据为：" + new String(eventData.getData()));
                    break;
                case NODE_UPDATED:
                    log.warn(eventData.getPath() + "节点数据更新\t更新数据为：" + new String(eventData.getData()) + "\t版本为：" + eventData.getStat().getVersion());
                    break;
                case NODE_REMOVED:
                    log.warn(eventData.getPath() + "节点被删除");
                    break;
                default:
                    break;
            }
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
