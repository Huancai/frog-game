package com.me.metadata.db.mapper;

import com.me.metadata.db.entity.PlayerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Mapper
public interface PlayerMapper {

    @Select("SELECT * FROM t_player")
    List<PlayerEntity> selectAll();

    @Select("SELECT * FROM t_player WHERE unionId=#{unionId}")
    PlayerEntity getOne(String unionId);


    int getAllCount();

}
