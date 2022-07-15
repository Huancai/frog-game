package com.me.common.worker.select;

import com.me.common.worker.api.Worker;
import com.me.common.worker.api.WorkerGroup.SelectStrategy;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuhuancai
 * @mail whuancai@163.com
 */
public final class DefaultChooserStrategyFactory implements ChooserStrategyFactory {

    /**
     * 单例
     */
    private final static DefaultChooserStrategyFactory INSTANCE = new DefaultChooserStrategyFactory();

    private DefaultChooserStrategyFactory() {
    }

    public static DefaultChooserStrategyFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public WorkerChooser newChooser(Worker[] workers) {
        return newChooser(workers, SelectStrategy.ROUND);
    }

    @Override
    public WorkerChooser newChooser(Worker[] workers, SelectStrategy strategy) {
        if (SelectStrategy.ROUND == strategy) {
            if (isPowerOfTwo(workers.length)) {
                return new PowerOfTowWorkerChooser(workers);
            } else {
                return new GenericWorkerChooser(workers);
            }
        } else if (SelectStrategy.BALANCE == strategy) {
            return new BalanceWorkerChooser(workers);
        } else {
            return new RandomWorkerChooser(workers);
        }
    }

    private static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }

    /**
     * 二次方
     */
    private static final class PowerOfTowWorkerChooser implements WorkerChooser {
        private final AtomicInteger idx = new AtomicInteger();
        private final Worker[] workers;

        PowerOfTowWorkerChooser(Worker[] workers) {
            this.workers = workers;
        }

        @Override
        public Worker next() {
            return workers[idx.getAndIncrement() & workers.length - 1];
        }

        @Override
        public Worker next(int hash) {
            return workers[hash % workers.length];
        }
    }

    /**
     * 顺序
     */
    private static final class GenericWorkerChooser implements WorkerChooser {
        private final AtomicInteger idx = new AtomicInteger();
        private final Worker[] executors;

        GenericWorkerChooser(Worker[] executors) {
            this.executors = executors;
        }

        @Override
        public Worker next() {
            return executors[Math.abs(idx.getAndIncrement() % executors.length)];
        }

        @Override
        public Worker next(int hash) {
            return executors[hash % executors.length];
        }
    }

    /**
     * 按注册数，这里要特别注意，需要在使用端管理好注册取消注册，
     * 否则会导致分配不均匀，使用端合理使用，则个模式是work分配最均匀的
     */
    private static final class BalanceWorkerChooser implements WorkerChooser {
        private final Worker[] executors;

        BalanceWorkerChooser(Worker[] executors) {
            this.executors = executors;
        }

        @Override
        public Worker next() {
            Worker excutor = null;
            for (final Worker e : executors) {
                if (null == excutor || e.getRegisterCount() < excutor.getRegisterCount()) {
                    excutor = e;
                }
            }
            return excutor;
        }

        @Override
        public Worker next(int hash) {
            return executors[hash % executors.length];
        }
    }


    /**
     * 随机
     */
    private static final class RandomWorkerChooser implements WorkerChooser {
        private final Worker[] executors;

        RandomWorkerChooser(Worker[] executors) {
            this.executors = executors;
        }

        @Override
        public Worker next() {
            return executors[ThreadLocalRandom.current().nextInt(executors.length)];
        }

        @Override
        public Worker next(int hash) {
            return executors[hash % executors.length];
        }
    }
}
