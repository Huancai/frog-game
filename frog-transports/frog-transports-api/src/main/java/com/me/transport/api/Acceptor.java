package com.me.transport.api;

import com.me.transport.api.event.IOEventListener;

import java.util.List;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface Acceptor {

    /**
     * @return 绑定端口
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
     *
     */
    void start(boolean sync) throws Exception;

    List<IOEventListener> listener();
}
