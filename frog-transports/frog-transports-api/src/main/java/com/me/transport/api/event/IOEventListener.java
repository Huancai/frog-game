package com.me.transport.api.event;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface IOEventListener {

    /**
     * 事件元数据传递
     *
     * @param ioEvent 网络事件
     */
    void onEvent(IOEvent ioEvent);

    /**
     * 拦截传递
     *
     * @param ioEvent
     */
    // void onIntercept(IOEvent ioEvent);
}
