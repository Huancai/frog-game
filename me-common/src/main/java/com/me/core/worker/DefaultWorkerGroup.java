package com.me.core.worker;

import com.me.core.worker.select.ChooserStrategyFactory;
import com.me.core.worker.select.DefaultChooserStrategyFactory;

/**
 *
 * @author wu_hc 【whuancai@163.com】
 */
public class DefaultWorkerGroup implements WorkerLoopGroup {

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
	private final ChooserStrategyFactory.EventLoopChooser chooser;

	/**
	 * 
	 * @param prefix
	 * @return
	 */
	public static DefaultWorkerGroup newGroup(String prefix) {
		return new DefaultWorkerGroup(prefix, Runtime.getRuntime().availableProcessors(), SelectStrategy.ROUND);
	}

	/**
	 * 
	 * @param prefix
	 * @param workerCount
	 * @return
	 */
	public static DefaultWorkerGroup newGroup(String prefix, int workerCount) {
		return new DefaultWorkerGroup(prefix, workerCount, SelectStrategy.ROUND);
	}

	/**
	 * 
	 * @param prefix
	 * @param workerCount
	 * @param strategy
	 * @return
	 */
	public static DefaultWorkerGroup newGroup(String prefix, int workerCount, SelectStrategy strategy) {
		return new DefaultWorkerGroup(prefix, workerCount, strategy);
	}

	/**
	 * 
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
	public SelectStrategy getStrategy() {
		return this.strategy;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < executors.length; i++) {
			sb.append("event-" + i).append(" register:").append(executors[i].getRegisterCount()).append("\t");
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
