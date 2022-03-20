package com.me.transport.api.event;


import com.me.transport.api.session.Session;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface IOEvent {

    /**
     * IO事件
     *
     * @return
     */
    Event event();

    /**
     * @return
     */
    Session session();

    /**
     * @return
     */
    Object attachment();

    enum Event {
        READ, REGISTERED, UNREGISTERED, EXCEPTION;
    }
}
