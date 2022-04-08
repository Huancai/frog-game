

package com.me.common.event;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 事件源。同一事件类型，支持多个监听者，并且监听者对象可以相同。 warning:可能出现死锁
 *
 * @author wu_hc 【whuancai@163.com】
 */
public class EventSource {

    /**
     * 监听都列表。同一事件，可能存在多个相同的监听者。
     */
    private Map<IEventType, Set<IEventListener>> listeners;

    /**
     *
     */
    private final ReadWriteLock lock;


    /**
     * 构造方法
     */
    public EventSource() {
        this.lock = new ReentrantReadWriteLock();
    }


    /**
     * 将监听者加入到指定事件类型的监听队列中。
     *
     * @param eventType 事件类型
     * @param listener  监听者
     */
    public void addListener(final IEventType eventType, final IEventListener listener) {
        try {
            // 加写锁确保没有并发问题。
            this.lock.writeLock().lock();

            if (listeners == null) {
                listeners = new HashMap<>();
            }
            Set<IEventListener> lstns = this.listeners.get(eventType);
            if (lstns == null) {
                lstns = new HashSet<>();
                lstns.add(listener);
                this.listeners.put(eventType, lstns);
            } else {
                lstns.add(listener);
            }

        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * 从指定事件类型的监听队列中移除指定的监听者。
     *
     * @param eventType 事件类型
     * @param listener  监听者
     */
    public void removeListener(final IEventType eventType, final IEventListener listener) {
        if (listeners == null) {
            return;
        }
        try {
            this.lock.writeLock().lock();
            Collection<IEventListener> lstns = null;
            if (listeners != null) {
                lstns = this.listeners.get(eventType);
                if (lstns != null) {
                    lstns.remove(listener);
                }
            }

        } finally {
            this.lock.writeLock().unlock();
        }
    }


    /**
     * 通知监听者发生了事件。事件源由事件参数arg指定。
     *
     * @param event 事件.
     */
    public void notifyListeners(final Event event) {
        notify(event);
    }


    /**
     * 通知监听者发生了事件。事件源为当前this对象。
     *
     * @param eventType 事件类型
     */
    public void notifyListeners(final IEventType eventType) {
        this.notifyListeners(eventType, null);
    }

    /**
     * 通知监听者发生了事件。事件源为当前this对象。
     *
     * @param eventType 事件类型
     * @param arg       事件参数
     */
    public void notifyListeners(final IEventType eventType, final Object arg) {
        this.notifyListeners(eventType, true, arg);
    }

    /**
     * @param eventType
     * @param chain
     */
    public void notifyListeners(final IEventType eventType, final boolean chain, final Object... args) {

        Event event = new Event(this, eventType, null, chain);
        if (args != null && args.length > 0) {
            event.setData(args[0]);
            for (Object object2 : args) {
                event.addPar(object2);
            }
        }

        notifyListeners(event);
    }

    /**
     * @param eventType
     * @param args
     */
    public void notifyListener(final IEventType eventType, final Object... args) {
        Event event = new Event(this, eventType, null, true);
        for (Object obj : args) {
            event.addPar(obj);
        }
        notifyListeners(event);
    }

    /**
     * @param event
     */
    private void notify(final Event event) {
        try {
            if (listeners == null || listeners.isEmpty()) {
                return;
            }
            Collection<IEventListener> eventListeners;
            eventListeners = listeners.get(event.getEventType());
            if (eventListeners == null || eventListeners.isEmpty()) {
                return;
            }

            this.lock.readLock().lock();
            if (eventListeners != null) {
                eventListeners = new ArrayList<>(eventListeners);
            }
            this.lock.readLock().unlock();

            for (IEventListener listener : eventListeners) {
                listener.onEvent(event);
            }

        } catch (Exception e) {
        } finally {
            if (event.isChain()) {
                EventSource eventOwner = getEventOwner();
                if (eventOwner != null) {
                    event.setCurrentEventSource(eventOwner);
                    eventOwner.notifyListeners(event);
                }
            }
        }
    }

    /**
     * @return
     */
    public <T extends EventSource> T getEventOwner() {
        return null;
    }

}
