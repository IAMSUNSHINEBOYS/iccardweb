package com.alen.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * WEB帮助类
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class WebUtils {
	/**
	 * 输出
	 */
	public static void print(HttpServletResponse response, Object str) throws Exception {
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			out.print(str);
		} finally {
			IOClose.close(out);
		}
	}

	/**
	 * 获取请求参数
	 * 
	 * @return
	 */
	public static String getRequestParams(HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();
		String paramStr = "";
		for (Map.Entry<String, String[]> param : params.entrySet()) {
			String key = param.getKey();
			String[] values = param.getValue();
			for (String str : values) {
				if (StringUtils.isEmpty(str))
					continue;
				if (paramStr.length() == 0)
					paramStr = key + "=" + str;
				else
					paramStr += "&" + key + "=" + str;
			}
		}
		return paramStr;
	}

	/**
	 * 判断是不是AJAX请求
	 * 
	 */
	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}

	/**
	 * 获取请求Context路径
	 * 
	 */
	public static String getContextPath(HttpServletRequest request) {
		return getContextPath(request, "");
	}

	/**
	 * 获取请求Context路径
	 * 
	 */
	public static String getContextPath(HttpServletRequest request, String path) {
		return request.getContextPath() + path;
	}

	/**
	 * 获取请求IP地址
	 * 
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip != null && !ip.trim().equals("")) {
			return ip.split(",")[0];
		}
		return request.getRemoteAddr();
	}
}
