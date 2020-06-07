package com.alen.controller;

import com.alen.entity.Admin;
import com.alen.service.AdminService;
import com.alen.utils.FileNameUtils;
import com.alen.utils.PwdUtils;
import com.alen.utils.R;
import com.alen.utils.TokenUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;


/**
 * 个人设置
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Controller
@RequestMapping("/admin/person")
public class PersonController extends BaseController {
	private static final String PRIFIX = "person/person/";
	@Autowired
	private AdminService adminService;

	/**
	 * 个人资料修改
	 */
	@RequestMapping("/infoUpdate")
	@RequiresPermissions("sys:person:infoUpdate")
	public String infoUpdate(ModelMap map, HttpSession session) {
		map.put("admin", adminService.findAdminById(getOperator().getId()));
		return PRIFIX + "infoUpdate";
	}

	/**
	 * 个人资料修改保存
	 */
	@RequestMapping("/infoUpdateSave")
	@ResponseBody
	@RequiresPermissions("sys:person:infoUpdate")
	public R infoUpdateSave(HttpSession session, Admin admin) {
		Admin temp = new Admin();
		temp.setId(getOperator().getId());
		temp.setPhone(admin.getPhone());
		temp.setSex(admin.getSex());
		adminService.updateAdminById(temp);
		return R.ok("修改成功");
	}

	/**
	 * 修改系统用户密码
	 */
	@RequestMapping("/pswUpdate")
	@RequiresPermissions("sys:person:pswUpdate")
	public String pswUpdate(ModelMap map) {
		map.put("admin", adminService.findAdminById(getOperator().getId()));
		map.put("publicKey", PwdUtils.publicKey);
		TokenUtils.makeTokenToSession();
		return PRIFIX + "pswUpdate";
	}

	/**
	 * 修改系统用户密码保存
	 */
	@RequestMapping("/pswUpdateSave")
	@ResponseBody
	@RequiresPermissions("sys:person:pswUpdate")
	public R pswUpdateSave(HttpSession session, String oldPassword,
			String newPassword, String publicKey, String token)
			throws Exception {
		if (!(PwdUtils.publicKey.equals(publicKey) && TokenUtils
				.verifyToken(token)))
			return R.err("页面已过期请重新刷新");
		String realOldPwd = PwdUtils.getRealPwd(oldPassword);
		String realNewPwd = PwdUtils.getRealPwd(newPassword);
		Admin a = adminService.findAdminById(getOperator().getId());
		if (!PwdUtils.verifyPwd(realOldPwd, a.getPassword()))
			return R.err("旧密码不正确");
		Admin temp = new Admin();
		temp.setId(a.getId());
		temp.setPassword(PwdUtils.getPwd(realNewPwd));
		adminService.updateAdminById(temp);
		return R.ok("修改成功");
	}

	/**
	 * 检查用户当前密码
	 */
	@RequestMapping("/checkCurrentPsw")
	@ResponseBody
	@RequiresPermissions("sys:person:pswUpdate")
	public R checkCurrentPsw(HttpSession session, String oldPassword,
			String publicKey, String token) throws Exception {
		if (!(PwdUtils.publicKey.equals(publicKey) && TokenUtils
				.verifyToken(token)))
			return R.err("页面已过期请重新刷新");
		String realOldPwd = PwdUtils.getRealPwd(oldPassword);
		Admin a = adminService.findAdminById(getOperator().getId());
		if (PwdUtils.verifyPwd(realOldPwd, a.getPassword())) {
			return R.ok();
		}
		return R.err("当前密码不正确");
	}

	/**
	 * 修改头像
	 */
	@RequestMapping("/headImgUpdate")
	@RequiresPermissions("sys:person:headImgUpdate")
	public String headImgUpdate(ModelMap map) {
		map.put("admin", adminService.findAdminById(getOperator().getId()));
		return PRIFIX + "headImgUpdate";
	}

	/**
	 * 修改头像上传
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequestMapping("/headImgUpdateUpload")
	@ResponseBody
	@RequiresPermissions("sys:person:headImgUpdate")
	public R headImgUpdateUpload(HttpServletRequest request, MultipartFile file)
			throws Exception {
		if (file == null || file.isEmpty())
			return R.err("上传的头像失败");
		String fileFileName = file.getOriginalFilename();
		String extName = fileFileName.substring(fileFileName.lastIndexOf("."));
		String fileName = FileNameUtils.getNewFileName(extName);
		if (!FileNameUtils.checkIsImage(extName))
			return R.err("上传的头像格式不正确");
		file.transferTo(new File(getPath(request) + "/heads", fileName));
		return R.ok("上传成功")
				.put("path", getContextPath(request) + "/heads/" + fileName)
				.put("imgName", fileName);
	}

	/**
	 * 修改头像保存
	 */
	@RequestMapping("/headImgUpdateSave")
	@ResponseBody
	@RequiresPermissions("sys:person:headImgUpdate")
	public R headImgUpdateSave(String fileFileName) {
		Admin a = new Admin();
		a.setId(getOperator().getId());
		a.setHeadImg(fileFileName);
		adminService.updateAdminById(a);
		return R.ok("修改成功");
	}
}
