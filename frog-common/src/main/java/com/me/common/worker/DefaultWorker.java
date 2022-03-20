package com.me.common.worker;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public final class DefaultWorker extends AbstractWorker implements Worker {


    /**
     * 高优先级的任务,在每次主循環中執行完
     */
    private final Queue<Runnable> priorityQueue = new ConcurrentLinkedQueue<>();

    /**
     * 任务缓冲队列，在每次主循环中取出一个任务进行处理
     */
    private final BlockingQueue<Runnable> taskQueue;


    /**
     * @param prefix
     */
    public DefaultWorker(final String prefix) {
        this(prefix, -1);
    }

    /**
     * @param prefix
     * @param index
     */
    public DefaultWorker(final String prefix, final int index) {
        this(prefix, index, 1 << 14);
    }

    /**
     * @param prefix
     * @param index
     * @param capacity
     */
    public DefaultWorker(final String prefix, final int index, int capacity) {
        super(ID_GENERATOR.incrementAndGet());
        if (capacity <= 0)
            throw new IllegalArgumentException("capacity must more than zero!!");

        // 多生产者单消费者
        taskQueue = new LinkedBlockingQueue<>(capacity);

        EventWorker worker = new EventWorker(index >= 0 ? (prefix + "-" + index) : prefix);
        worker.start();
    }

    @Override
    public void runInLoop(Runnable task) {
        boolean result = taskQueue.offer(task);
        submitTaskCount.incrementAndGet();
        if (!result) {
//			log.error("##### thread[{}]--- NOR task Queue full!! ---#####", Thread.currentThread().getName());
        }
    }

    @Override
    public void runInPriLoop(Runnable task) {
        boolean result = priorityQueue.offer(task);
        submitTaskCount.incrementAndGet();
        if (!result) {
//			log.error("##### thread[{}]--- PRI task Queue error!! ----#####", Thread.currentThread().getName());
        }
    }

    // 处理事件的线程
    private final class EventWorker extends Thread {

        public EventWorker(final String name) {
            super(name);
            setDaemon(true);
        }

        @Override
        public void run() {
            for (; ; ) {
                try {
                    mainLoop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 线程主循环
         *
         * @throws Exception
         */
        private void mainLoop() throws Exception {

            Runnable task;
            while ((task = priorityQueue.poll()) != null) {
                executeTask(task);
                completeTaskCount.incrementAndGet();
            }
            task = taskQueue.take();
            if (null != task) {
                executeTask(task);
                completeTaskCount.incrementAndGet();
            }
        }
    }

    @Override
    public String toString() {
        return "DefaultEventLoop [getRegisterCount()=" + getRegisterCount() + ", getId()=" + getId() + "]";
    }

}
