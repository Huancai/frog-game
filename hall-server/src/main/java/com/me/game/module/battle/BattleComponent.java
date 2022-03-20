package com.me.game.module.battle;

import com.me.game.middleware.component.AbstractComponent;
import com.me.game.middleware.component.ComponentType;
import com.me.game.middleware.component.MeComponent;
import com.me.game.module.misc.data.GameUnit;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@MeComponent(type = ComponentType.BATTLE, desc = "战斗")
public class BattleComponent extends AbstractComponent {
    public BattleComponent(GameUnit owner) {
        super(owner);
    }
}
