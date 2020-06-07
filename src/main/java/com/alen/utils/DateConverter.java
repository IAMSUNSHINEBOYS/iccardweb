package com.alen.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class DateConverter implements Converter<String, Date> {

	private static final DateFormat[] ACCEPT_DATE_FORMATS = {
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm"),
			new SimpleDateFormat("yyyy-MM-dd HH"),
			new SimpleDateFormat("yyyy-MM-dd")};

	@Override
	public Date convert(String value) {
		if (value != null && !value.trim().equals("")) {
			for (DateFormat format : ACCEPT_DATE_FORMATS) {
				try {
					return format.parse(value);
				} catch (Exception e) {
					continue;
				}
			}
		}
		return null;
	}
}
