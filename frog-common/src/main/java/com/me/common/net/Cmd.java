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
     * @return
     */
    String desc() default "";
}
