package com.me.core.worker.select;

import com.me.core.worker.Worker;
import com.me.core.worker.WorkerGroup.SelectStrategy;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface ChooserStrategyFactory {

    /**
     * @param executors
     * @return
     */
    EventLoopChooser newChooser(Worker[] executors);

    /**
     * @param executors
     * @param strategy
     * @return
     */
    EventLoopChooser newChooser(Worker[] executors, SelectStrategy strategy);

    /**
     * 选择策略器
     */
    interface EventLoopChooser {

        Worker next();
    }
}
