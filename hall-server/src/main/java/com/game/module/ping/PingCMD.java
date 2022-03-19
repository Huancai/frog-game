package com.game.module.ping;

import com.game.core.cmd.AbstractCMD;
import com.game.module.misc.GamePlayer;
import com.me.core.net.InCmd;
import com.me.metadata.pb.misc.MiscMsgProto;
import com.me.transport.Cmd;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Cmd(code = InCmd.PING, desc = "PING")
public final class PingCMD extends AbstractCMD<MiscMsgProto.PingProto> {

    final static MiscMsgProto.PongProto.Builder builder = MiscMsgProto.PongProto.newBuilder();

    @Override
    protected void execute(GamePlayer player, MiscMsgProto.PingProto message) {
        player.send(builder);
    }
}
