package com.game.module.battle;

import com.game.middleware.component.AbstractComponent;
import com.game.middleware.component.ComponentType;
import com.game.middleware.component.MeComponent;
import com.game.module.misc.data.GameUnit;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@MeComponent(type = ComponentType.BATTLE, desc = "战斗")
public class BattleComponent extends AbstractComponent {
    public BattleComponent(GameUnit owner) {
        super(owner);
    }
}
