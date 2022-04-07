package com.me.common.bit;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public enum ESettings implements IndexStatus {

    /**
     * 冻结
     */
    FREEZE(0),

    /**
     * 审核
     */
    EXAMINED(1),

    /**
     * 隐藏成员列表
     */
    CONCEAL_MEMBER(2),


    ;

    // [0,64)
    private final int index;

    ESettings(int index) {
        this.index = index;
    }

    @Override
    public int index() {
        return index;
    }

    /**
     *
     */
    public static ESettings valueOf(int index) {
        for (ESettings type : ESettings.values()) {
            if (index == type.index()) {
                return type;
            }
        }
        return null;
    }

}
