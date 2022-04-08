package com.me.pb;

import lombok.Data;

import java.util.List;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Data
public class PBMessage {
    private String name;
    private List<PBField> filedList;
}
