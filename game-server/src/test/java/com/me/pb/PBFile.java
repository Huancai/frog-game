package com.me.pb;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public class PBFile {
    public final Map<String, String> optionMap = new HashMap<>();
    public final Map<String, PBMessage> msgMap = new HashMap<>();
}
