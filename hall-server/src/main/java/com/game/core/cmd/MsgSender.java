package com.game.core.cmd;

import com.me.transport.Message;
import com.me.transport.session.Session;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public final class MsgSender {


    /**
     * @param code
     * @param message
     * @param session
     */
    public static void send(short code, com.google.protobuf.GeneratedMessage message, Session session) {
        Message msg = Message.create(code, message.toByteArray());
        session.send(msg);
    }

    /**
     * @param code
     * @param builder
     * @param session
     */
    public static void send(short code, com.google.protobuf.GeneratedMessage.Builder<?> builder, Session session) {
        Message msg = Message.create(code, builder.build().toByteArray());
        session.send(msg);
    }

    /**
     * @param message
     * @param session
     */
    public static void send(Message message, Session session) {
        session.send(message);
    }


    private MsgSender() {
    }
}
