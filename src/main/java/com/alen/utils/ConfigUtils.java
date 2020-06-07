package com.alen.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置文件
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Component
public class ConfigUtils {
	@Value("${singleLogin:false}")
	private boolean singleLogin; // 是否开启当用户登录true开启false禁用

	public boolean isSingleLogin() {
		return singleLogin;
	}
}
