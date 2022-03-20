package com.me.game.common.worker.select;

import com.me.game.common.worker.Worker;
import com.me.game.common.worker.WorkerGroup.SelectStrategy;

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
