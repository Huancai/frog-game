package com.game.module.bag.cmd;

import com.game.core.cmd.AbstractCMD;
import com.game.middleware.component.ComponentType;
import com.game.module.bag.BagComponent;
import com.game.module.misc.data.GamePlayer;
import com.google.protobuf.GeneratedMessage;

/**
 * @author wu_hc【whuancai@163.com】
 */
//@Cmd(code = 234, desc = "获取商品列表")
public final class GetBagItemListCMD extends AbstractCMD<GeneratedMessage> {

    @Override
    protected void execute(GamePlayer player, GeneratedMessage message) {

        BagComponent component = player.getComponent(ComponentType.BAG);
    }
}
