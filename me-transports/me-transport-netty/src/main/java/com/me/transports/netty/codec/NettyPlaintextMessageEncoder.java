package com.me.transports.netty.codec;

import com.me.transport.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wu_hc 【whuancai@163.com】
 */
final class NettyPlaintextMessageEncoder extends MessageToByteEncoder<Message> {
	/**
	 * 
	 */
	NettyPlaintextMessageEncoder() {
	}

	@Override
	protected void encode(final ChannelHandlerContext ctx, final Message message, final ByteBuf out) throws Exception {
		// 若存在不同线程给同一玩家发送数据的情况，因此加密过程需要同步处理
		byte[] plainText = message.toByteArray();
		out.writeBytes(plainText);
	}
}