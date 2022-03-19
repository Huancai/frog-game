package com.me.transport.session;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface Session {

    /**
     * 发送数据
     *
     * @param message
     */
    void send(final Object message);

    /**
     * @return
     */
    boolean isActive();

    /**
     * 关闭连接
     */
    void shutdownGracefully();


    /**
     * 会话ID
     *
     * @return
     */
    long getSessionId();

    enum SessionException {
        FREQUENT, // 访问过于频繁
        SEND_ERR, // 发送失败
        CHANNEL_NIL,
        ;
    }
}
