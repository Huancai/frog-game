package com.me.common.bit;
/**
 * @author wu_hc 【whuancai@163.com】
 */
public final class IntBits {

    public static byte getBitValue(byte source, int pos) {
        return (byte) ((source >> pos) & 1);
    }

    public static int setBitValue(int source, int pos, byte value) {

        int mask = (int) (1 << pos);
        if (value > 0) {
            source |= mask;
        } else {
            source &= (~mask);
        }

        return source;
    }

    public static int reverseBitValue(int source, int pos) {
        int mask = (int) (1 << pos);
        return (int) (source ^ mask);
    }

    public static boolean checkBitValue(int source, int pos) {

        source = (int) (source >>> pos);

        return (source & 1) == 1;
    }

}
