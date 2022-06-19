package com.me.game.module.test.cmd;

import com.me.common.net.Cmd;
import com.me.common.net.InCmd;
import com.me.common.net.OutCmd;
import com.me.game.common.cmd.AbstractCMD;
import com.me.game.module.misc.data.GamePlayer;
import com.me.game.module.test.TestComponent;
import com.me.game.module.test.data.TestData;
import com.me.metadata.pb.test.TestMsgProto;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Cmd(code = InCmd.TEST, desc = "这是一个测试的cmd")
public class TestCMD extends AbstractCMD<TestMsgProto.TestReqProto> {
    @Override
    protected void execute(GamePlayer player, TestMsgProto.TestReqProto message) {

        TestComponent testComponent = player.getComponent(TestComponent.class);
        TestData testData = testComponent.doTest();

        TestMsgProto.TestRspProto.Builder builder = TestMsgProto.TestRspProto.newBuilder();
        builder.setId(testData.getId());
        builder.setMsg(testData.getValue());
        builder.setUserName(message.getUserName());
        builder.setPassWorld(message.getPassWorld());
        player.send(OutCmd.TEST, builder);
    }

}
