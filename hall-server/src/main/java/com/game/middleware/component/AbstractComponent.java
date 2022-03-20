package com.game.middleware.component;


import com.game.module.misc.data.GameUnit;

/**
 * @author wu_hc【whuancai@163.com】
 */
public abstract class AbstractComponent {


    protected GameUnit owner;

    public AbstractComponent(GameUnit owner) {
        this.owner = owner;
    }

    /**
     * 准备数据
     */
    public void prepareData() {

    }

    /**
     * 准备数据后
     */
    public void afterPrepareData() {

    }

    /**
     * 拥有者
     *
     * @param <T>
     * @return
     */
    public <T extends GameUnit> T getOwner() {
        return (T) owner;
    }
}
