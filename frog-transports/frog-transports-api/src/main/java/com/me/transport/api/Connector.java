package com.me.transport.api;

import com.me.transport.api.event.IOEventListener;

import java.util.List;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface Connector {
    /**
     * 发起链接
     *
     * @return
     */
    boolean connect();

    /**
     * 重连
     *
     * @return
     */
    boolean reConnect();

    /**
     * 初始化
     *
     * @return
     */
    boolean doInit();

    /**
     * 登陆到目标服务器，主要用于校验连接合法性
     *
     * @return
     */
    boolean doLogin();

    /**
     * 发送请求
     *
     * @param message
     */
    void send(final Object message);

    /**
     * 停止
     */
    void shutdownGracefully();

    /**
     * ip:port
     *
     * @return
     */
    String getConnectKey();

    /**
     * @return
     */
    boolean isActive();

    List<IOEventListener> listener();
}
