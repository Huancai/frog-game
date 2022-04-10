package com.me.game.middleware.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MeModule {

    /**
     * 类型
     *
     * @return
     */
    ModuleType type();

    /**
     * 描述
     *
     * @return
     */
    String desc() default "";

    /**
     * 初始化顺序
     *
     * @return
     */
    int order() default Integer.MAX_VALUE - 1;
}
