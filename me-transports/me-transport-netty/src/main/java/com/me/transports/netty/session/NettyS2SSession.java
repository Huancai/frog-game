package com.me.transports.netty.session;

import io.netty.channel.Channel;

/**
 * 服务器与服务器之间的连接
 *
 * @author wu_hc
 */
public final class NettyS2SSession extends AbstractNettySession {

    /**
     * @param channel
     */
    public NettyS2SSession(Channel channel) {
        super(channel);
    }

}
