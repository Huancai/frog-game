package com.me.common.worker;

/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
public interface IEventListener {
    /**
     * @param event
     */
    void onEvent(final Object event);
}
