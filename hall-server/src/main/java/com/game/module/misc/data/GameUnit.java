package com.game.module.misc.data;

import com.game.middleware.component.AbstractComponent;
import com.game.middleware.component.ComponentType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wu_hc
 */
public class GameUnit {

    protected final Map<ComponentType, AbstractComponent> components = new ConcurrentHashMap<>();

    public void initComponent() {

    }

    /**
     * 拿到组件
     *
     * @param componentType
     * @param <T>
     * @return
     */
    public <T extends AbstractComponent> T getComponent(ComponentType componentType) {
        return (T) components.get(componentType);
    }

    public void addComponent(ComponentType componentType, AbstractComponent component) {
        components.put(componentType, component);
    }
}
