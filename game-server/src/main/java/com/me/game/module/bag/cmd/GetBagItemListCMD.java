package com.me.game.module.bag.cmd;

import com.me.game.common.cmd.AbstractCMD;
import com.me.game.middleware.component.ComponentType;
import com.me.game.module.bag.BagComponent;
import com.me.game.module.misc.data.GamePlayer;
import com.google.protobuf.GeneratedMessage;

/**
 * @author wu_hc【whuancai@163.com】
 */
//@Cmd(code = 234, desc = "获取商品列表")
public final class GetBagItemListCMD extends AbstractCMD<GeneratedMessage> {

    @Override
    protected void execute(GamePlayer player, GeneratedMessage message) {

        BagComponent component = player.getComponent(BagComponent.class);
    }
}
