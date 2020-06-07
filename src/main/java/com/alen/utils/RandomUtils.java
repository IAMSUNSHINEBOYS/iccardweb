package com.alen.utils;

import java.util.Random;

/**
 * 获取随机数
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class RandomUtils {

	/**
	 * 获取一定长度的随机字符串
	 * 
	 * @param length
	 *            指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getRandom(int length) {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 获取长度8的随机字符串
	 * 
	 * @return 长度8的字符串
	 */
	public static String get8Random() {
		return getRandom(8);
	}

	/**
	 * 获取长度32的随机字符串
	 * 
	 * @return 长度32的字符串
	 */
	public static String get32Random() {
		return getRandom(32);
	}

	public static void main(String[] args) {
		System.out.println(getRandom(32));
	}
}
