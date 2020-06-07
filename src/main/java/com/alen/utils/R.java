package com.alen.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class R extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	private R() {
		put("status", 0);
	}

	public static R err(String msg) {
		return err(-1, msg);
	}

	public static R err(int status, String msg) {
		R result = new R();
		result.put("status", status);
		result.put("msg", msg);
		return result;
	}

	public static R ok(String msg) {
		R result = new R();
		result.put("msg", msg);
		return result;
	}

	public static R ok(Map<String, Object> map) {
		R result = new R();
		result.putAll(map);
		return result;
	}

	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
