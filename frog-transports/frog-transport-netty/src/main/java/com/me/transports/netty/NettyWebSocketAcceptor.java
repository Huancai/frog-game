//package com.me.transports.netty;
//
//import com.me.transports.netty.codec.NettyCodecFactory;
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpServerCodec;
//import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
//import io.netty.handler.logging.LogLevel;
//import io.netty.handler.logging.LoggingHandler;
//import io.netty.util.concurrent.DefaultThreadFactory;
//
//import java.util.concurrent.ThreadFactory;
//
//
//public final class NettyWebSocketAcceptor extends NettySocketAcceptor {
//
//    /**
//     * @param inetPort
//     */
//    public NettyWebSocketAcceptor(int inetPort) {
//        super(inetPort);
//    }
//
//    @Override
//    public void doInit() {
//
////		checkArgument(ioEventListeners.isEmpty(), "Acceptor ioEventListeners CAN NOT be a nil value!!");
//
//        ThreadFactory bossFactory = new DefaultThreadFactory("socket-acceptor-boss", Thread.MAX_PRIORITY);
//        ThreadFactory workerFactory = new DefaultThreadFactory("socket-acceptor-worker", Thread.MAX_PRIORITY);
//
//        bootstrap = new ServerBootstrap();
//        bootstrap.group(initEventLoopGroup(1, bossFactory), initEventLoopGroup(workerCount(), workerFactory));
//        bootstrap.channel(NioServerSocketChannel.class);
//        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
//            @Override
//            protected void initChannel(final SocketChannel ch) throws Exception {
//
//                ch.pipeline().addFirst(new LoggingHandler(LogLevel.INFO));
//                ch.pipeline().addLast(new HttpServerCodec()); // 将请求和应答消息编码或解码为HTTP消息
//                ch.pipeline().addLast(new HttpObjectAggregator(65536)); // 将HTTP消息的多个部分组合成一条完整的HTTP消息
//                // p.addLast(new WebSocketServerCompressionHandler());
//                ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", null, true)); // Netty支持websocket
//                ch.pipeline().addLast(new MyWebSocketServerHandler()); // websocket消息帧处理看下面代码(这里需要把前台的消息分类，判断传过来的是websocket哪个帧，如果为二进制帧往下传值，让protobuf解码)
//
//                ch.pipeline().addLast("encoder", NettyCodecFactory.getEncoder(Boolean.FALSE));
//                ch.pipeline().addLast("decoder", NettyCodecFactory.getDecoder(Boolean.FALSE));
//                ch.pipeline().addLast("handler", new NettyAcceptorHandler(NettyWebSocketAcceptor.this));
//            }
//        });
//        initAcceptorOption(bootstrap);
//    }
//
//}
