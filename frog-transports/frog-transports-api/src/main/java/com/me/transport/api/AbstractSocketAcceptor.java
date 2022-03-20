
package com.me.transport.api;

import com.me.transport.api.event.IOEvent;
import com.me.transport.api.event.IOEventListener;
import com.me.transport.api.session.Session;

import java.util.LinkedList;
import java.util.List;


/**
 * @author wu_hc 【whuancai@163.com】
 */
public abstract class AbstractSocketAcceptor<T extends Session> implements Acceptor, IOEventListener {

    protected final int inetPort;
    protected final String inetHost;

    /**
     * 网络事件事件监听者
     */
    protected final List<IOEventListener> ioEventListeners = new LinkedList<IOEventListener>();

    /**
     * @param inetPort
     */
    public AbstractSocketAcceptor(int inetPort) {
        this(inetPort, null);
    }

    public AbstractSocketAcceptor(int inetPort, String inetHost) {
        this.inetPort = inetPort;
        this.inetHost = inetHost;
    }

    @Override
    public void onEvent(IOEvent ioEvent) {
        for (int i = 0; i < ioEventListeners.size(); i++) {
            ioEventListeners.get(i).onEvent(ioEvent);
        }
    }

    @Override
    public int boundPort() {
        return this.inetPort;
    }

    public String getInetHost() {
        return inetHost;
    }

    @Override
    public void start() throws Exception {
        start(true);
    }

    /**
     * @return
     */
    @Override
    public final List<IOEventListener> listener() {
        return this.ioEventListeners;
    }
}
