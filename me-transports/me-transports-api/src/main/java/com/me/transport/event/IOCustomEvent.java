package com.me.transport.event;


import com.me.transport.session.Session;

/**
 * @author wu_hc
 */
public final class IOCustomEvent implements IOEvent {

    private final Event event;
    private final Session session;
    private final Object attachment;

    /**
     * @param event
     * @param session
     */
    public IOCustomEvent(Event event, Session session) {
        this(event, session, null);
    }

    public IOCustomEvent(Event event, Session session, Object attachment) {
        this.event = event;
        this.session = session;
        this.attachment = attachment;
    }

    @Override
    public Event event() {
        return event;
    }

    @Override
    public Session session() {
        return this.session;
    }

    @Override
    public Object attachment() {
        return attachment;
    }

    @Override
    public String toString() {
        return "IOCustomEvent [event=" + event + ", session=" + session + ", attachment=" + attachment + "]";
    }

}
