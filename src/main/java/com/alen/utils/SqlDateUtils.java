package com.alen.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * SQL日期处理工具类
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class SqlDateUtils {
	public static Map<String, Date> getDateMap(Map<String, String> dMap) {
		Map<String, Date> dateMap = new HashMap<String, Date>();
		if (dMap != null){
			for (Entry<String, String> en : dMap.entrySet()) {
				String value = en.getValue();
				String key = en.getKey();
				if (value == null || value.trim().equals(""))
					continue;
				if (value != null && key.startsWith("e_"))
					dateMap.put(
							key,
							DateUtils.addDays(
									DateUtils.getDate(value, "yyyy-MM-dd"), 1));
				else
					dateMap.put(key, DateUtils.getDate(value, "yyyy-MM-dd"));
			}
		}
		return dateMap;
	}
}
