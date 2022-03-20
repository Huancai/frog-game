package com.me.transports.netty.codec;

import com.me.transport.api.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author wu_hc 【whuancai@163.com】
 */
final class NettyPlaintextMessageDecoder extends ByteToMessageDecoder {

    NettyPlaintextMessageDecoder() {

    }

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception {
        if (in.readableBytes() < Message.HEAD_SIZE) {
            return;
        }
        in.markReaderIndex();
        short cmd = in.readShort();
        short length = in.readShort();
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        byte[] body = null;
        if (length > 0) {
            body = new byte[length];
            in.readBytes(body);
        }
        Message message = Message.create(cmd);
        message.setBody(body);
        out.add(message);
    }
}