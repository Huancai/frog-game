package com.me.common.type;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public enum EServerType {

    ROUTER(1),
    GAME(2),
    ;

    private final int type;

    EServerType(int type) {
        this.type = type;
    }

    public static EServerType of(int type) {
        for (EServerType value : EServerType.values()) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }
}
