package com.me.transports.netty;

import com.me.transport.api.Message;
import com.me.transport.api.SessionKey;
import com.me.transport.api.event.IOCustomEvent;
import com.me.transport.api.event.IOEvent;
import com.me.transport.api.event.IOEventListener;
import com.me.transports.netty.session.NettyC2SSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public final class NettyAcceptorHandler extends SimpleChannelInboundHandler<Message> {

    final static AttributeKey<NettyC2SSession> key = AttributeKey.valueOf(SessionKey.acceptor_session);

    private final IOEventListener listener;

    public NettyAcceptorHandler(IOEventListener listener) {
        this.listener = listener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        listener.onEvent(new IOCustomEvent(IOEvent.Event.READ, getSession(ctx), message));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        NettyC2SSession session = new NettyC2SSession(ctx.channel());
        ctx.channel().attr(key).set(session);
        listener.onEvent(new IOCustomEvent(IOEvent.Event.REGISTERED, session));
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        listener.onEvent(new IOCustomEvent(IOEvent.Event.UNREGISTERED, getSession(ctx)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        listener.onEvent(new IOCustomEvent(IOEvent.Event.EXCEPTION, getSession(ctx), cause));
    }

    /**
     * @param ctx
     * @return
     */
    private static NettyC2SSession getSession(ChannelHandlerContext ctx) {
        return ctx.channel().attr(key).get();
    }
}
