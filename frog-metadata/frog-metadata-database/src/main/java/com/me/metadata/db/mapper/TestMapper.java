package com.me.metadata.db.mapper;

import com.me.metadata.db.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Mapper
public interface TestMapper {

    @Select("UPDATE t_test SET ")
    void update(TestEntity entity);
}
