/**
 * Copyright(c) 2014 ShenZhen jingqi Network Technology Co., Ltd.
 * All rights reserved.
 * Created on  2014-2-21  下午3:47:16
 */

package com.me.common.event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
     * 监听都列表。同一事件，可能存在多个相同的监听者。
     */
    private Map<IEventType, Set<IEventListener>> weakListeners;

    /**
     * 监听都列表。同一事件，可能存在多个相同的监听者。
     */
    private Map<IEventType, Set<IVoteEventListener>> voteListeners;

    /**
     *
     */
    private ReadWriteLock lock;

    /**
     *
     */
    private ReadWriteLock vLock;

    /**
     * 构造方法
     */
    public EventSource() {
        this.lock = new ReentrantReadWriteLock();
        this.vLock = new ReentrantReadWriteLock();

    }

    /**
     * @param eventType
     * @param listener
     */
    public void addListener(final IEventType eventType, final IEventListener listener) {
        addListener(eventType, listener, true);
    }

    /**
     * 将监听者加入到指定事件类型的监听队列中。
     *
     * @param eventType 事件类型
     * @param listener  监听者
     */
    public void addListener(final IEventType eventType, final IEventListener listener, boolean weakReference) {

        try {

            // 加写锁确保没有并发问题。                
            this.lock.writeLock().lock();

            if (!weakReference) {
                if (listeners == null) {
                    listeners = new ConcurrentHashMap<IEventType, Set<IEventListener>>();
                }
                Set<IEventListener> lstns = this.listeners.get(eventType);
                if (lstns == null) {
                    lstns = new HashSet<IEventListener>();
                    lstns.add(listener);
                    this.listeners.put(eventType, lstns);
                } else {
                    lstns.add(listener);
                }
            } else {
                if (weakListeners == null) {
                    weakListeners = new ConcurrentHashMap<IEventType, Set<IEventListener>>();
                }
                Set<IEventListener> lstns = this.weakListeners.get(eventType);
                if (lstns == null) {
                    lstns = Collections.newSetFromMap(new WeakHashMap<IEventListener, Boolean>());
                    lstns.add(listener);
                    this.weakListeners.put(eventType, lstns);
                } else {
                    lstns.add(listener);
                }
            }
        } catch (Exception e) {
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
        if (listeners == null && weakListeners == null) {
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

            if (weakListeners != null) {
                lstns = this.weakListeners.get(eventType);
                if (lstns != null) {
                    lstns.remove(listener);
                }
            }

        } catch (Exception e) {
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * @param event
     */
    public void notifyListeners(final Event event) {
        notifycation(event, weakListeners, true);
        notifycation(event, listeners, false);
    }

    /**
     * 通知监听者发生了事件。事件源由事件参数arg指定。
     *
     * @param event 事件.
     */
    public void notifyListeners(final Event event, boolean isWeak) {
        if (isWeak) {
            notifycation(event, weakListeners, isWeak);
        } else {
            notifycation(event, listeners, isWeak);
        }
    }

    /**
     * @param event
     */
    private void notifycation(final Event event, Map<IEventType, Set<IEventListener>> listens, boolean isWeak) {
        try {
            if (listens == null || listens.isEmpty()) {
                return;
            }
            Collection<IEventListener> lstns = null;
            lstns = listens.get(event.getEventType());
            if (lstns == null || lstns.isEmpty()) {
                return;
            }

            this.lock.readLock().lock();
            if (lstns != null) {
                lstns = new ArrayList<IEventListener>(lstns);
            }
            this.lock.readLock().unlock();

            for (IEventListener lstn : lstns) {
                lstn.onEvent(event);
            }

        } catch (Exception e) {
        } finally {
            if (event.isChain()) {
                EventSource eventOwner = getEventOwner();
                if (eventOwner != null) {
                    event.setCurrentEventSource(eventOwner);
                    eventOwner.notifyListeners(event, isWeak);
                }
            }
        }
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
    public void notifyListenners(final IEventType eventType, final Object... args) {
        Event event = new Event(this, eventType, null, true);
        for (Object object2 : args) {
            event.addPar(object2);
        }
        notifyListeners(event);
    }

    //////////////////////////vote event////////////////////////////////////////////////////////////

    /**
     *
     */
    private boolean notifyVoteListeners(final Event event) {

//        Collection<IEventListener> lstns = null;
//       
//        if (lstns == null || lstns.isEmpty()) {
//            return;
//        }
//
        if (voteListeners == null || voteListeners.isEmpty()) {
            return true;
        }

        Collection<IVoteEventListener> lstns = null;
        lstns = voteListeners.get(event.getEventType());
        if (lstns == null || lstns.isEmpty()) {
            return true;
        }

        try {
            this.vLock.readLock().lock();
            if (lstns != null) {
                lstns = new ArrayList<IVoteEventListener>(lstns);
            }
            this.vLock.readLock().unlock();

            for (IVoteEventListener item : lstns) {
                if (!item.onVoteEvent(event)) {
                    return false;
                }
            }
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * 将监听者加入到指定事件类型的监听队列中。
     *
     * @param eventType 事件类型
     * @param listener  监听者
     */
    public void addVoteListener(final IEventType eventType, final IVoteEventListener listener) {

        try {
            // 加写锁确保没有并发问题。
            this.vLock.writeLock().lock();
            if (voteListeners == null) {
                voteListeners = new ConcurrentHashMap<IEventType, Set<IVoteEventListener>>();
            }
            Set<IVoteEventListener> lstns = this.voteListeners.get(eventType);
            if (lstns == null) {
                lstns = new HashSet<IVoteEventListener>();
                lstns.add(listener);
                this.voteListeners.put(eventType, lstns);
            } else {
                lstns.add(listener);
            }
        } catch (Exception e) {
        } finally {
            this.vLock.writeLock().unlock();
        }

    }

    /**
     * 从指定事件类型的监听队列中移除指定的监听者。
     *
     * @param eventType 事件类型
     * @param listener  监听者
     */
    public void removeVoteListener(final IEventType eventType, final IVoteEventListener listener) {
        if (voteListeners == null) {
            return;
        }
        try {
            this.vLock.writeLock().lock();
            Collection<IVoteEventListener> lstns = this.voteListeners.get(eventType);
            if (lstns != null) {
                lstns.remove(listener);
            }
        } catch (Exception e) {
        } finally {
            this.vLock.writeLock().unlock();
        }

    }

    /**
     * 一票own否決 不需要事件链（玩家被控制了 导致小伙伴也不能使用技能） 时间静止 自己构造时间链
     *
     * @param eventType
     * @param arg
     */
    public boolean notifyVoteListeners(final IEventType eventType, final Object arg) {
        return notifyVoteListeners(new Event(this, eventType, arg, false));
    }

    /**
     * @param eventType
     * @return
     */
    public boolean notifyVoteListeners(final IEventType eventType) {
        return this.notifyVoteListeners(eventType, null);
    }

    /**
     * @return
     */
    public <T extends EventSource> T getEventOwner() {
        return null;
    }

}
