package com.me.transports.netty;

import com.me.transport.api.Connector;
import com.me.transport.api.Message;
import com.me.transport.api.SessionKey;
import com.me.transport.api.event.IOCustomEvent;
import com.me.transport.api.event.IOEvent;
import com.me.transport.api.event.IOEventListener;
import com.me.transports.netty.session.NettyS2SSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public final class NettyConnectorHandler extends SimpleChannelInboundHandler<Message> {

    final static AttributeKey<NettyS2SSession> key = AttributeKey.valueOf(SessionKey.connector_session);
    private final IOEventListener listener;
    private final Connector connector;

    public NettyConnectorHandler(IOEventListener listener, Connector connector) {
        this.listener = listener;
        this.connector = connector;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        listener.onEvent(new IOCustomEvent(IOEvent.Event.READ, getSession(ctx), message));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        listener.onEvent(new IOCustomEvent(IOEvent.Event.EXCEPTION, getSession(ctx)));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyS2SSession session = new NettyS2SSession(ctx.channel());
        listener.onEvent(new IOCustomEvent(IOEvent.Event.ACTIVE, session));
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        listener.onEvent(new IOCustomEvent(IOEvent.Event.INACTIVE, getSession(ctx)));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.connector.reConnect();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    }


    private static NettyS2SSession getSession(ChannelHandlerContext ctx) {
        return ctx.channel().attr(key).get();
    }
}
