package com.game.module.test.cmd;

import com.game.core.cmd.AbstractCMD;
import com.game.module.misc.GamePlayer;
import com.me.metadata.pb.test.TestMsgProto;
import com.me.transport.Cmd;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Cmd(code = 6666, desc = "这是一个测试的cmd")
public class TestCMD extends AbstractCMD<TestMsgProto.TestReqProto> {
    @Override
    protected void execute(GamePlayer player, TestMsgProto.TestReqProto message) {
        TestMsgProto.TestRspProto.Builder builder = TestMsgProto.TestRspProto.newBuilder();
        builder.setId(message.getId());
        builder.setMsg("ok");
        builder.setUserName(message.getUserName());
        builder.setPassWorld(message.getPassWorld());
        player.send(builder);
    }
}
