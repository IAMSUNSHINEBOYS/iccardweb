package com.alen.shiro;

import com.alen.entity.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class ShiroUtils {

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static Admin getAdmin() {
		return (Admin) getSubject().getPrincipal();
	}

	public static Session getSession() {
		return getSubject().getSession();
	}

	public static String getSessionId() {
		return String.valueOf(getSession().getId());
	}

	public static void setSessionAttr(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttr(Object key) {
		return getSession().getAttribute(key);
	}

	public static void removeSessionAttr(Object... key) {
		for (Object k : key)
			getSession().removeAttribute(k);
	}

	public static void logout() {
		getSubject().logout();
	}
}
