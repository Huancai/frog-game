package com.me.game.common.net;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public interface InCmd {

    short TEST = -6666;


    /**
     * ping
     */
    short PING = 1;

    /**
     * 登录
     */
    short LOGIN = 2;

}
