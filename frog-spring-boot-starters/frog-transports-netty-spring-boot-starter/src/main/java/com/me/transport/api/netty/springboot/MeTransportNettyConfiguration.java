package com.me.transport.api.netty.springboot;

import com.me.transport.api.netty.springboot.conf.NettyProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({NettyProperties.class})
public class MeTransportNettyConfiguration {

    @Bean
    public NettyBootstrap bootstrap(NettyProperties nettyProperties) {
        return new NettyBootstrap(nettyProperties);
    }

}
