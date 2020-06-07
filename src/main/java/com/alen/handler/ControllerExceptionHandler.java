package com.alen.handler;

import com.alen.utils.LogUtils;
import com.alen.utils.R;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Controller异常处理
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(AuthorizationException.class)
	@ResponseBody
	public R handleAuthorizationException(AuthorizationException e) {
		return R.err(-2,"权限不足");
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public R handleException(Exception e) {
		LogUtils.ERROR.error(e.getMessage(), e);
		return R.err("异常");
	}
}
