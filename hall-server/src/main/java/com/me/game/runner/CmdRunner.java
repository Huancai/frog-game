package com.me.game.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(value = 2)
public class CmdRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }
}
