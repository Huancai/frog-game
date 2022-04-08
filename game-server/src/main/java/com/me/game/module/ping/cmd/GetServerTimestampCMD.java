package com.me.game.module.ping.cmd;

import com.me.common.net.Cmd;
import com.me.common.net.InCmd;
import com.me.common.net.OutCmd;
import com.me.game.common.cmd.AbstractCMD;
import com.me.game.module.misc.data.GamePlayer;
import com.me.metadata.pb.CommMsgProto;

import java.util.Objects;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Cmd(code = InCmd.TIMESTAMP, desc = "时间戳")
public final class GetServerTimestampCMD extends AbstractCMD<CommMsgProto.EmptyProto> {
    @Override
    protected void execute(GamePlayer player, CommMsgProto.EmptyProto message) {
        if (Objects.isNull(player)) {
            return;
        }

        CommMsgProto.ServerTimestampProto.Builder builder = CommMsgProto.ServerTimestampProto.newBuilder();
        builder.setTimestamp(System.currentTimeMillis());
        player.send(OutCmd.TIMESTAMP, builder);
    }
}
