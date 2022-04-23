
package com.me.common.delayqueue;


/**
 * @author wu_hc 【whuancai@163.com】
 */
public class DelayActionExecutor implements Runnable {

    private DelayAction action = null;

    public DelayActionExecutor(DelayAction action) {
        this.action = action;
    }


    @Override
    public void run() {
        try {
            action.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}