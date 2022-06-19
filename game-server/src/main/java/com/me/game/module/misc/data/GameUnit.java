package com.me.game.module.misc.data;

import com.me.common.event.EventSource;
import com.me.game.middleware.component.AbstractComponent;
import com.me.game.middleware.component.ComponentType;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wu_hc
 */
public class GameUnit extends EventSource {

    private final Map<ComponentType, AbstractComponent> components = new ConcurrentHashMap<>();
    private final Map<Class<?>, AbstractComponent> componentsC = new ConcurrentHashMap<>();

    protected void initComponent() {

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

    public <T extends AbstractComponent> T getComponent(Class<T> clazz) {
        return clazz.cast(componentsC.get(clazz));
    }

    public void addComponent(ComponentType componentType, AbstractComponent component) {
        components.put(componentType, component);
        componentsC.put(component.getClass(), component);
    }

    public Collection<AbstractComponent> getAllComponents() {
        return Collections.unmodifiableCollection(components.values());
    }

}
