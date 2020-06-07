package com.alen.controller;

import com.alen.entity.Admin;
import com.alen.service.AdminService;
import com.alen.shiro.ShiroLogin;
import com.alen.shiro.ShiroUtils;
import com.alen.utils.*;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Date;


/**
 * 用户登录
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Controller
public class LoginController extends BaseController {
	@Autowired
	private Producer producer;
	@Autowired
	private AdminService adminService;
	@Autowired
	private ShiroLogin shiroLogin;

	/**
	 * 登录界面
	 */
	@RequestMapping("/login")
	public String login(ModelMap map) {
		map.put("publicKey", PwdUtils.publicKey);
		TokenUtils.makeTokenToSession();
		return "login_5";
	}

	/**
	 * 登录ajax
	 */
	@RequestMapping("/adminLogin")
	@ResponseBody
	public R adminLogin(HttpServletRequest request, HttpSession session, String checkCode, String publicKey,
						Admin admin, String token) throws Exception {
		if (!(PwdUtils.publicKey.equals(publicKey) && TokenUtils.verifyToken(token)))
			return R.err("页面已过期请重新刷新");
		String randomCode = String.valueOf(ShiroUtils.getSessionAttr("randomCode"));
		if (!randomCode.equalsIgnoreCase(checkCode)) {
			ShiroUtils.removeSessionAttr("randomCode");
			return R.err(1, "验证码错误");
		}
		if (!ShiroUtils.getSubject().isAuthenticated()
				|| !admin.getUsername().equals(ShiroUtils.getAdmin().getUsername())) {
			try {
				String realPwd = PwdUtils.getRealPwd(admin.getPassword());
				UsernamePasswordToken uptoken = new UsernamePasswordToken(admin.getUsername(), realPwd);
				shiroLogin.login(uptoken);
				Admin a = ShiroUtils.getAdmin();
				a.setLastIp(WebUtils.getIp(request));
				a.setLastTime(new Date());
				adminService.saveLoginInfo(a, ShiroUtils.getSessionId());
			} catch (UnknownAccountException e) {
				return R.err("用户名或密码错误");
			} catch (DisabledAccountException e) {
				return R.err("账号已被禁止登录");
			} catch (IncorrectCredentialsException e) {
				return R.err("用户名或密码错误");
			} catch (Exception e) {
				LogUtils.ERROR.error(e.getMessage(), e);
				return R.err("登录失败");
			}
		}
		return R.ok();
	}

	/**
	 * 登录验证码生成
	 */
	@RequestMapping("/randomCode")
	public void randomCode(OutputStream os) throws Exception {
		try {
			// 生成文字验证码
			String text = producer.createText();
			// 生成图片验证码
			BufferedImage image = producer.createImage(text);
			ShiroUtils.setSessionAttr("randomCode", text.toUpperCase());
			ImageIO.write(image, "jpg", os);
		} finally {
			IOClose.close(os);
		}
	}
}
