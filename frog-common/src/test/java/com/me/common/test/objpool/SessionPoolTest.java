package com.me.common.test.objpool;

import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public class SessionPoolTest {

    public void test() {
//        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//        poolConfig.setMaxTotal(3);
//        poolConfig.setMaxIdle(2);
//        poolConfig.setMinIdle(1);
//        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTimeBetweenEvictionRuns(Duration.ofSeconds(10L));
//        poolConfig.setMaxWait(Duration.ofSeconds(2L));

//        AbandonedConfig abandonedConfig = new AbandonedConfig();
//        abandonedConfig.setRemoveAbandonedOnMaintenance(true);
//        abandonedConfig.setUseUsageTracking(true);
//        abandonedConfig.setRemoveAbandonedTimeout(Duration.ofHours(1L)); //借出多长时间未还认为泄露
//        SessionPool pool = new SessionPool(poolConfig, new SessionFactory(new SessionConf("127.0.0.1", 8080, 5000)), abandonedConfig);
//        Session s = pool.borrowObject();
    }
}
