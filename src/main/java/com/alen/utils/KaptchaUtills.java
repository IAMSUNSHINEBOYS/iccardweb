package com.alen.utils;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 生成验证码
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Configuration
public class KaptchaUtills {
	@Bean
	public static DefaultKaptcha producer() {
		Properties properties = new Properties();
		properties.put(Constants.KAPTCHA_BORDER, "no");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "red");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "44");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "10");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789");
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM, "white");
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO, "white");
		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
