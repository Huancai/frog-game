package com.me.common.worker;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
@Slf4j
public final class SelfDriverQueue implements Runnable, Executor {

    private static final LongAdder ID_ADDER = new LongAdder();

    /**
     * 队列
     */
    private final LinkedList<Runnable> taskQueue = new LinkedList<>();

    /**
     * 执行器
     */
    private final Executor executor;

    /**
     * 锁对象
     */
    private final Lock lock = new ReentrantLock();

    /**
     * 提交的任务数量
     */
    private final LongAdder submitTaskCount = new LongAdder();

    /**
     * 容量
     */
    private int capacity;

    /**
     * 名字
     */
    private String name;


    public static SelfDriverQueue newQueue(Executor executor) {
        return new SelfDriverQueue(executor, null, Integer.MAX_VALUE);
    }

    public static SelfDriverQueue newQueue(Executor executor, String name) {
        return new SelfDriverQueue(executor, name, Integer.MAX_VALUE);
    }

    public static SelfDriverQueue newQueue(Executor executor, String name, int capacity) {
        return new SelfDriverQueue(executor, name, capacity);
    }

    /**
     * @param executor
     */
    private SelfDriverQueue(Executor executor, String name, int capacity) {
        checkNotNull(executor, "executor is a nil!");
        if (capacity <= 0)
            throw new RuntimeException("capacity must > 0");

        ID_ADDER.increment();

        this.executor = executor;
        this.name = StrUtil.isEmpty(name) ? "SELF-DRIVER-QUEUE-" + ID_ADDER.longValue() : name;
        this.capacity = capacity;
    }


    /**
     * @param task
     */
    public int addTask(final Runnable task) {
        checkNotNull(task, "task is nil value ");

        int taskSize = 0;
        final Lock lock = this.lock;
        lock.lock();

        try {
            if (size() >= capacity) {
                throw new RuntimeException("Queue is full!!!!");
            }

            if (isEmpty()) {
                taskQueue.add(task);
                executor.execute(this);
            } else {
                taskQueue.add(task);
            }
            submitTaskCount.increment();
            taskSize = size();
            if (taskSize > 1000) {
                log.warn("more than 1000 task in queue! size:[{}],thread:[{}]", taskSize, Thread.currentThread().getName());
            }

        } finally {
            lock.unlock();
        }

        return taskSize;
    }

    @Override
    public void execute(Runnable task) {
        addTask(task);
    }

    /**
     * 将队列里未执行的任务抽离出来 --GAME-TODO(待测试)
     *
     * @return
     */
    public List<Runnable> drainQueue() {

        List<Runnable> taskList = null;
        final Lock lock = this.lock;
        lock.lock();
        try {
            if (isEmpty()) {
                taskList = Collections.emptyList();
            } else {
                taskList = new ArrayList<>(size() + 16);
                taskList.addAll(taskQueue);
                taskQueue.clear();
            }
        } finally {
            lock.unlock();
        }
        return taskList;
    }

    @Override
    public void run() {
        try {
            doExecute();
        } finally {
            tryNextCommit();
        }
    }

    /**
     * 每次提交一个任务
     */
    void doExecute() {

        Runnable task = null;
        final Lock lock = this.lock;
        lock.lock();
        try {
            if (!taskQueue.isEmpty()) {
                task = taskQueue.peek();
            }
        } finally {
            lock.unlock();
        }

        // 在锁外执行，执行期间不占用锁
        if (Objects.nonNull(task)) {
            safeExecute(task);
        }
    }


    /**
     * 尝试再提交
     */
    private void tryNextCommit() {
        final Lock lock = this.lock;
        lock.lock();
        try {
            taskQueue.poll();
            if (!isEmpty()) {
                executor.execute(this);
            }
        } finally {
            lock.unlock();
        }
    }

    static void safeExecute(Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int size() {
        return taskQueue.size();
    }

    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    public void clear() {
        final Lock lock = this.lock;
        lock.lock();
        try {
            taskQueue.clear();
        } finally {
            lock.unlock();
        }
    }

    public long getTotalTaskCount() {
        return submitTaskCount.longValue();
    }

    /**
     * @param v
     * @param msg
     */
    private static void checkNotNull(Object v, String msg) {
        if (v == null)
            throw StrUtil.isEmpty(msg) ? new NullPointerException() : new NullPointerException(msg);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return StrUtil.format("name:{}\tcapacity:{}\ttotal:{}\tsize:{}", name, capacity, getTotalTaskCount(), size());
    }
}
