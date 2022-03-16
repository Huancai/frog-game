package com.me.transports.netty;


import com.me.transports.netty.codec.NettyCodecFactory;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

import java.nio.channels.Channel;
import java.util.concurrent.TimeUnit;


/**
 * @author wu_hc
 */
public class NettySocketConnector implements Connector, IOEventListener {


    /**
     *
     */
    private Bootstrap bootstrap;

    /**
     *
     */
    private Channel channel;


    /**
     * 网络事件事件监听者
     */
    private final List<IOEventListener> ioEventListeners = Lists.newArrayList();


    /**
     * 是否需要重连
     */
    private boolean reConnect = true;

    private UnresolvedAddress address;

    @Override
    public boolean doInit() {

//		checkArgument(ioEventListeners.isEmpty(), "Connector ioEventListeners CAN NOT be a nil value!!");

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
            ChannelFuture future = bootstrap.connect(address.getHost(), address.getPort());
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        channel = future.channel();
                        log.info("Connect to server[{}] successfully!", address.toString());
                    } else {
                        log.info("Failed to connect to server[{}], try connect after 5s", address.toString());
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
            log.error(String.format("连接服务器失败(IP:%s,PORT:%s)", address.getHost(), address.getPort()));
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
        return this.address.toString();
    }

    public Channel getChannel() {
        return this.channel;
    }

    /**
     * @return the reConnect
     */
    public boolean isReConnect() {
        return reConnect;
    }

    /**
     * @param reConnect the reConnect to set
     */
    public void setReConnect(boolean reConnect) {
        this.reConnect = reConnect;
    }


    @Override
    public boolean doLogin() {
        return true;
    }

    @Override
    public void onEvent(IOEvent ioEvent) {
        for (int i = 0; i < ioEventListeners.size(); i++) {
            ioEventListeners.get(i).onEvent(ioEvent);
        }
    }

    /**
     * @return
     */
    public final List<IOEventListener> listener() {
        return this.ioEventListeners;
    }
}
