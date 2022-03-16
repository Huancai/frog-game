package com.me.transport.session;

/**
 * @author wu_hc
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


    enum SessionException {
        FREQUENT, // 访问过于频繁
        SEND_ERR, // 发送失败
        CHANNEL_NIL,
        ;
    }
}
