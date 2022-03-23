package com.me.common.test.objpool;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Data
@AllArgsConstructor
public class NettySession implements Session {
    private String host;
    private int port;
    private int timeOut;

    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean close() {
        return false;
    }
}
