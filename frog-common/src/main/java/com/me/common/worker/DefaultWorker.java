package com.me.common.worker;

import cn.hutool.core.util.StrUtil;
import com.me.common.worker.api.Worker;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
@Slf4j
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
     * 工作线程
     */
    private EventWorker worker;


    public DefaultWorker(final String prefix) {
        this(prefix, -1);
    }

    public DefaultWorker(final String prefix, final int index) {
        this(prefix, index, 1 << 14);
    }

    public DefaultWorker(final String prefix, final int index, int capacity) {
        super(ID_GENERATOR.incrementAndGet());
        if (capacity <= 0)
            throw new IllegalArgumentException("capacity must more than zero!!");

        // 多生产者单消费者
        taskQueue = new LinkedBlockingQueue<>(capacity);

        EventWorker worker = new EventWorker(index >= 0 ? (prefix + "-" + index) : prefix);
        this.worker = worker;
        worker.start();
    }

    @Override
    public void runInLoop(Runnable task) {
        boolean result = taskQueue.offer(task);
        if (result) {
            submitTaskCount.incrementAndGet();
        } else {
            log.error("##### thread[{}]--- NOR task Queue full!! ---#####", Thread.currentThread().getName());
        }
    }

    @Override
    public void runInPriLoop(Runnable task) {
        boolean result = priorityQueue.offer(task);
        if (result) {
            submitTaskCount.incrementAndGet();
        } else {
            log.error("##### thread[{}]--- PRI task Queue error!! ----#####", Thread.currentThread().getName());
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
                safeExecute(task);
            }
            task = taskQueue.take();
            if (null != task) {
                safeExecute(task);
            }
        }
    }

    @Override
    public String toString() {
        return StrUtil.format("DefaultWork > threadName:{}\ntaskQueueSize:{}\npriorityQueueSize:{}\nsubmitTaskCount:{}\ncompleteTaskCount:{}\nregCnt:{}",
                worker.getName(),
                taskQueue.size(),
                priorityQueue.size(),
                submitTaskCount.get(),
                completeTaskCount.get(),
                registerCounter.get());
    }
}
