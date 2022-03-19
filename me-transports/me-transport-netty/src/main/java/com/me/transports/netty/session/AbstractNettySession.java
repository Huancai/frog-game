/**
 * game-me
 */
package com.me.transports.netty.session;


import com.me.transport.session.AbstractSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public abstract class AbstractNettySession extends AbstractSession {
    /**
     *
     */
    protected final Channel channel;

    /**
     * @param channel
     */
    public AbstractNettySession(Channel channel) {
        this.channel = channel;
        genSessionId();
    }


    /**
     * 发送数据[c:request,s:response]
     *
     * @param message
     */
    @Override
    public final void send(final Object message) {
        if (isActive()) {
            channel.writeAndFlush(message).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                    }
                }
            });
        }
    }

    @Override
    public boolean isActive() {
        return (null != channel && channel.isActive());
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public void shutdownGracefully() {
        if (isActive()) {
            channel.close();
        }
    }
}
