package com.alen.shiro;

import com.alen.entity.Admin;
import com.alen.utils.ConfigUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * shiro 登录管理
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Component
public class ShiroLogin {
	@Autowired
	private ConfigUtils configUtils;
	@Autowired
	private SessionDAO sessionDAO;

	/**
	 * 用户登录
	 * 
	 * @param token
	 *            登录信息token
	 */
	public void login(UsernamePasswordToken token) {
		ShiroUtils.getSubject().login(token);
		ShiroUtils.removeSessionAttr("randomCode", "token");
		if (configUtils.isSingleLogin()) {
			singleAdminLogin();
		}
	}

	/**
	 * 单用户登录(踢用户)
	 */
	private void singleAdminLogin() {
		Integer adminId = ShiroUtils.getAdmin().getId();
		String sessionId = ShiroUtils.getSessionId();
		for (Session session : sessionDAO.getActiveSessions()) {
			SimplePrincipalCollection principals = (SimplePrincipalCollection) session
					.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if (principals != null) {
				Admin admin = (Admin) principals.getPrimaryPrincipal();
				if (adminId.equals(admin.getId()) && !session.getId().equals(sessionId)) {
					session.stop();
				}
			}
		}
	}
}
