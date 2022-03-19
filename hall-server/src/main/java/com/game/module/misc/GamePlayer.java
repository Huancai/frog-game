package com.game.module.misc;

import com.me.metadata.db.entity.PlayerEntity;
import com.me.transport.session.Session;

import java.util.Objects;

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

    public void send(final Object message) {
        if (Objects.isNull(this.session)) {
            return;
        }

        this.session.send(message);
    }

    public long getId() {
        return 0;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }
}
