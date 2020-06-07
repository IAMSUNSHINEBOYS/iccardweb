package com.alen.shiro;

import com.alen.entity.Admin;
import com.alen.service.AdminService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 用户认证及授权
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class AdminRealm extends AuthorizingRealm {
	@Autowired
	private AdminService adminService;

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		Admin a = adminService.findAdminByUsername(username);
		if (a == null) {// 用户名不存在
			throw new UnknownAccountException();
		}
		if (!"1".equals(a.getValid())) {// 账号已被禁止登录
			throw new DisabledAccountException();
		}
		String password = a.getPassword();
		ByteSource credentialsSalt = ByteSource.Util.bytes(password.substring(
				0, 8));
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(a,
				password.substring(8), credentialsSalt, getName());
		return info;
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Admin a = ((Admin) principals.getPrimaryPrincipal());
		Set<String> perms = adminService.selectPermsByAdminId(a.getId());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}

	/**
	 * 清除授权缓存
	 */
	public void clearAuthorizationCached() {
		clearCachedAuthorizationInfo(ShiroUtils.getSubject().getPrincipals());
	}
}
