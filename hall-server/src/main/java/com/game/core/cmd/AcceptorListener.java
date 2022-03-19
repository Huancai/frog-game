package com.game.core.cmd;

import com.game.core.manager.CmdManager;
import com.game.core.manager.SpringHolder;
import com.me.transport.Message;
import com.me.transport.event.IOEvent;
import com.me.transport.event.IOEventListener;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public final class AcceptorListener implements IOEventListener {
    @Override
    public void onEvent(IOEvent ioEvent) {
        if (ioEvent.event() == IOEvent.Event.READ) { //读事件
            Object attachment = ioEvent.attachment();
            if (attachment instanceof Message) {
                SpringHolder.getContext().getBean(CmdManager.class).doExecuteCMD(null, (Message) attachment);
            }
            return;
        }

        if (ioEvent.event() == IOEvent.Event.REGISTERED) {

        }
    }
}
