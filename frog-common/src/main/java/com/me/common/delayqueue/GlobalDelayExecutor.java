package com.me.common.delayqueue;


import cn.hutool.core.thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
public final class GlobalDelayExecutor {

    private static final GlobalDelayExecutor INSTANCE = new GlobalDelayExecutor();

    private final ExecutorService executorService = new ThreadPoolExecutor(
            2,
            2,
            0,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new NamedThreadFactory("DELAY-QUEUE-PROCESS-THREADS", true)
    );

    private final DelayQueue taskQueue = new DelayQueue();

    private GlobalDelayExecutor() {
        Thread thread = new Thread(new DelayQueueCustomer());
        thread.setName("DELAY-QUEUE-CUSTOMER-THREADS");
        thread.start();
    }


    public static GlobalDelayExecutor getInstance() {
        return GlobalDelayExecutor.INSTANCE;
    }


    public <T extends DelayAction> void add(final T task) {
        this.taskQueue.add(task);
    }

    /**
     * 获取任务数量
     *
     * @return
     */
    public int getTaskQueueSize() {
        return taskQueue.size();
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    /**
     * @return the taskQueue
     */
    public DelayQueue getTaskQueue() {
        return taskQueue;
    }


    private class DelayQueueCustomer implements Runnable {
        @Override
        public void run() {
            for (; ; ) {
                try {
                    DelayAction action = (DelayAction) taskQueue.take();
                    DelayActionExecutor delayActionExecutor = new DelayActionExecutor(action);
                    executorService.execute(delayActionExecutor);
                } catch (Exception e) {
                    log.error("DelayQueueCustomer#run", e);
                }
            }
        }

    }

}
