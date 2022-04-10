package com.me.game.common.manager;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.ConsoleTable;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.me.game.middleware.module.MeModule;
import com.me.game.middleware.module.ModuleType;
import com.me.game.middleware.module.AbstractModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Slf4j
@Component
public class ModuleInit implements InitializingBean, DisposableBean {


    //module map
    private static final Map<ModuleType, AbstractModule> moduleMap = new HashMap<>();

    final List<AbstractModule> orderModules = Lists.newArrayList();

    @Autowired
    Environment environment;


    @Override
    public void afterPropertiesSet() throws Exception {
        String scanPackage = environment.getProperty("frog.module-scan-package", "com.me.game.module");
        log.info("ModuleInit scanPackage:{}", scanPackage);
        Set<Class<?>> classSet = ClassUtil.scanPackageByAnnotation(scanPackage, MeModule.class);
        List<Class<? extends AbstractModule>> list = Lists.newArrayListWithCapacity(classSet.size());
        for (Class<?> aClass : classSet) {
            list.add((Class<? extends AbstractModule>) aClass);
        }
        ConsoleTable table = ConsoleTable.create().addHeader("Module", "Class", "Cost(ms)");
        list.sort(Comparator.comparingInt(c -> c.getAnnotation(MeModule.class).order()));
        for (Class<? extends AbstractModule> moduleClass : list) {

            long cur = System.currentTimeMillis();

            AbstractModule abstractModule = moduleClass.newInstance();
            abstractModule.start();
            ModuleType type = moduleClass.getAnnotation(MeModule.class).type();
            log.info("ModuleInit >{} : {}", type, moduleClass);

            AbstractModule oldObj = moduleMap.put(type, abstractModule);
            if (Objects.nonNull(oldObj)) {
                throw new RuntimeException(StrUtil.format("duplicate module type:{}", type));
            }
            orderModules.add(abstractModule);

            long now = System.currentTimeMillis();
            table.addBody(type.name(), moduleClass.getName(), Convert.toStr(now - cur));
        }

        log.info("Module Info:\n{}", table.toString());
    }

    @Override
    public void destroy() throws Exception {
        stopModule();
    }

    public void stopModule() {
        for (AbstractModule module : orderModules) {
            module.stop();
        }
    }

    public <T> T getModule(ModuleType moduleType) {
        return (T) moduleMap.get(moduleType);
    }

}
