package com.game.runner;

import com.me.metadata.db.entity.PlayerEntity;
import com.me.metadata.db.mapper.PlayerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Component
@Slf4j
public class DBRunner implements ApplicationRunner {


    @Autowired
    private PlayerMapper playerMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<PlayerEntity> playerEntities = playerMapper.selectAll();
        log.info("DBRunner ,playerEntities size:{}", playerEntities.size());

        int allCount = playerMapper.getAllCount();
        log.info("DBRunner ,execute from mapper xml,count:{}",allCount);
    }
}
