package com.me.game.module.misc.data;

import com.me.game.middleware.component.AbstractComponent;
import com.me.game.middleware.component.ComponentType;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wu_hc
 */
public class GameUnit {

    private final Map<ComponentType, AbstractComponent> components = new ConcurrentHashMap<>();

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

    public Collection<AbstractComponent> getAllComponents() {
        return Collections.unmodifiableCollection(components.values());
    }
}
