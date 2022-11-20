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
     */
    Worker next();

    /**
     *
     */
    Worker next(int hash);

    /**
     * 返回分配策略
     */
    SelectStrategy getStrategy();

    /**
     * 总提交任务数量
     */
    long getTaskCount();

    /**
     * 已经执行的任务数量
     */
    long getCompletedTaskCount();

    /**
     * 选择策略
     */
    enum SelectStrategy {

        /**
         * 轮训
         */
        ROUND,

        /**
         * 按注册量
         */
        BALANCE,

        /**
         * 随机
         */
        RANDOM,

        /**
         * 剩余任务人最少的
         */
        REMAINING
    }
}