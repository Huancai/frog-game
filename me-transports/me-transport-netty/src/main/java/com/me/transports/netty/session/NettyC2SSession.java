package com.me.transports.netty.session;

import io.netty.channel.Channel;

/**
 * 客户端与服务器间的会话
 *
 * @author wu_hc
 */
public final class NettyC2SSession extends AbstractNettySession {

    /**
     * @param channel
     */
    public NettyC2SSession(Channel channel) {
        super(channel);
    }


}
