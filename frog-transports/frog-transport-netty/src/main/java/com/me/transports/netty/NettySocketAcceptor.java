package com.me.transports.netty;


import com.me.transport.AbstractSocketAcceptor;
import com.me.transports.netty.codec.NettyCodecFactory;
import com.me.transports.netty.session.NettyC2SSession;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;


/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
public class NettySocketAcceptor extends AbstractSocketAcceptor<NettyC2SSession> {

    /**
     * @param inetPort
     */
    public NettySocketAcceptor(int inetPort) {
        super(inetPort);
    }

    /**
     * 启动辅助器
     */
    protected ServerBootstrap bootstrap = null;

    @Override
    public void shutdownGracefully() {
        if (null != bootstrap) {
            bootstrap.config().childGroup().shutdownGracefully();
            bootstrap.config().group().shutdownGracefully();
        }
    }

    @Override
    public void doInit() {

//		checkArgument(ioEventListeners.isEmpty(), "Acceptor ioEventListeners CAN NOT be a nil value!!");

        ThreadFactory bossFactory = new DefaultThreadFactory("netty-boss", Thread.MAX_PRIORITY);
        ThreadFactory workerFactory = new DefaultThreadFactory("netty-worker", Thread.MAX_PRIORITY);

        bootstrap = new ServerBootstrap();
        bootstrap.group(initEventLoopGroup(1, bossFactory), initEventLoopGroup(workerCount(), workerFactory));
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(final SocketChannel ch) throws Exception {
                ch.pipeline().addLast("encoder", NettyCodecFactory.getEncoder(Boolean.FALSE));
                ch.pipeline().addLast("decoder", NettyCodecFactory.getDecoder(Boolean.FALSE));
                ch.pipeline().addLast("handler", new NettyAcceptorHandler(NettySocketAcceptor.this));
            }
        });
        initAcceptorOption(bootstrap);
    }

    @Override
    public void start(boolean sync) throws Exception {
//        checkNotNull(bootstrap, "You need to invoke doInit before!!");

        ChannelFuture future;
        if (StringUtil.isNullOrEmpty(this.inetHost)) {
            future = bootstrap.bind(inetPort).sync();
        } else {
            future = bootstrap.bind(inetHost, inetPort).sync();
        }

        if (sync)
            future.channel().closeFuture().sync();

        log.info("Server Acceptor bind in PORT:{}", inetPort);
    }

    /**
     * 初始化连接参数
     *
     * @param bootstrap
     */
    protected void initAcceptorOption(final ServerBootstrap bootstrap) {
        bootstrap.option(ChannelOption.SO_RCVBUF, 1024 * 64);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        bootstrap.childOption(ChannelOption.SO_RCVBUF, 1024 * 64);
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
    }


    protected int workerCount() {
        return Runtime.getRuntime().availableProcessors() << 1;
    }


    protected EventLoopGroup initEventLoopGroup(int nThreads, ThreadFactory factory) {
        return new NioEventLoopGroup(nThreads, factory);
    }

}
