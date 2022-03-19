package com.game.module.misc;

import com.me.transport.session.Session;

import java.util.Objects;

/**
 * @author wu_hc
 */
public class GamePlayer extends GameUnit {

    private Session session;

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
}
