package com.me.game.module.market;

import com.me.game.middleware.component.AbstractComponent;
import com.me.game.middleware.component.ComponentType;
import com.me.game.middleware.component.MeComponent;
import com.me.game.module.misc.data.GameUnit;

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
