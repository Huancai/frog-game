package com.me.tool.db.model;

import lombok.Data;

import java.util.List;

@Data
public class Table {
    private String className;
    private String comment;
    private String tableName;
    private String dbName;
    private String packageName;
    private List<Filed> filedList;
}
