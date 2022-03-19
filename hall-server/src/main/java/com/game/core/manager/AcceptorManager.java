package com.game.core.manager;


import com.game.core.cmd.AcceptorListener;
import com.me.transport.Acceptor;
import com.me.transports.netty.NettySocketAcceptor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Component
@Order(value = 1)
public class AcceptorManager implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        Acceptor acceptor = new NettySocketAcceptor(8971);
        acceptor.doInit();
        acceptor.listener().add(new AcceptorListener());
        acceptor.start(false);
    }

    @Override
    public void destroy() throws Exception {

    }
}
