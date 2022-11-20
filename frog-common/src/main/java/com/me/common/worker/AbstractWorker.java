package com.me.common.worker;

import com.me.common.worker.api.Worker;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
public abstract class AbstractWorker implements Worker {

    /**
     * ID生成器
     */
    protected static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    /**
     * 注册数量
     */
    protected final AtomicInteger registerCounter = new AtomicInteger(0);

    /**
     * 提交的任务数量
     */
    protected final AtomicLong submitTaskCount = new AtomicLong(0);

    /**
     * 完成的任务数量
     */
    protected final AtomicLong completeTaskCount = new AtomicLong(0);


    protected final AtomicLong totalTimeCount = new AtomicLong(0);
    /**
     * ID
     */
    private final int id;

    /**
     * @param id
     */
    AbstractWorker(int id) {
        this.id = id;
    }

    @Override
    public void register(final IEventListener listener) {
        if (Objects.isNull(listener)) {
            return;
        }
        runInPriLoop(new Runnable() {
            @Override
            public void run() {
                registerCounter.incrementAndGet();
                listener.onEvent(this);
            }
        });
    }

    @Override
    public void unRegister(IEventListener listener) {
        registerCounter.decrementAndGet();
    }

    @Override
    public int getRegisterCount() {
        return registerCounter.get();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public long getTaskCount() {
        return submitTaskCount.get();
    }

    @Override
    public long getCompletedTaskCount() {
        return completeTaskCount.get();
    }

    @Override
    public long getTotalTime() {
        return totalTimeCount.get();
    }

    @Override
    public long getAvgTime() {
        return getTotalTime() / completeTaskCount.get();
    }

    /**
     * @param task
     */
    final void safeExecute(final Runnable task) {
        try {
            long t1 = System.currentTimeMillis();
            task.run();
            long diff = System.currentTimeMillis() - t1;
            completeTaskCount.incrementAndGet();
            totalTimeCount.addAndGet(diff);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
