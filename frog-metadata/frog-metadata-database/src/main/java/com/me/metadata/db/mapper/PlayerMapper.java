package com.me.metadata.db.mapper;

import com.me.metadata.db.BaseEntity;
import com.me.metadata.db.entity.PlayerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Mapper
public interface PlayerMapper extends BaseMapper {

    @Select("SELECT * FROM t_player")
    List<PlayerEntity> selectAll();

    int getAllCount();

    //    @Update("UPDATE")
    @Override
    default void update(BaseEntity entity) {

    }
}
