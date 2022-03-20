package com.me.metadata.db;

import java.io.Serializable;

/**
 * @author wu_hc date:2021/3/12 17:59
 */
public abstract class BaseEntity implements Serializable, Cloneable {


    private static final long serialVersionUID = 1L;

    /**
     * 是否有更新
     */
    private volatile boolean isUpdate;


    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
