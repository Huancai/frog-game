package com.game.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hall")
@RestController
public class HiController {


    @GetMapping("/hi/{gameId}/{playerId}")
    public String hi(@PathVariable String gameId,@PathVariable int playerId){

        return "hi" + String.format("%s === %s",gameId,playerId);
    }
}
