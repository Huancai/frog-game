package com.me.common.net;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author wu_hc 【whuancai@163.com】
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Cmd {

    /**
     * 协议号
     *
     * @return
     */
    short code();

    /**
     * 是否异步，这里异步就不会在玩家工作线程执行，适用于查询操作，不适用增删改操作
     *
     * @return
     */
    boolean async() default false;

    /**
     * @return
     */
    String desc() default "";
}
