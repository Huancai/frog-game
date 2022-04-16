package com.me.common.worker.select;

import com.me.common.worker.Worker;
import com.me.common.worker.WorkerGroup.SelectStrategy;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wu_hc 【whuancai@163.com】
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
    public EventLoopChooser newChooser(Worker[] executors) {
        return newChooser(executors, SelectStrategy.ROUND);
    }

    @Override
    public EventLoopChooser newChooser(Worker[] executors, SelectStrategy strategy) {
        if (SelectStrategy.ROUND == strategy) {
            if (isPowerOfTwo(executors.length)) {
                return new PowerOfTowEventLoopChooser(executors);
            } else {
                return new GenericEventLoopChooser(executors);
            }
        } else if (SelectStrategy.BALANCE == strategy) {
            return new BalanceEventLoopChooser(executors);
        } else {
            return new RandomEventLoopChooser(executors);
        }
    }

    private static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }

    /**
     * 二次方
     */
    private static final class PowerOfTowEventLoopChooser implements EventLoopChooser {
        private final AtomicInteger idx = new AtomicInteger();
        private final Worker[] executors;

        PowerOfTowEventLoopChooser(Worker[] executors) {
            this.executors = executors;
        }

        @Override
        public Worker next() {
            return executors[idx.getAndIncrement() & executors.length - 1];
        }
    }

    /**
     * 顺序
     */
    private static final class GenericEventLoopChooser implements EventLoopChooser {
        private final AtomicInteger idx = new AtomicInteger();
        private final Worker[] executors;

        GenericEventLoopChooser(Worker[] executors) {
            this.executors = executors;
        }

        @Override
        public Worker next() {
            return executors[Math.abs(idx.getAndIncrement() % executors.length)];
        }
    }

    /**
     * 按注册数，这里要特别注意，需要在使用端管理好注册取消注册，
     * 否则会导致分配不均匀，使用端合理使用，则个模式是work分配最均匀的
     */
    private static final class BalanceEventLoopChooser implements EventLoopChooser {
        private final Worker[] executors;

        BalanceEventLoopChooser(Worker[] executors) {
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

    }

    /**
     * 随机
     */
    private static final class RandomEventLoopChooser implements EventLoopChooser {
        private final Worker[] executors;

        RandomEventLoopChooser(Worker[] executors) {
            this.executors = executors;
        }

        @Override
        public Worker next() {
            return executors[ThreadLocalRandom.current().nextInt(executors.length)];
        }
    }
}
