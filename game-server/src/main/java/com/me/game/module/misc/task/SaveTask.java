package com.me.game.module.misc.task;

import com.me.game.common.manager.UserManager;
import com.me.game.module.misc.data.GamePlayer;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
public final class SaveTask implements Runnable {

    private final String wxUnionId;

    public SaveTask(String wxUnionId) {
        this.wxUnionId = wxUnionId;
    }

    @Override
    public void run() {
        GamePlayer gamePlayer = UserManager.getInst().getGamePlayer(this.wxUnionId);
        if (Objects.nonNull(gamePlayer)) {
            gamePlayer.doSaveDB();
        } else {
            log.info("GamePlayer doSave Task,but player not in cache >{}", this.wxUnionId);
        }
    }
}
