package com.me.game.module.misc.data;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import com.me.game.common.cmd.MsgSender;
import com.me.game.common.manager.ManagerInit;
import com.me.game.common.manager.SpringManager;
import com.me.game.middleware.component.AbstractComponent;
import com.me.game.middleware.component.MeComponent;
import com.me.common.worker.DefaultWorkerGroup;
import com.me.common.worker.Worker;
import com.me.common.worker.WorkerGroup;
import com.me.metadata.db.entity.PlayerEntity;
import com.me.transport.api.Message;
import com.me.transport.api.session.Session;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.Set;

/**
 * @author wu_hc
 */
@Slf4j
public class GamePlayer extends GameUnit {

    final static WorkerGroup workerGroup = DefaultWorkerGroup.newGroup("GAME-PLAYER", Runtime.getRuntime().availableProcessors() << 1);

    final Worker worker;

    private final Session session;

    private final PlayerEntity playerEntity;

    public GamePlayer(PlayerEntity playerEntity, Session session) {
        this.playerEntity = playerEntity;
        this.session = session;
        this.worker = workerGroup.next();
    }

    public void login() {

    }

    public void logout() {

    }

    @Override
    public void initComponent() {

        StopWatch stopWatch = StopWatch.create(String.format("player:%s initComponent！", info()));
        Set<Class<? extends AbstractComponent>> componentClasses = SpringManager.getBean(ManagerInit.class).getComponentClasses();
        for (Class<? extends AbstractComponent> componentClass : componentClasses) {
            try {
                MeComponent meComponent = componentClass.getAnnotation(MeComponent.class);
                stopWatch.start(String.format("component:%s", meComponent.type()));
                Constructor<? extends AbstractComponent> constructor = componentClass.getConstructor(GameUnit.class);
                AbstractComponent component = constructor.newInstance(this);
                component.prepareData();
                component.afterPrepareData();
                addComponent(meComponent.type(), component);
                stopWatch.stop();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("player:{} initComponent error", info(), e);
            }
        }

        log.info(stopWatch.prettyPrint());
    }

    /**
     * 在玩家线程处理
     *
     * @param task
     */
    public void runInLoop(Runnable task) {
        worker.runInLoop(task);
    }


    public void send(Message message) {
        MsgSender.send(message, session);
    }

    public void send(short code, com.google.protobuf.GeneratedMessage generatedMessage) {
        MsgSender.send(code, generatedMessage, session);
    }

    public void send(short code, com.google.protobuf.GeneratedMessage.Builder<?> builder) {
        MsgSender.send(code, builder, session);
    }

    public long getId() {
        return this.getPlayerEntity().getPlayerId();
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public String info() {
        return StrUtil.format("player:{} - {}", playerEntity.getPlayerId(), playerEntity.getPlayerName());
    }

    public Worker getWorker() {
        return worker;
    }
}
