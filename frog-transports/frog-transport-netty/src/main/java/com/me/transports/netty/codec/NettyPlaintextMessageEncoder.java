package com.me.transports.netty.codec;

import com.me.transport.api.Message;
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
		byte[] plainText = message.toByteArray();
		out.writeBytes(plainText);
	}
}