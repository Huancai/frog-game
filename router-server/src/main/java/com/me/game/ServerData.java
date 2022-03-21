package com.me.game;

import lombok.Data;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Data
public class ServerData {

    /**
     * 服务id
     */
    private int id;

    /**
     * 域
     */
    private String host;
    /**
     * 端口
     */
    private int port;

    /**
     * 权重
     */
    private int weight;

    /**
     * 总量
     */
    private int totalCnt;

    /**
     * 在线数量
     */
    private int onlineCnt;
}
