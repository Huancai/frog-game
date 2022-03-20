package com.me.game.module.ping.cmd;

import com.me.game.common.cmd.AbstractCMD;
import com.me.game.module.misc.data.GamePlayer;
import com.me.game.module.ping.data.PongData;
import com.me.game.common.net.InCmd;
import com.me.game.common.net.OutCmd;
import com.me.metadata.pb.misc.MiscMsgProto;
import com.me.game.common.net.Cmd;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Cmd(code = InCmd.PING, desc = "PING")
public final class PingCMD extends AbstractCMD<MiscMsgProto.PingProto> {

    @Override
    protected void execute(GamePlayer player, MiscMsgProto.PingProto message) {
        player.send(OutCmd.PONG, PongData.PONG_BUILDER);
    }
}
