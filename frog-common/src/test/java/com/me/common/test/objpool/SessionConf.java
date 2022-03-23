package com.me.common.test.objpool;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Data
@AllArgsConstructor
public class SessionConf {
    private String host;
    private int port;
    private int timeOut;
}
