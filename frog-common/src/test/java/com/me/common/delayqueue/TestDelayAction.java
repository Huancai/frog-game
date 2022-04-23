package com.me.common.delayqueue;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public class TestDelayAction extends DelayAction {

    /**
     * delayTime单位秒
     *
     * @param delayTime
     */
    public TestDelayAction(int delayTime) {
        super(delayTime);
    }

    @Override
    public void execute() {
        System.out.println("TestDelayAction execute");
    }
}
