package com.game.core.manager;

import cn.hutool.core.util.ClassUtil;
import com.game.middleware.component.AbstractComponent;
import com.game.middleware.component.MeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
@Component
public class ManagerInit implements InitializingBean {

    private final Set<Class<? extends AbstractComponent>> componentClasses = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<Class<?>> classSet = ClassUtil.scanPackageByAnnotation("com.game.module", MeComponent.class);
        for (Class<?> aClass : classSet) {
            log.info("ManagerInit > {}", aClass);
            componentClasses.add((Class<? extends AbstractComponent>) aClass);
        }
    }

    public Set<Class<? extends AbstractComponent>> getComponentClasses() {
        return componentClasses;
    }
}
