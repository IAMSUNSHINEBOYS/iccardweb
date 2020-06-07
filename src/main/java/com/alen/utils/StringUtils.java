package com.alen.utils;

/**
 * 字符串工具类
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class StringUtils {
	
	/**
	 * 判断是否为车牌
	 * @param carNo 车牌号
	 * @return
	 */
	public static boolean isCarNo(String carNo) {
		if (carNo == null) {
			return false;
		}
		String regex = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))"
				    + "|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1})";
		return carNo.matches(regex);
	}

	public static boolean isMobile(String phone) {
		return phone != null && phone.matches("[1][3456789][0-9]{9}$");
	}
	
	public static String formatPrefix(String str, String pre, int len) {
		if (str != null && str.length() < len) {
			while (str.length() < len) {
				str = pre + str;
			}
		}
		return str;
	}

	public static String formatSuffix(String str, String suf, int len) {
		if (str != null && str.length() < len) {
			while (str.length() < len) {
				str += suf;
			}
		}
		return str;
	}

	public static boolean isEmpty(Object str) {
		return str == null || "".equals(str);
	}

	public static boolean isBlank(Object str) {
		return str == null || (str + "").trim().equals("");
	}
}
