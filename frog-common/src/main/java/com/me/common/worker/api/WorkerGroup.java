package com.me.common.worker.api;

/**
 * 处理线程组
 *
 * @author wuhuancai
 * @mail whuancai@163.com
 */
public interface WorkerGroup {

    /**
     * 下一个
     *
     * @return
     */
    Worker next();

    /**
     * @param hash
     * @return
     */
    Worker next(int hash);

    /**
     * 返回分配策略
     *
     * @return
     */
    SelectStrategy getStrategy();

    /**
     * 总提交任务数量
     *
     * @return
     */
    long getTaskCount();

    /**
     * 已经执行的任务数量
     *
     * @return
     */
    long getCompletedTaskCount();

    /**
     * 选择策略
     */
    enum SelectStrategy {
        ROUND, BALANCE, RANDOM, REMAINING
    }
}