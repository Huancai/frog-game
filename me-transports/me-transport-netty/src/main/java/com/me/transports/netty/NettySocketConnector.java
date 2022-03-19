package com.me.transports.netty;


import com.me.transport.Connector;
import com.me.transport.event.IOEvent;
import com.me.transport.event.IOEventListener;
import com.me.transports.netty.codec.NettyCodecFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
public class NettySocketConnector implements Connector, IOEventListener {

    private Bootstrap bootstrap;
    private Channel channel;
    private final List<IOEventListener> ioEventListeners = new LinkedList<>();

    private boolean reConnect = true;

    private String host;
    private int port;

    @Override
    public boolean doInit() {
        Bootstrap b = new Bootstrap();
        b.group(new NioEventLoopGroup(1)).channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast("encoder", NettyCodecFactory.getEncoder(Boolean.FALSE));
                ch.pipeline().addLast("decoder", NettyCodecFactory.getDecoder(Boolean.FALSE));
                ch.pipeline().addLast("handler", new NettyConnectorHandler(NettySocketConnector.this, NettySocketConnector.this));
            }
        });
        bootstrap = b;
        return true;
    }

    @Override
    public boolean connect() {
        if (isActive())
            return true;
        try {
            ChannelFuture future = bootstrap.connect(host, port);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        channel = future.channel();
                        log.info("Connect to server[{}] successfully!", getConnectKey());
                    } else {
                        log.info("Failed to connect to server[{}], try connect after 5s", getConnectKey());
                        future.channel().eventLoop().schedule(new Runnable() {
                            @Override
                            public void run() {
                                reConnect();
                            }
                        }, 5, TimeUnit.SECONDS);
                    }
                }
            });
        } catch (Exception e) {
            log.error(String.format("连接服务器失败(IP:%s,PORT:%s)", host, port));
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean reConnect() {
        if (!reConnect)
            return false;
        return connect();
    }

    @Override
    public boolean isActive() {
        return (null != channel && channel.isActive());
    }

    @Override
    public void send(Object request) {
        if (isActive()) {
            channel.writeAndFlush(request);
        }
    }

    @Override
    public void shutdownGracefully() {
        if (null != bootstrap) {
            bootstrap.config().group().shutdownGracefully();
        }
    }

    @Override
    public String getConnectKey() {
        return String.format("%s:%d", host, port);
    }

    public Channel getChannel() {
        return this.channel;
    }

    public boolean isReConnect() {
        return reConnect;
    }


    public void setReConnect(boolean reConnect) {
        this.reConnect = reConnect;
    }


    @Override
    public boolean doLogin() {
        return true;
    }

    @Override
    public void onEvent(IOEvent ioEvent) {
        for (IOEventListener ioEventListener : ioEventListeners) {
            ioEventListener.onEvent(ioEvent);
        }
    }

    /**
     * @return
     */
    @Override
    public final List<IOEventListener> listener() {
        return this.ioEventListeners;
    }
}
