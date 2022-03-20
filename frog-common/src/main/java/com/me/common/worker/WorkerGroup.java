package com.me.common.worker;

/**
 * 处理线程组
 *
 * @author wu_hc 【whuancai@163.com】
 */
public interface WorkerGroup {

    /**
     * 下一个
     *
     * @return
     */
    Worker next();

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
        ROUND, BALANCE, RANDOM;
    }
}