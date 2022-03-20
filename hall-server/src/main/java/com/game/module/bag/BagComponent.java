package com.game.module.bag;

import com.game.middleware.component.AbstractComponent;
import com.game.middleware.component.ComponentType;
import com.game.middleware.component.MeComponent;
import com.game.module.misc.data.GameUnit;

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
