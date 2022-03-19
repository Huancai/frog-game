package com.game.module.misc;

import com.me.transport.session.Session;

/**
 * 还未登录的连接
 *
 * @author wu_hc 【whuancai@163.com】
 */
public final class UnLoginSessionWrap {

    private final Session session;

    private long creatTime;

    public UnLoginSessionWrap(Session session) {
        this.session = session;
        this.creatTime = System.currentTimeMillis();
    }

    public Session getSession() {
        return session;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }
}
