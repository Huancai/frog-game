
package com.me.common.event;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface IEventListener {
    /**
     * 事件触发时的回调。
     *
     * @param event 事件。
     */
    void onEvent(Event event);
}
