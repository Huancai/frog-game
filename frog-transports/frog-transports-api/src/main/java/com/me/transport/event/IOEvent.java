package com.me.transport.event;


import com.me.transport.session.Session;

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
