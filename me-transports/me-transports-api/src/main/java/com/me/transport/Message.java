
package com.me.transport;

import java.nio.ByteBuffer;
import java.util.Arrays;

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
    private static final ThreadLocal<ByteBuffer> bytedBuffer = ThreadLocal.withInitial(() -> ByteBuffer.allocate(Short.MAX_VALUE));

    /**
     * @param command
     */
    private Message(int command) {
        this.command = (short) command;
    }

    /**
     * @param command
     * @param body
     */
    private Message(int command, byte[] body) {
        this.command = (short) command;
        this.body = body;
    }

    /**
     * @return
     */
    public static Message create(final int command) {
        if (command < Short.MIN_VALUE || command > Short.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("command illegal[%s]", command));
        }
        return new Message(command);
    }

    /**
     * @return
     */
    public static Message create(final int command, byte[] body) {
        if (command < Short.MIN_VALUE || command > Short.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("command illegal[%s]", command));
        }
        return new Message(command, body);
    }


    /**
     * @return
     */
    public byte[] toByteArray() {
        ByteBuffer buffer = bytedBuffer.get();
        try {
            buffer.putInt(command);
            buffer.put(body);
            buffer.flip();
            return buffer.array();
        } finally {
            buffer.clear();
        }
    }

    /**
     * @return
     */
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
