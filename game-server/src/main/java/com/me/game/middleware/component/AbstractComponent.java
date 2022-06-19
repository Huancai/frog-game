package com.me.game.middleware.component;


import com.me.game.module.misc.data.GameUnit;

/**
 * @author wu_hc【whuancai@163.com】
 */
public abstract class AbstractComponent {

    /**
     * 组件拥有者
     */
    protected GameUnit owner;


    /**
     * 序号
     */
    private int order;

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

    public void saveData() {

    }

    /**
     * 拥有者
     *
     * @param <T>
     * @return
     */
    public final <T extends GameUnit> T getOwner() {
        return (T) owner;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
