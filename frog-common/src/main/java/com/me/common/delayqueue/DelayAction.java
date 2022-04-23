
package com.me.common.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public abstract class DelayAction implements Delayed {

    /**
     * 结束时间
     */
    private long endTime;

    /**
     * delayTime单位毫秒
     */
    private int delayTime = 0;

    /**
     * delayTime单位秒
     */
    public DelayAction(int delayTime) {
        this.endTime = System.currentTimeMillis() + delayTime;
        this.delayTime = delayTime;
    }

    /**
     * @return the delayTime
     */
    public int getDelayTime() {
        return delayTime;
    }

    /**
     * 转换成纳秒。内部实现是用纳秒
     */
    @Override
    public long getDelay(TimeUnit unit) {
        long result = unit.convert((this.endTime - System.currentTimeMillis()) * 1000 * 1000, TimeUnit.NANOSECONDS);
        return result;
    }

    @Override
    public int compareTo(Delayed other) {
        if (other == this) {
            return 0;
        }
        if (other instanceof DelayAction) {
            DelayAction x = (DelayAction) other;
            long diff = this.endTime - x.endTime;
            if (diff < 0) {
                return -1;
            } else if (diff > 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            throw new IllegalArgumentException("传入的参数不正确!");
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + ",延迟时间是:" + delayTime;
    }

    /**
     *
     */
    public abstract void execute();

}
