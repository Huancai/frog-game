package com.me.common.worker;

import com.me.common.worker.api.Worker;
import com.me.common.worker.api.WorkerGroup;
import com.me.common.worker.select.ChooserStrategyFactory;
import com.me.common.worker.select.DefaultChooserStrategyFactory;

/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
public class DefaultWorkerGroup implements WorkerGroup {

    /**
     * 工作组
     */
    private final Worker[] executors;

    /**
     * 分配策略
     */
    private final SelectStrategy strategy;

    /**
     * 选择器
     */
    private final ChooserStrategyFactory.WorkerChooser chooser;

    /**
     * @param prefix
     * @return
     */
    public static DefaultWorkerGroup newGroup(String prefix) {
        return new DefaultWorkerGroup(prefix, Runtime.getRuntime().availableProcessors(), SelectStrategy.ROUND);
    }

    /**
     * @param prefix
     * @param workerCount
     * @return
     */
    public static DefaultWorkerGroup newGroup(String prefix, int workerCount) {
        return new DefaultWorkerGroup(prefix, workerCount, SelectStrategy.ROUND);
    }

    /**
     * @param prefix
     * @param workerCount
     * @param strategy
     * @return
     */
    public static DefaultWorkerGroup newGroup(String prefix, int workerCount, SelectStrategy strategy) {
        return new DefaultWorkerGroup(prefix, workerCount, strategy);
    }

    /**
     * @param prefix
     * @param workerCount
     * @param strategy
     */
    private DefaultWorkerGroup(String prefix, int workerCount, SelectStrategy strategy) {
        if (workerCount <= 0) {
            throw new IllegalArgumentException("create event loop group,size must more than zero!");
        }

        executors = new Worker[workerCount];
        for (int i = 0; i < workerCount; i++) {
            executors[i] = new DefaultWorker(prefix, i + 1);
        }

        this.strategy = strategy;
        chooser = DefaultChooserStrategyFactory.getInstance().newChooser(executors, strategy);
    }

    @Override
    public Worker next() {
        return chooser.next();
    }

    @Override
    public Worker next(int hash) {
        return chooser.next(hash);
    }

    @Override
    public SelectStrategy getStrategy() {
        return this.strategy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DefaultWorkerGroup >");
        for (int i = 0; i < executors.length; i++) {
            sb.append(executors[i].toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public long getTaskCount() {
        long count = 0;
        for (Worker executor : executors) {
            count += executor.getTaskCount();
        }
        return count;
    }

    @Override
    public long getCompletedTaskCount() {
        long count = 0;
        for (Worker executor : executors) {
            count += executor.getCompletedTaskCount();
        }
        return count;
    }

}
