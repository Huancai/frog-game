package com.me.transports.netty.codec;

import com.me.transport.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


/**
 * @author wu_hc 【whuancai@163.com】
 */
final class NettyCiphertextMessageDecoder extends ByteToMessageDecoder {


    NettyCiphertextMessageDecoder() {

    }

    /**
     * 解密
     */
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }

        Message packet = Message.create(1);
        if (packet != null) {
            out.add(packet);
        }
        return;
    }

    /**
     * 获取当前加解密密钥
     *
     * @return
     */
    private int[] getKey(final ChannelHandlerContext ctx) {
        int[] key = ctx.channel().attr(NettyCodecFactory.DECRYPTION_KEY).get();
        return key;
    }
}