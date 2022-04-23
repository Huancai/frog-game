package com.me.game.module.test;

import cn.hutool.core.util.IdUtil;
import com.me.game.common.manager.SpringManager;
import com.me.game.middleware.component.AbstractComponent;
import com.me.game.middleware.component.ComponentType;
import com.me.game.middleware.component.MeComponent;
import com.me.game.module.misc.data.GameUnit;
import com.me.game.module.test.data.TestData;
import com.me.metadata.db.entity.TestEntity;
import com.me.metadata.db.mapper.TestMapper;

import java.util.Objects;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@MeComponent(type = ComponentType.TEST, order = 10, desc = "test component")
public class TestComponent extends AbstractComponent {

    private TestEntity testEntity;

    public TestComponent(GameUnit owner) {
        super(owner);
    }

    @Override
    public void prepareData() {
        testEntity = new TestEntity();
    }

    @Override
    public void saveData() {
        final TestMapper testMapper = SpringManager.getBean(TestMapper.class);
        if (Objects.nonNull(testEntity) && testEntity.isUpdate()) {
            testMapper.update(this.testEntity);
            this.testEntity.clean();
        }
    }

    public TestData doTest() {
        TestData testData = new TestData();
        testData.setId(996);
        testData.setValue(IdUtil.getSnowflakeNextIdStr());

        //
        return testData;
    }
}
