
package com.me.common.event;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface IVoteEventListener {
    /**
     * 事件触发时的回调。
     *
     * @param event 事件。
     */
    boolean onVoteEvent(Event event);
}
