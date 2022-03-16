/**
 * game-me
 */
package com.me.transports.netty.session;


import com.me.transport.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * @author wu_hc <b> Jul 23, 20175:56:31 PM </b>
 */
public abstract class AbstractNettySession implements Session {
    /**
     *
     */
    protected final Channel channel;

    /**
     * @param channel
     */
    public AbstractNettySession(Channel channel) {
        this.channel = channel;
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
