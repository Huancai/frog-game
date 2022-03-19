package com.game.core.manager;

import com.game.module.misc.GamePlayer;
import com.game.module.misc.UnLoginSessionWrap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public class SessionManager {

    private static final Map<Long, GamePlayer> players = new ConcurrentHashMap<>();
    private static final Map<Long, UnLoginSessionWrap> sessions = new ConcurrentHashMap<>();

    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

    public static SessionManager M = new SessionManager();

    public static SessionManager getInstance() {
        return M;
    }


    private SessionManager() {
        scheduledThreadPool.scheduleAtFixedRate(() -> {

        }, 60, 10, TimeUnit.SECONDS);
    }

    /**
     * @param gamePlayer
     */
    public void login(GamePlayer gamePlayer) {
    }

    /**
     * 建立连接，未完成登录
     *
     * @param wrap
     */
    public void unLoginCache(UnLoginSessionWrap wrap) {
        sessions.put(wrap.getSession().getSessionId(), wrap);
    }

}
