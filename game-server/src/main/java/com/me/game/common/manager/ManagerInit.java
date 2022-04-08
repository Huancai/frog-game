package com.me.game.common.manager;

import cn.hutool.core.util.ClassUtil;
import com.me.game.middleware.component.AbstractComponent;
import com.me.game.middleware.component.MeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
@Component
public class ManagerInit implements InitializingBean {

    @Autowired
    Environment environment;

    private final Set<Class<? extends AbstractComponent>> componentClasses = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        String scanPackage = environment.getProperty("frog.component-scan-package", "com.me.game.module");
        log.info("ManagerInit scanPackage:{}", scanPackage);
        Set<Class<?>> classSet = ClassUtil.scanPackageByAnnotation(scanPackage, MeComponent.class);
        for (Class<?> aClass : classSet) {
            log.info("ManagerInit > {}", aClass);
            componentClasses.add((Class<? extends AbstractComponent>) aClass);
        }
    }

    public Set<Class<? extends AbstractComponent>> getComponentClasses() {
        return componentClasses;
    }
}
