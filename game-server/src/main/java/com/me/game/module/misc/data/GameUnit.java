package com.me.game.module.misc.data;

import com.me.common.event.EventSource;
import com.me.game.middleware.component.AbstractComponent;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wu_hc
 */
public class GameUnit extends EventSource {

    private final Map<Class<?>, AbstractComponent> components = new ConcurrentHashMap<>();

    protected void initComponent() {

    }

    public <T extends AbstractComponent> T getComponent(Class<T> clazz) {
        return clazz.cast(components.get(clazz));
    }

    public void addComponent(Class<?> clazz, AbstractComponent component) {
        components.put(clazz, component);
    }

    public Collection<AbstractComponent> getAllComponents() {
        return Collections.unmodifiableCollection(components.values());
    }

}
