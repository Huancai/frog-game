package com.game.module.bag.cmd;

import com.game.core.cmd.AbstractCMD;
import com.game.module.misc.GamePlayer;
import com.google.protobuf.GeneratedMessage;
import com.me.transport.Cmd;

/**
 * @author wu_hc【whuancai@163.com】
 */
@Cmd(code = 234, desc = "获取商品列表")
public final class GetBagItemListCMD extends AbstractCMD<GeneratedMessage> {

    @Override
    protected void execute(GamePlayer player, GeneratedMessage message) {

    }
}
