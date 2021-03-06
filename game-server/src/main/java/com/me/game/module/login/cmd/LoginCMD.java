package com.me.game.module.login.cmd;

import com.me.common.net.Cmd;
import com.me.common.net.InCmd;
import com.me.game.common.cmd.AbstractCMD;
import com.me.game.common.manager.UserManager;
import com.me.game.module.misc.data.GamePlayer;
import com.me.metadata.pb.login.LoginMsgProto;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Cmd(code = InCmd.LOGIN, desc = "登录请求")
public class LoginCMD extends AbstractCMD<LoginMsgProto.LoginReqProto> {
    @Override
    protected void execute(GamePlayer player, LoginMsgProto.LoginReqProto message) {
        UserManager.getInst().onLogin(null, message.getUserName());
    }
}
