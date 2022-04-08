package com.me.game.common.manager;

import cn.hutool.core.thread.NamedThreadFactory;
import com.me.game.module.misc.data.GamePlayer;
import com.me.metadata.db.entity.PlayerEntity;
import com.me.metadata.db.mapper.PlayerMapper;
import com.me.transport.api.session.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
public class UserManager {

    public static final UserManager M = new UserManager();

    //调度线程
    private static ScheduledExecutorService DB_SCHEDULE = Executors.newScheduledThreadPool(1);

    //DB线程
    private static final Executor DB_EXECUTOR = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() << 1,
            Runtime.getRuntime().availableProcessors() << 1,
            5,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(1 << 16),
            new NamedThreadFactory("PLAYER-SAVE", false));

    private final Map<String, GamePlayer> players = new ConcurrentHashMap<>();

    public static UserManager getInst() {
        return M;
    }


    public void onLogin(Session session, String unionId) {
        GamePlayer gamePlayer = players.get(unionId);
        if (Objects.isNull(gamePlayer)) {
            PlayerEntity playerEntity = SpringManager.getBean(PlayerMapper.class).getOne(unionId);
            if (Objects.isNull(playerEntity)) {
                //send error msg to client
                return;
            }
            gamePlayer = new GamePlayer(playerEntity, session);
            gamePlayer.doLogin();
            players.put(unionId, gamePlayer);
        }
    }

    private UserManager() {

        DB_SCHEDULE.scheduleAtFixedRate(() -> {
            for (Map.Entry<String, GamePlayer> gamePlayerEntry : players.entrySet()) {
                GamePlayer gamePlayer = gamePlayerEntry.getValue();
                DB_EXECUTOR.execute(gamePlayer);
            }
        }, 3, 3, TimeUnit.MINUTES);
    }
}

