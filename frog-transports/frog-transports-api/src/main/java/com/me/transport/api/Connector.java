package com.me.transport.api;

import com.me.transport.api.event.IOEventListener;

import java.util.List;

/**
 * @author wu_hc
 * @mail whuancai@163.com
 */
public interface Connector {
    /**
     * 发起链接
     */
    boolean connect();

    /**
     * 重连
     */
    boolean reConnect();

    /**
     * 初始化
     */
    boolean doInit();

    /**
     * 登陆到目标服务器，主要用于校验连接合法性
     */
    boolean doLogin();

    /**
     * 发送请求
     *
     * @param message 消息
     */
    void send(final Object message);

    /**
     * 停止
     */
    void shutdownGracefully();

    /**
     * ip:port
     */
    String getConnectKey();

    /**
     *
     */
    boolean isActive();

    /**
     * 监听器
     */
    List<IOEventListener> listener();
}
