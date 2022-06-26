package com.me.tool.pb.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public class ProtoBufDesc {

    private Map<String, String> optionMap = new HashMap<>();
    private List<String> importList = new ArrayList<>();

    private List<ProtobufMsgDesc> msgDescList = new ArrayList<>();

    public Map<String, String> getOptionMap() {

        return optionMap;
    }

    public void setOptionMap(Map<String, String> optionMap) {
        this.optionMap = optionMap;
    }

    public List<String> getImportList() {
        return importList;
    }

    public void setImportList(List<String> importList) {
        this.importList = importList;
    }

    public List<ProtobufMsgDesc> getMsgDescList() {
        return msgDescList;
    }

    public void setMsgDescList(List<ProtobufMsgDesc> msgDescList) {
        this.msgDescList = msgDescList;
    }
}
