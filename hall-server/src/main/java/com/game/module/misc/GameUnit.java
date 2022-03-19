package com.game.module.misc;

import com.game.middleware.component.AbstractComponent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wu_hc
 */
public class GameUnit {

    protected final Map<String, AbstractComponent> components = new ConcurrentHashMap<>();

    public void initComponent() {

    }

    /**
     * 拿到组件
     *
     * @param name
     * @param <T>
     * @return
     */
    public <T extends AbstractComponent> T getComponent(String name) {
        return (T) components.get(name);
    }
}
