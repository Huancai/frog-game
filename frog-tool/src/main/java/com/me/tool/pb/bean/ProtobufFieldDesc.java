package com.me.tool.pb.bean;

import cn.hutool.core.util.StrUtil;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public class ProtobufFieldDesc {

    private String modifier;

    /**
     * 类型
     */
    private String type;

    private String fieldName;

    private int seqId;

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    @Override
    public String toString() {
        return StrUtil.format("{} {} {} = {};", modifier, type, fieldName, seqId);
    }

    enum Modifier {
        OPTIONAL, REQUIRED, REPEATED;

        public static Modifier of(String def) {
            for (Modifier value : Modifier.values()) {
                if(value.toString().equalsIgnoreCase(def)){
                    return value;
                }
            }

            return null;
        }
    }
}
