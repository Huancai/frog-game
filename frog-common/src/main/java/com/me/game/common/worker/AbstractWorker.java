package com.me.game.common.worker;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 工作队列抽象 ，对应一个线程
 *
 * @author wu_hc 【whuancai@163.com】
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
    protected AtomicLong submitTaskCount = new AtomicLong(0);

    /**
     * 完成的任务数量
     */
    protected AtomicLong completeTaskCount = new AtomicLong(0);

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

    /**
     * @param task
     */
    static void executeTask(final Runnable task) {

        long c = System.currentTimeMillis();
        try {
            task.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (System.currentTimeMillis() - c > 200) {
            //log
        }
    }

}
