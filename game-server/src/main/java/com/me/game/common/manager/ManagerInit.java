package com.me.game.common.manager;

import cn.hutool.core.lang.ConsoleTable;
import cn.hutool.core.util.ClassUtil;
import com.google.common.collect.Lists;
import com.me.game.middleware.component.AbstractComponent;
import com.me.game.middleware.component.MeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
@Component
public class ManagerInit implements InitializingBean {

    @Autowired
    Environment environment;

    private final Set<Class<? extends AbstractComponent>> componentClasses = new LinkedHashSet<>();

    @Override
    public void afterPropertiesSet() throws Exception {

        ConsoleTable table = ConsoleTable.create().addHeader("Component", "Class");

        String scanPackage = environment.getProperty("frog.component-scan-package", "com.me.game.module");
        log.info("ManagerInit scanPackage:{}", scanPackage);
        Set<Class<?>> classSet = ClassUtil.scanPackageByAnnotation(scanPackage, MeComponent.class);
        List<Class<? extends AbstractComponent>> list = Lists.newArrayListWithCapacity(classSet.size());
        for (Class<?> aClass : classSet) {
            list.add((Class<? extends AbstractComponent>) aClass);
        }
        list.sort(Comparator.comparingInt(c -> c.getAnnotation(MeComponent.class).order()));
        for (Class<? extends AbstractComponent> componentClass : list) {
            componentClasses.add(componentClass);
            log.info("ManagerInit > {}", componentClass);
            table.addBody(componentClass.getAnnotation(MeComponent.class).type().toString(), componentClass.getName());
        }
        log.info("Component Info:\n{}", table.toString());
    }

    public Set<Class<? extends AbstractComponent>> getComponentClasses() {
        return componentClasses;
    }
}
