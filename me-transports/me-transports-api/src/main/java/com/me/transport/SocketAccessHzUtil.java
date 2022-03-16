package com.me.transport;

/**
 * 网络访问频率控制
 * 
 * @author wu_hc
 */
public final class SocketAccessHzUtil {
	/**
	 * 检测频率(毫秒)
	 */
	private static final int SESSION_HZ_MS = 10 * 1000;

	/**
	 * 请求频率(每SESSION_HZ_MS秒最高链接次数)
	 */
	private static final int SESSION_LINK_HZ = 300;

	/**
	 * 
	 */
	private long lastHzFlushTime;

	/**
	 * 访问记数器
	 */
	private int accessCounter = 0;

	/**
	 * 检测请求频率
	 * 
	 * @return
	 */
	public boolean checkIsNormalHz(long accessTime) {
		if (accessTime - lastHzFlushTime > SESSION_HZ_MS) {
			lastHzFlushTime = accessTime;
			accessCounter = 1;
		} else {
			accessCounter++;
		}
		if (accessCounter > SESSION_LINK_HZ) {
			return false;
		}
		return true;
	}

}
