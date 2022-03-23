package com.me.common.test.objpool;

import com.me.common.objpool.Pool;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public class SessionPool extends Pool<Session> {
    public SessionPool(GenericObjectPoolConfig poolConfig, PooledObjectFactory<Session> factory) {
        super(poolConfig, factory);
    }

    public SessionPool(GenericObjectPoolConfig poolConfig, PooledObjectFactory<Session> factory, AbandonedConfig abandonedConfig) {
        super(poolConfig, factory, abandonedConfig);
    }
}
