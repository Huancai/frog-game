package com.me.game.common.cmd;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.me.game.module.misc.data.GamePlayer;
import com.me.transport.api.Message;

/**
 * 请求处理器，实现类需要保障无状态
 *
 * @author wu_hc 【whuancai@163.com】
 */
public abstract class AbstractCMD<T extends GeneratedMessage> {

    private Parser<? extends GeneratedMessage> parser;

    public void setParser(Parser<? extends GeneratedMessage> parser) {
        this.parser = parser;
    }

    /**
     * @param gamePlayer
     * @param message
     * @throws InvalidProtocolBufferException
     */
    public void doExecute(GamePlayer gamePlayer, Message message) throws Exception {
        execute(gamePlayer, (T) parser.parseFrom(message.body()));
    }

    protected abstract void execute(GamePlayer player, T message);
}
