package com.me;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class})*/
@MapperScan(value = "com.me.metadata.db.mapper")
public class AppBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(AppBootstrap.class, args);
    }

}
