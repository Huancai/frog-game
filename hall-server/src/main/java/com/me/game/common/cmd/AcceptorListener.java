//package com.me.game.core.cmd;
//
//import com.me.game.core.manager.CmdManager;
//import com.me.game.core.manager.SpringManager;
//import com.me.game.module.misc.UnLoginSessionWrap;
//import com.me.transport.Message;
//import com.me.transport.event.IOEvent;
//import com.me.transport.event.IOEventListener;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author wu_hc 【whuancai@163.com】
// */
//@Slf4j
//public final class AcceptorListener implements IOEventListener {
//    static CmdManager cmdManager;
//
//    @Override
//    public void onEvent(IOEvent ioEvent) {
//        if (ioEvent.event() == IOEvent.Event.READ) { //读事件
//            Message message = (Message) ioEvent.attachment();
//            SpringManager.getContext().getBean(CmdManager.class).doExecuteCMD(null, (Message) attachment);
//            return;
//        }
//
//        if (ioEvent.event() == IOEvent.Event.REGISTERED) {
//            UnLoginSessionWrap session = new UnLoginSessionWrap(ioEvent.session());
//            return;
//        }
//
//        if (ioEvent.event() == IOEvent.Event.UNREGISTERED) {
//            return;
//        }
//    }
//}
