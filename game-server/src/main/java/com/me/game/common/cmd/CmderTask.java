package com.me.game.common.cmd;

import com.me.game.common.manager.CmdManager;
import com.me.game.common.manager.SpringManager;
import com.me.game.module.misc.data.GamePlayer;
import com.me.transport.api.Message;

/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
public final class CmderTask implements Runnable {

    private final GamePlayer gamePlayer;
    private final AbstractCMD cmder;
    private final Message message;

    public CmderTask(GamePlayer gamePlayer, AbstractCMD cmder, Message message) {
        this.gamePlayer = gamePlayer;
        this.cmder = cmder;
        this.message = message;
    }

    @Override
    public void run() {
        SpringManager.getBean(CmdManager.class).doExecuteCMD_(cmder, gamePlayer, message);
    }
}
