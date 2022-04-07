package com.me.common.bit;
/**
 * @author wu_hc 【whuancai@163.com】
 */
public final class LongBits {

	public static long setBitValue(long source, int pos, byte value) {

		long mask = (1L << pos);
		if (value > 0) {
			source |= mask;
		} else {
			source &= (~mask);
		}

		return source;
	}

	static long reverseBitValue(long source, int pos) {
		long mask = 1L << pos;
		return (source ^ mask);
	}

	public static boolean checkBitValue(long source, int pos) {

		source = (source >>> pos);

		return (source & 1L) == 1;
	}

}
