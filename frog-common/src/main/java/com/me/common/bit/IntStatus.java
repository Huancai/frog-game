package com.me.common.bit;

/**
 * @author wu_hc 【whuancai@163.com】
 */
public final class IntStatus {

    /**
     * 状态
     */
    private int status;

    /**
     * @param status
     * @return
     */
    public static IntStatus newWithStatus(final int status) {
        IntStatus m = new IntStatus();
        m.status = status;
        return m;
    }

    /**
     * 重置
     */
    public void reset() {
        status = 0;
    }

    /**
     * 添加状态
     *
     * @param eTypes
     */
    public int statusAdd(IndexStatus... eTypes) {
        for (IndexStatus e : eTypes) {
            status = IntBits.setBitValue(status, e.index(), (byte) 1);
        }
        return status;
    }

    /**
     * 删除状态
     *
     * @param eTypes
     */
    public int statusDel(IndexStatus... eTypes) {
        for (IndexStatus e : eTypes) {
            status = IntBits.setBitValue(status, e.index(), (byte) 0);
        }
        return status;
    }

    /**
     * 状态是否为真
     *
     * @param eType
     * @return
     */
    public boolean isStatusTrue(IndexStatus eType) {
        return IntBits.checkBitValue(status, eType.index());
    }

    /**
     * 与
     *
     * @param eTypes
     * @return
     */
    public boolean statusAND(IndexStatus... eTypes) {
        for (IndexStatus e : eTypes) {
            if (!IntBits.checkBitValue(status, e.index())) {
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
    public boolean statusOR(IndexStatus... eTypes) {
        for (IndexStatus e : eTypes) {
            if (IntBits.checkBitValue(status, e.index())) {
                return true;
            }
        }
        return false;
    }

    public int getStatus() {
        return status;
    }
}
