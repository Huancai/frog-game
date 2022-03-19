package com.game;

import com.me.transport.netty.springboot.EnableMeTransportNetty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableMeTransportNetty
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AppBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(AppBootstrap.class, args);
    }

}
