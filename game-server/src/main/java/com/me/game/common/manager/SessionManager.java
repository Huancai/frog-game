package com.me.game.common.manager;

import com.me.game.module.misc.data.GamePlayer;
import com.me.transport.api.session.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
public class SessionManager {

    private static final Map<Long, GamePlayer> players = new ConcurrentHashMap<>();
    private static final Map<Long, Session> allSession = new ConcurrentHashMap<>();

    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

    private static SessionManager M = new SessionManager();

    public static SessionManager getInstance() {
        return M;
    }

    private SessionManager() {
        scheduledThreadPool.scheduleAtFixedRate(() -> {

        }, 60, 10, TimeUnit.SECONDS);
    }

    public void sessionCreated(Session session) {
        allSession.put(session.getId(), session);
    }

    public void sessionFree(Session session) {
        Session remove = allSession.remove(session.getId());
        if (Objects.nonNull(remove)) {

        }
    }
}
