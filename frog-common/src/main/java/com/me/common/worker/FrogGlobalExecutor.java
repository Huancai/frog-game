package com.me.common.worker;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
public final class FrogGlobalExecutor {
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(),
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>());

    /**
     * 异步执行消息，一般用于需要查询数据库的请求
     *
     * @param task
     */
    public static void invokerAsync(final Runnable task) {
        executor.execute(task);
    }

    private FrogGlobalExecutor() {
    }
}
