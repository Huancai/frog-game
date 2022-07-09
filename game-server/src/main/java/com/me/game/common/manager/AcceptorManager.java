package com.me.game.common.manager;


import com.me.transport.api.Acceptor;
import com.me.transport.api.Message;
import com.me.transport.api.event.IOEvent;
import com.me.transport.api.event.IOEventListener;
import com.me.transports.netty.NettySocketAcceptor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Component
@Order(value = 1)
public class AcceptorManager implements InitializingBean, DisposableBean, IOEventListener {

    @Autowired
    private CmdManager cmdManager;

    private Acceptor acceptor;

    @Override
    public void afterPropertiesSet() throws Exception {
        acceptor = new NettySocketAcceptor(8971);
        acceptor.doInit();
        acceptor.listener().add(this);
        acceptor.start(false);
    }

    @Override
    public void destroy() throws Exception {
        if (Objects.nonNull(acceptor)) {
            acceptor.shutdownGracefully();
        }
    }

    @Override
    public void onEvent(IOEvent ioEvent) {
        if (ioEvent.event() == IOEvent.Event.READ) { //读事件
            Message message = (Message) ioEvent.attachment();
            cmdManager.doExecuteCMD(null, message);
            return;
        }

        if (ioEvent.event() == IOEvent.Event.ACTIVE) { //注册事件
            return;
        }

        if (ioEvent.event() == IOEvent.Event.INACTIVE) { //失效事件
            return;
        }
    }
}
