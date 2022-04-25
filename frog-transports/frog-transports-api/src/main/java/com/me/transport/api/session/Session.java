package com.me.transport.api.session;

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
    void close();


    /**
     * 会话ID
     *
     * @return
     */
    long getId();

    /**
     * 创建时间
     *
     * @return
     */
    long getCreateTime();

    /**
     * 上一次交互
     *
     * @return
     */
    long getLastInteractive();

    void interactive();

    long sendBytes();

    long writeBytes();
}
