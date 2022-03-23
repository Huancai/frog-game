package com.me.common.test.objpool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.Objects;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public class SessionFactory implements PooledObjectFactory<Session> {

    private SessionConf sessionConf;

    public SessionFactory(SessionConf sessionConf) {
        this.sessionConf = sessionConf;
    }

    @Override
    public void activateObject(PooledObject<Session> p) throws Exception {

    }

    @Override
    public void destroyObject(PooledObject<Session> p) throws Exception {
        Session session = p.getObject();
        if (Objects.nonNull(session)) {
            session.close();
        }
    }

    @Override
    public PooledObject<Session> makeObject() throws Exception {
        Session session = new NettySession(sessionConf.getHost(), sessionConf.getPort(), sessionConf.getTimeOut());
        if (!session.connect()) {
            throw new RuntimeException("connect error !!!");
        }
        return new DefaultPooledObject<>(session);
    }

    @Override
    public void passivateObject(PooledObject<Session> p) throws Exception {

    }

    @Override
    public boolean validateObject(PooledObject<Session> p) {
        return true;
    }
}
