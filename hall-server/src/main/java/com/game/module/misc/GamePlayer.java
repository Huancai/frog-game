package com.game.module.misc;

import com.game.core.cmd.MsgSender;
import com.me.core.worker.DefaultWorkerGroup;
import com.me.core.worker.Worker;
import com.me.core.worker.WorkerGroup;
import com.me.metadata.db.entity.PlayerEntity;
import com.me.transport.Message;
import com.me.transport.session.Session;

/**
 * @author wu_hc
 */
public class GamePlayer extends GameUnit {


    final static WorkerGroup workerGroup = DefaultWorkerGroup.newGroup("GAME-PLAYER", Runtime.getRuntime().availableProcessors());


    final Worker worker;

    private Session session;

    private final PlayerEntity playerEntity;

    public GamePlayer(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
        this.worker = workerGroup.next();
    }

    /**
     * 在玩家线程处理
     *
     * @param task
     */
    public void runInLoop(Runnable task) {
        worker.runInLoop(task);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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
        return 0;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }
}
