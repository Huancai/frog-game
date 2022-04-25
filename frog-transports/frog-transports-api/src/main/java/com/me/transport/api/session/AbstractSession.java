package com.me.transport.api.session;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public abstract class AbstractSession implements Session {

    private static final AtomicLong ID_GEN = new AtomicLong(10);
    protected long sessionId;
    private long lastInteractive;
    private long createTime;

    private AtomicLong writeAdder = new AtomicLong();
    private AtomicLong sendAdder = new AtomicLong();

    @Override
    public long getId() {
        return sessionId;
    }

    public final void init() {
        this.sessionId = ID_GEN.incrementAndGet();
        this.createTime = System.currentTimeMillis();
    }


    @Override
    public long getLastInteractive() {
        return lastInteractive;
    }

    @Override
    public void interactive() {
        this.lastInteractive = System.currentTimeMillis();
    }

    @Override
    public long getCreateTime() {
        return createTime;
    }

    public long increaseWrite(long size) {
        return writeAdder.addAndGet(size);
    }

    public long increaseSend(long size) {
        return sendAdder.addAndGet(size);
    }

    @Override
    public long sendBytes() {
        return sendAdder.get();
    }

    @Override
    public long writeBytes() {
        return writeAdder.get();
    }
}
