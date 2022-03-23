package com.me.common.objpool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.Closeable;
import java.util.Objects;

/**
 * 对象池抽象类，基于apache commons pool2，参考jedis pool的源码实现
 *
 * @author wu_hc 【whuancai@163.com】
 */
public abstract class Pool<T> implements Closeable {

    protected GenericObjectPool<T> internalPool;

    public Pool(GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory) {
        this(poolConfig, factory, null);
    }

    public Pool(GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory, AbandonedConfig abandonedConfig) {
        this.initPool(poolConfig, factory, abandonedConfig);
    }

    private void initPool(GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory, AbandonedConfig abandonedConfig) {
        if (Objects.nonNull(this.internalPool)) {
            close();
        }

        this.internalPool = new GenericObjectPool<>(factory, poolConfig, abandonedConfig);
        this.internalPool.setSwallowedExceptionListener(new PoolExceptionListener());
    }

    public T borrowObject() {
        try {
            return internalPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void returnObject(T session) {
        this.internalPool.returnObject(session);
    }

    @Override
    public void close() {
        this.internalPool.close();
    }
}
