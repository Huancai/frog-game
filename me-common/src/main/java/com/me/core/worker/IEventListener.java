package com.me.core.worker;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface IEventListener {
    /**
     * @param event
     */
    void onEvent(final Object event);
}
