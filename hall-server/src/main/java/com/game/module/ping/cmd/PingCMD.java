package com.game.module.ping.cmd;

import com.game.core.cmd.AbstractCMD;
import com.game.module.misc.data.GamePlayer;
import com.game.module.ping.data.PongData;
import com.me.core.net.InCmd;
import com.me.core.net.OutCmd;
import com.me.metadata.pb.misc.MiscMsgProto;
import com.me.transport.Cmd;

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
