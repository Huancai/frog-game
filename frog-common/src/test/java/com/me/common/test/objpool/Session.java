package com.me.common.test.objpool;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface Session {
    boolean connect();

    boolean isActive();

    boolean close();
}
