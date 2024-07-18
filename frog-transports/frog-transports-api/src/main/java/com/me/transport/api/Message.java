
package com.me.transport.api;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

/**
 * 消息载体
 *
 * @author wu_hc 【whuancai@163.com】
 */
public final class Message {

    public static final int HEAD_SIZE = 4;

    /**
     * 协议码
     */
    private final short command;

    /**
     * 包体字节流
     */
    private byte[] body;

    /**
     * 重复利用
     */
    private static final ThreadLocal<ByteBuffer> byteBuffer = ThreadLocal.withInitial(() -> ByteBuffer.allocate(Short.MAX_VALUE));

    private Message(int command) {
        this.command = (short) command;
    }


    private Message(int command, byte[] body) {
        this.command = (short) command;
        this.body = body;
    }

    public static Message create(final int command) {
        if (command < Short.MIN_VALUE || command > Short.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("command illegal[%s]", command));
        }
        return new Message(command);
    }


    public static Message create(final int command, byte[] body) {
        if (command < Short.MIN_VALUE || command > Short.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("command illegal[%s]", command));
        }
        return new Message(command, body);
    }


    public byte[] toByteArray() {
        ByteBuffer buffer = byteBuffer.get();
        try {
            buffer.putShort(command);
            if (Objects.nonNull(body)) {
                buffer.putShort((short) body.length);
                buffer.put(body);
            } else {
                buffer.putShort((short) 0);
            }
            buffer.flip();
            return buffer.array();
        } finally {
            buffer.clear();
        }
    }


    public short command() {
        return this.command;
    }

    public byte[] body() {
        return this.body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "command=" + command +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
