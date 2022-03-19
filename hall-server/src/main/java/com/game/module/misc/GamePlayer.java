package com.game.module.misc;

import com.game.core.cmd.MsgSender;
import com.me.metadata.db.entity.PlayerEntity;
import com.me.transport.Message;
import com.me.transport.session.Session;

/**
 * @author wu_hc
 */
public class GamePlayer extends GameUnit {

    private Session session;

    private final PlayerEntity playerEntity;

    public GamePlayer(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void send(Message message) {
        MsgSender.send(message, session);
    }

    public void send(short code, com.google.protobuf.GeneratedMessage generatedMessage) {
        MsgSender.send(code, generatedMessage, session);
    }

    public void send(short code, com.google.protobuf.GeneratedMessage.Builder<?> builder) {
        MsgSender.send(code, builder, session);
    }

    public long getId() {
        return 0;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }
}
