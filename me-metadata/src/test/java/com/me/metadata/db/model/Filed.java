package com.me.metadata.db.model;

import java.util.HashMap;
import java.util.Map;

public class Filed {

    static final Map<String, String> map = new HashMap<>();

    static {
        map.put("tinyint", "boolean");
        map.put("bool", "boolean");
        map.put("tinyint", "int");
        map.put("smallint", "int");

        map.put("bigint", "long");
        map.put("int", "int");
        map.put("varchar", "String");
        map.put("text", "String");
        map.put("char", "String");
        map.put("mediumtext", "String");
        map.put("longtext", "String");
        map.put("datetime", "java.util.Date");
        map.put("timestamp", "java.util.Date");
        map.put("blob", "byte[]");
        map.put("longblob", "byte[]");
        map.put("mediumblob", "byte[]");
        map.put("decimal", "java.math.BigDecimal");
    }

    private String name;
    private String comment;
    private String type;
    private boolean isPri;

    public String toJavaType() {
        String r = map.get(type);
        return null != r ? r : type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPri() {
        return isPri;
    }

    public void setPri(boolean pri) {
        isPri = pri;
    }
}
