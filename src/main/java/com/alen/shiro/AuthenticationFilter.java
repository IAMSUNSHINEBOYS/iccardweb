package com.alen.shiro;


import com.alen.entity.Admin;
import com.alen.service.AdminService;
import com.alen.utils.WebUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Shiro认证过滤器
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Component
public class AuthenticationFilter extends FormAuthenticationFilter {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRealm adminRealm;

    @Override
    public boolean onPreHandle(ServletRequest request,
                               ServletResponse response, Object mappedValue) throws Exception {
        if (ShiroUtils.getSubject().isAuthenticated()) {
            Admin currentAdmin = ShiroUtils.getAdmin();
            Admin admin = adminService.findAdminById(currentAdmin.getId());
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            // 用户不存在或者是否禁用登录
            if (admin == null || !"1".equals(admin.getValid())
                    && WebUtils.isAjax(servletRequest)) {// 处理AJAX请求
                servletResponse.setHeader("status", "disable");
                servletResponse.setStatus(800);
                servletResponse.setHeader("redirectUrl",
                        WebUtils.getContextPath(servletRequest, getLoginUrl()));
                ShiroUtils.logout();
                return false;
            } else if (admin == null || !"1".equals(admin.getValid())) {// 处理网页请求
                this.saveRequestAndRedirectToLogin(request, response);
                ShiroUtils.logout();
                return false;
            } else {
                adminRealm.clearAuthorizationCached();// 清除授权缓存
            }
        }
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            if (WebUtils.isAjax(servletRequest)) {// 处理AJAX请求
                servletResponse.setHeader("status", "timeout");
                servletResponse.setStatus(800);
                servletResponse.setHeader("redirectUrl",
                        WebUtils.getContextPath(servletRequest, getLoginUrl()));
            } else {// 处理网页请求
                this.saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }
}
