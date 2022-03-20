package com.me.transports.netty.codec;

import com.me.transport.api.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wu_hc 【whuancai@163.com】
 */
final class NettyCiphertextMessageEncoder extends MessageToByteEncoder<Message> {

    NettyCiphertextMessageEncoder() {

    }

    @Override
    protected void encode(final ChannelHandlerContext ctx, final Message message, final ByteBuf out) throws Exception {
        // 若存在不同线程给同一玩家发送数据的情况，因此加密过程需要同步处理
        byte[] plainText = message.toByteArray();
        // 获取key
        int[] encryptKey = getKey(ctx);
        // 加密过程
        byte[] cipherText = plainText;
        out.writeBytes(cipherText);
    }

    /**
     * 获取当前加解密密钥
     *
     * @return
     */
    private int[] getKey(final ChannelHandlerContext ctx) {
        int[] key = ctx.channel().attr(NettyCodecFactory.ENCRYPTION_KEY).get();
        return key;
    }
}