package com.me.game.common.manager;

import com.me.game.module.misc.data.GamePlayer;
import com.me.metadata.db.entity.PlayerEntity;
import com.me.metadata.db.mapper.PlayerMapper;
import com.me.transport.api.session.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
public class UserManager {

    public static final UserManager M = new UserManager();

    private final Map<String, GamePlayer> players = new ConcurrentHashMap<>();

    public static UserManager getInst() {
        return M;
    }


    public void onLogin(Session session, String unionId) {
        GamePlayer gamePlayer = players.get(unionId);
        if (Objects.isNull(gamePlayer)) {
            PlayerEntity one = SpringManager.getBean(PlayerMapper.class).getOne(unionId);
            if (Objects.isNull(one)) {
                //send error msg to client
                return;
            }
            gamePlayer = new GamePlayer(one, session);
            gamePlayer.onLogin();
            players.put(unionId, gamePlayer);
        }

    }

    private UserManager() {
    }
}

