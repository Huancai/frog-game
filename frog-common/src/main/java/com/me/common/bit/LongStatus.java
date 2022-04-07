package com.me.common.bit;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public final class LongStatus<E extends IndexStatus> {

    /**
     * 状态
     */
    private long status;

    public static LongStatus newStatus() {
        return newStatus(0L);
    }

    /**
     * @param status
     * @return
     */
    public static LongStatus newStatus(final long status) {
        LongStatus m = new LongStatus();
        m.status = status;
        return m;
    }

    /**
     * 重置
     */
    public void reset() {
        status = 0;
    }

    public void reset(long newSettings) {
        status = newSettings;
    }

    /**
     * 添加状态
     *
     * @param eTypes
     */
    public long statusAdd(E... eTypes) {
        for (E e : eTypes) {
            status = LongBits.setBitValue(status, e.index(), (byte) (1));
        }
        return status;
    }

    /**
     * 删除状态
     *
     * @param eTypes
     */
    public long statusDel(E... eTypes) {
        for (E e : eTypes) {
            status = LongBits.setBitValue(status, e.index(), (byte) 0);
        }
        return status;
    }

    /**
     * 状态是否为真
     *
     * @param eType
     * @return
     */
    public boolean isStatusTrue(E eType) {
        return LongBits.checkBitValue(status, eType.index());
    }

    /**
     * 与
     *
     * @param eTypes
     * @return
     */
    public boolean statusAND(E... eTypes) {
        for (E e : eTypes) {
            if (!LongBits.checkBitValue(status, e.index())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 或
     *
     * @param eTypes
     * @return
     */
    public boolean statusOR(E... eTypes) {
        for (E e : eTypes) {
            if (LongBits.checkBitValue(status, e.index())) {
                return true;
            }
        }
        return false;
    }

    public long getStatus() {
        return status;
    }

    public int convertInt() {
        return (int) status;
    }
}
