package com.me.common.bit;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public class IndexStatusTest {
    public static void main(String[] args) {
        LongStatus<ESettings> status = LongStatus.newStatus();
        long curStatus = status.statusAdd(ESettings.FREEZE, ESettings.EXAMINED);
        System.out.println(curStatus);
        LongStatus<ESettings> longStatus = LongStatus.newStatus(curStatus);
        System.out.println(longStatus.isStatusTrue(ESettings.FREEZE));
        System.out.println(longStatus.isStatusTrue(ESettings.EXAMINED));
        System.out.println(longStatus.isStatusTrue(ESettings.CONCEAL_MEMBER));
        System.out.println(status.statusOR(ESettings.FREEZE, ESettings.CONCEAL_MEMBER));
        System.out.println(status.statusAND(ESettings.FREEZE, ESettings.CONCEAL_MEMBER));
    }
}
