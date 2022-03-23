package com.me.common.objpool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.SwallowedExceptionListener;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
public final class PoolExceptionListener implements SwallowedExceptionListener {
    @Override
    public void onSwallowException(Exception e) {
        log.error("object pool error", e);
    }
}
