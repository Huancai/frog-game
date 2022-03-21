package com.me.game.controller;

import com.me.game.data.ServerDto;
import com.me.game.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@RestController
@RequestMapping("/server")
public class ServerListController {

    @GetMapping("/list")
    public Result<List<ServerDto>> listServer() {
        List<ServerDto> dtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ServerDto dto = new ServerDto();
            dto.setHost("127.0.0." + (i + 1));
            dto.setPort(5575);
            dtoList.add(dto);
        }
        return Result.success(dtoList);
    }

    @GetMapping("/choose")
    public Result<ServerDto> chooseServer(@RequestParam("openId") String openId) {
        ServerDto dto = new ServerDto();
        dto.setHost("127.0.0.1");
        dto.setPort(7781);
        return Result.success(dto);
    }
}
