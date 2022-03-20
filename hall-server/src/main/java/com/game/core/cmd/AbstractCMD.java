package com.game.core.cmd;

import com.game.module.misc.data.GamePlayer;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.me.transport.Message;

/**
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
    public void doExecute(GamePlayer gamePlayer, Message message) throws InvalidProtocolBufferException {
        execute(gamePlayer, (T) parser.parseFrom(message.body()));
    }

    protected abstract void execute(GamePlayer player, T message);
}
