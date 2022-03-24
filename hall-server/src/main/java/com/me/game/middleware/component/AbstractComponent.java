package com.me.game.middleware.component;


import com.me.game.module.misc.data.GameUnit;
import com.me.metadata.db.BaseEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wu_hc【whuancai@163.com】
 */
public abstract class AbstractComponent {


    protected GameUnit owner;

    private final List<BaseEntity> updateEntityList = new LinkedList<>();

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

    protected final void trust(BaseEntity entity) {
        if (entity.isUpdate()) {
            updateEntityList.add(entity);
        }
    }

    public void prepareSaveData() {

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
}
