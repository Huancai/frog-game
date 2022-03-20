package com.game.module.login.cmd;

import com.game.core.cmd.AbstractCMD;
import com.game.module.misc.data.GamePlayer;
import com.me.core.net.InCmd;
import com.me.metadata.pb.login.LoginMsgProto;
import com.me.transport.Cmd;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Cmd(code = InCmd.LOGIN, desc = "登录请求")
public class LoginCMD extends AbstractCMD<LoginMsgProto.LoginReqProto> {
    @Override
    protected void execute(GamePlayer player, LoginMsgProto.LoginReqProto message) {

    }
}
