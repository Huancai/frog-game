package com.me.transport.api.event;


import com.me.transport.api.session.Session;

/**
 * @author wu_hc
 * @mail whuancai@163.com
 */
public interface IOEvent {

    /**
     * @return IO事件
     */
    Event event();

    /**
     * @return 会话
     */
    Session session();

    /**
     * @return 附带信息
     */
    Object attachment();

    enum Event {
        READ, ACTIVE, INACTIVE, EXCEPTION;
    }
}
