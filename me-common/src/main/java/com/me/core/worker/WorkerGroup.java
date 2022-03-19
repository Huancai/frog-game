package com.me.core.worker;

/**
 * 线程组
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
     * 选择策略
     */
    enum SelectStrategy {
        ROUND, BALANCE, RANDOM;
    }
}