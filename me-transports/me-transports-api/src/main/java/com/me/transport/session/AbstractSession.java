package com.me.transport.session;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public abstract class AbstractSession implements Session {

    private static final AtomicLong ID_GEN = new AtomicLong(10);
    protected long sessionId;

    @Override
    public long getSessionId() {
        return sessionId;
    }

    protected final void genSessionId() {
        this.sessionId = ID_GEN.incrementAndGet();
    }
}
