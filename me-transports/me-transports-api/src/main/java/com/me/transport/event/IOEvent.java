package com.me.transport.event;


import com.me.transport.session.Session;

/**
 * @author wu_hc <b> 2018年2月3日下午11:16:26 </b>
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
