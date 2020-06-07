package com.alen.interceptor;


import com.alen.entity.Admin;
import com.alen.entity.SysLog;
import com.alen.shiro.ShiroUtils;
import com.alen.utils.WebUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 系统日志拦截器
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Component
public class SysLogInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute("startTime", System.currentTimeMillis());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Admin admin = ShiroUtils.getAdmin();
		String uri = request.getRequestURI().replaceFirst(
				request.getContextPath(), "");
		long time = System.currentTimeMillis()
				- (Long) request.getAttribute("startTime");
		//sysLogService.saveSysLog(new SysLog(admin.getId(), admin.getName(), admin
				//.getUsername(), new Date(), uri, WebUtils
				//.getRequestParams(request), WebUtils.getIp(request), time));
		//把日志存到mongodb 数据库中
		SysLog sysLog = new SysLog(admin.getId(), admin.getName(), admin
				.getUsername(), new Date(), uri, WebUtils
				.getRequestParams(request), WebUtils.getIp(request), time);


	}

}
