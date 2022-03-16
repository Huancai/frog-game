package com.me.transports.netty;

import com.me.transport.Connector;
import com.me.transport.Message;
import com.me.transport.SessionKey;
import com.me.transport.event.IOCustomEvent;
import com.me.transport.event.IOEvent;
import com.me.transport.event.IOEventListener;
import com.me.transports.netty.session.NettyS2SSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * 消息处理器
 *
 * @author vincent.wu
 */
public final class NettyConnectorHandler extends SimpleChannelInboundHandler<Message> {

    final static AttributeKey<NettyS2SSession> key = AttributeKey.valueOf(SessionKey.connector_session);
    /**
     *
     */
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
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        NettyS2SSession session = new NettyS2SSession(ctx.channel());
        listener.onEvent(new IOCustomEvent(IOEvent.Event.REGISTERED, session));
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        listener.onEvent(new IOCustomEvent(IOEvent.Event.UNREGISTERED, getSession(ctx)));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.connector.reConnect();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    }

    /**
     * @param ctx
     * @return
     */
    private static NettyS2SSession getSession(ChannelHandlerContext ctx) {
        return ctx.channel().attr(key).get();
    }
}
