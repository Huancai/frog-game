/**
 * game-me
 */
package com.me.transports.netty.codec;

import com.me.transport.Message;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.AttributeKey;

/**
 * Netty 编码,解码器工厂.
 * 
 * @author wu_hc 【whuancai@163.com】
 */
public final class NettyCodecFactory {
	/**
	 * 解密密钥netty上下文属性
	 */
	static final AttributeKey<int[]> DECRYPTION_KEY = AttributeKey.valueOf("DECRYPTION_KEY");
	/**
	 * 加密密钥netty上下文属性
	 */
	static final AttributeKey<int[]> ENCRYPTION_KEY = AttributeKey.valueOf("ENCRYPTION_KEY");

	/**
	 * 
	 */
	private NettyCodecFactory() {

	}

	/**
	 * 获得编码器
	 */
	public static MessageToByteEncoder<Message> getEncoder(final Boolean isCiphertext) {
		MessageToByteEncoder<Message> encoder = null;
		if (isCiphertext) {
			encoder = new NettyCiphertextMessageEncoder();
		} else {
			encoder = new NettyPlaintextMessageEncoder();
		}
		return encoder;
	}

	/**
	 * 获得解码器
	 */
	public static ByteToMessageDecoder getDecoder(final Boolean isCiphertext) {
		ByteToMessageDecoder decoder = null;
		if (isCiphertext) {
			decoder = new NettyCiphertextMessageDecoder();
		} else {
			decoder = new NettyPlaintextMessageDecoder();
		}
		return decoder;
	}
}
