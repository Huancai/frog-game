package com.me.game.module.ping.data;


import com.me.metadata.pb.misc.MiscMsgProto;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public class PongData {
    public final static MiscMsgProto.PongProto.Builder PONG_BUILDER;
    static {
        PONG_BUILDER = MiscMsgProto.PongProto.newBuilder();
    }
}
