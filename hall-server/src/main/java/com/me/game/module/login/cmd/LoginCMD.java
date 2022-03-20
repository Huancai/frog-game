package com.me.game.module.login.cmd;

import com.me.game.common.cmd.AbstractCMD;
import com.me.game.module.misc.data.GamePlayer;
import com.me.game.common.net.InCmd;
import com.me.metadata.pb.login.LoginMsgProto;
import com.me.game.common.net.Cmd;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Cmd(code = InCmd.LOGIN, desc = "登录请求")
public class LoginCMD extends AbstractCMD<LoginMsgProto.LoginReqProto> {
    @Override
    protected void execute(GamePlayer player, LoginMsgProto.LoginReqProto message) {

    }
}
