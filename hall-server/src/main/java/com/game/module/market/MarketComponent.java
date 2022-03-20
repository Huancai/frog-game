package com.game.module.market;

import com.game.middleware.component.AbstractComponent;
import com.game.middleware.component.ComponentType;
import com.game.middleware.component.MeComponent;
import com.game.module.misc.data.GameUnit;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@MeComponent(type = ComponentType.MARKET, desc = "商店")
public class MarketComponent extends AbstractComponent {
    public MarketComponent(GameUnit owner) {
        super(owner);
    }

    @Override
    public void prepareData() {

    }
}
