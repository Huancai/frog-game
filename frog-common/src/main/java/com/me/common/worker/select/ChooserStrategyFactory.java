package com.me.common.worker.select;

import com.me.common.worker.api.Worker;
import com.me.common.worker.api.WorkerGroup.SelectStrategy;

/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
public interface ChooserStrategyFactory {

    /**
     * @param workers 工作线程数组
     * @return
     */
    WorkerChooser newChooser(Worker[] workers);

    /**
     * @param workers 工作线程数组
     * @param strategy  策略
     * @return
     */
    WorkerChooser newChooser(Worker[] workers, SelectStrategy strategy);

    /**
     * 选择策略器
     */
    interface WorkerChooser {

        Worker next();

        default Worker next(int hash) {
            return next();
        }
    }
}
