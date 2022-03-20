package com.me.pb;

import lombok.Data;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Data
public class PBField {

    private Type type;
    private String nam;
    private int seq;


    enum Type {
        OPTIONAL, REQUIRED, REPEATED;

        public static Type of(String sType) {
            for (Type value : Type.values()) {
                if (value.name().equalsIgnoreCase(sType)) {
                    return value;
                }
            }
            return null;
        }
    }
}
