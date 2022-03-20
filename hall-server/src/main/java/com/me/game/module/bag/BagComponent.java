package com.me.game.module.bag;

import com.me.game.middleware.component.AbstractComponent;
import com.me.game.middleware.component.ComponentType;
import com.me.game.middleware.component.MeComponent;
import com.me.game.module.misc.data.GameUnit;

/**
 * @author wu_hc【whuancai@163.com】
 */
@MeComponent(type = ComponentType.BAG, desc = "背包组件")
public class BagComponent extends AbstractComponent {

    public BagComponent(GameUnit owner) {
        super(owner);
    }

    @Override
    public void prepareData() {

    }
}
