package com.me.transport.netty.springboot;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MeTransportsNettyImportSelector.class)
public @interface EnableMeTransportNetty {
}
