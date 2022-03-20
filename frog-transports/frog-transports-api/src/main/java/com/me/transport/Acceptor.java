package com.me.transport;

import com.me.transport.event.IOEventListener;

import java.util.List;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface Acceptor {

    /**
     * @return
     */
    int boundPort();

    /**
     * 停止
     */
    void shutdownGracefully() throws InterruptedException;

    /**
     *
     */
    void doInit();

    /**
     * 启动
     */
    void start() throws Exception;

    /**
     * @param sync
     * @throws Exception
     */
    void start(boolean sync) throws Exception;

    List<IOEventListener> listener();
}
