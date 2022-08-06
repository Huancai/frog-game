package com.me.game.common.cmd;

import com.me.common.net.Cmd;

/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
public final class CmderWrap {
    private final AbstractCMD cmder;
    private final Cmd cmdA;

    public CmderWrap(AbstractCMD cmder, Cmd cmdA) {
        this.cmder = cmder;
        this.cmdA = cmdA;
    }

    /**
     * @return
     */
    public boolean isAsync() {
        return this.cmdA.async();
    }

    public AbstractCMD getCmder() {
        return cmder;
    }

    public Cmd getCmdA() {
        return cmdA;
    }
}
