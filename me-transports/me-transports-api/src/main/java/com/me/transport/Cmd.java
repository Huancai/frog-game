package com.me.transport;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


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
