package com.me.game.common.db.worker;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class DBThreadManager {

	private final ThreadPoolExecutor DB_EXECUTOR;

	private final static class LazyHolder {
		public static final DBThreadManager DB_MANAGER = new DBThreadManager();
	}

	private DBThreadManager() {
		int threadCount = Runtime.getRuntime().availableProcessors();
		DB_EXECUTOR = new ThreadPoolExecutor(threadCount, threadCount, 20, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(),
				new DBThreadFactory("PLAYER-SEND-MSG-PROCESS-THREADS", Thread.NORM_PRIORITY));
	}

	public static DBThreadManager getInstance() {
		return LazyHolder.DB_MANAGER;
	}

	public void execute(final Runnable task) {
		DB_EXECUTOR.execute(task);
	}

	public void execute(final Collection<Runnable> tasks) {
		for (final Runnable r : tasks)
			DB_EXECUTOR.execute(r);
	}
}
