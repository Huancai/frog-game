package com.me.transport.api.netty.springboot;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MeTransportsNettyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MeTransportNettyConfiguration.class.getName()};
    }
}
