package com.me.common.delayqueue;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public class GlobalDelayExecuteTest {

    public static void main(String[] args) {
        GlobalDelayExecutor.getInstance().add(new TestDelayAction(3000));

    }
}
