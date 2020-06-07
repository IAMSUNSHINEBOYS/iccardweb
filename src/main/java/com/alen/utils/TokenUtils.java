package com.alen.utils;


import com.alen.shiro.ShiroUtils;

/**
 * token生产与验证
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class TokenUtils {
	/*
	 * 生产token放到session
	 */
	public static void makeTokenToSession() {
		ShiroUtils.setSessionAttr("token", RandomUtils.get32Random());
	}

	/**
	 * 验证token
	 * 
	 * @param token
	 *            待验证的token
	 */
	public static boolean verifyToken(String token) {
		return token.equals(ShiroUtils.getSessionAttr("token"));
	}
}
