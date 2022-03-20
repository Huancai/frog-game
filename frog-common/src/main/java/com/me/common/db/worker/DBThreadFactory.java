package com.me.common.db.worker;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class DBThreadFactory implements ThreadFactory, UncaughtExceptionHandler {

	/**
	 * 是否为后台线程
	 */
	private boolean daemon;

	/**
	 * 线程名
	 */
	private String threadName;
	/**
	 * 优先级
	 */
	private int prio;
	/**
	 * 线程组
	 */
	private ThreadGroup group;
	/**
	 * 线程数目
	 */
	private AtomicInteger threadNumber = new AtomicInteger(1);

	/**
	 * 
	 * @param threadName
	 */
	public DBThreadFactory(final String threadName) {
		this(threadName, false);
		group = new ThreadGroup(threadName);
	}

	/**
	 * 
	 * 
	 * @param threadName
	 *            线程名前缀
	 * @param daemon
	 *            是否为后台线程
	 */
	public DBThreadFactory(final String threadName, final boolean daemon) {
		this.threadName = threadName;
		this.daemon = daemon;
		group = new ThreadGroup(threadName);
	}

	/**
	 * 
	 * 
	 * @param name
	 * @param prio
	 */
	public DBThreadFactory(final String threadName, final int prio) {
		this.threadName = threadName;
		this.prio = prio;
		this.daemon = false;
		group = new ThreadGroup(threadName);
	}

	/**
	 * 
	 * 
	 * @param name
	 * @param prio
	 */
	public DBThreadFactory(final String threadName, final boolean daemon, final int prio) {
		this.prio = prio;
		this.daemon = daemon;
		this.threadName = threadName;
		group = new ThreadGroup(threadName);
	}

	@Override
	public Thread newThread(final Runnable r) {
		Thread t = new Thread(group, r);
		t.setName(threadName + "-" + threadNumber.getAndIncrement());
		t.setPriority(prio);
		t.setDaemon(daemon);
		t.setUncaughtExceptionHandler(this);
		return t;
	}

	@Override
	public void uncaughtException(final Thread thread, final Throwable throwable) {
		System.out.println(String.format("线程【%s】抛出异常。,exception:%s", thread.getName(), throwable));
	}

	/**
	 * @return the threadName
	 */
	public String getThreadName() {
		return threadName;
	}
}
