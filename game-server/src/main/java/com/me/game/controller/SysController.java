package com.me.game.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@RestController
@RequestMapping("/sys")
public class SysController {

    @GetMapping("/shutdown")
    public String shutdown() {
        return "";
    }
}
