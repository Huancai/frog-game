package com.me;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class})*/
@MapperScan(value = "com.me.metadata.db.mapper")
public class GameApp {

    public static void main(String[] args) {
        SpringApplication.run(GameApp.class, args);
    }

}
