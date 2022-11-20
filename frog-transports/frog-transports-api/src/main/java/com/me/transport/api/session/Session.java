package com.me.transport.api.session;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface Session {

    /**
     * 发送数据
     *
     * @param message 消息包
     */
    void send(final Object message);

    /**
     * @return 是否存货
     */
    boolean isActive();

    /**
     * 关闭连接
     */
    void close();


    /**
     *
     * @return 会话ID
     */
    long getId();

    /**
     *
     * @return 会话创建时间
     */
    long getCreateTime();

    /**
     *
     * @return 上一次交互
     */
    long getLastInteractive();

    void interactive();

    long sendBytes();

    long writeBytes();
}
