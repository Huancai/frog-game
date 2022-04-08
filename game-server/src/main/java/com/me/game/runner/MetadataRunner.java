package com.me.game.runner;

import com.me.metadata.db.entity.PlayerEntity;
import com.me.metadata.db.mapper.PlayerMapper;
import com.me.metadata.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Component
@Slf4j
@Order(value = 3)
public class MetadataRunner implements ApplicationRunner {


    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private RedisManager redisManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<PlayerEntity> playerEntities = playerMapper.selectAll();
        log.info("MetadataRunner ,playerEntities size:{}", playerEntities.size());

        int allCount = playerMapper.getAllCount();
        log.info("MetadataRunner ,execute from mapper xml,count:{}", allCount);

        redisManager.set("hello", "world");
        log.info("redis k:hello v:{}", redisManager.get("hello"));
    }
}
