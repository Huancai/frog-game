package com.game.module.recharge;

import com.game.middleware.component.AbstractComponent;
import com.game.middleware.component.ComponentType;
import com.game.middleware.component.MeComponent;
import com.game.module.misc.data.GameUnit;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@MeComponent(type = ComponentType.RECHARGE, desc = "充值")
public class RechargeComponent extends AbstractComponent {
    public RechargeComponent(GameUnit owner) {
        super(owner);
    }
}
