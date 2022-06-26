package com.me.tool.pb.bean;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public class ProtobufMsgDesc {

    private String msgName;


    private String comment;
    private List<ProtobufFieldDesc> fieldDescList;

    public String getMsgName() {
        return msgName;
    }

    public void setMsgName(String msgName) {
        this.msgName = msgName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ProtobufFieldDesc> getFieldDescList() {
        return fieldDescList;
    }

    public void setFieldDescList(List<ProtobufFieldDesc> fieldDescList) {
        this.fieldDescList = fieldDescList;
    }

    public void appendField(ProtobufFieldDesc fieldDesc) {
        if (this.fieldDescList == null) {
            this.fieldDescList = new ArrayList<>();
        }
        this.fieldDescList.add(fieldDesc);
    }
    private boolean isEnum;

    public void setEnum(boolean anEnum) {
        isEnum = anEnum;
    }

    public boolean isEnum(){
        return false;
    }

    @Override
    public String toString() {
        return "ProtobufMsgDesc{" +
                "msgName='" + msgName + '\'' +
                ", comment='" + comment + '\'' +
                ", fieldDescList=" + fieldDescList +
                ", isEnum=" + isEnum +
                '}';
    }
}
