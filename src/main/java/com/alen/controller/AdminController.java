package com.alen.controller;

import com.alen.entity.Admin;
import com.alen.entity.LoginRecord;
import com.alen.entity.UserMenu;
import com.alen.service.AdminService;
import com.alen.service.LoginRecordService;
import com.alen.service.MenuService;
import com.alen.service.RoleService;
import com.alen.utils.MenuUtills;
import com.alen.utils.PwdUtils;
import com.alen.utils.R;
import com.alen.utils.Column;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 * 系统用户
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Controller
@RequestMapping("/admin/admin")
public class AdminController extends BaseController {
	private static final String PRIFIX = "sysManage/admin/";
	@Autowired
	private AdminService adminService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private LoginRecordService loginRecordService;

	/**
	 * 系统用户列表
	 */
	@RequestMapping("/adminList")
	@RequiresPermissions("sys:admin:list")
	public String adminList(ModelMap map, Params p, Admin admin) {
		if (p.getFlag() == 0) {
			map.put("roles", roleService.findAll());
			return PRIFIX + "adminQuery";
		}
		map.put("list", adminService.findAdminList(p.getPager(), admin, p.getDateMap(), p.getField(), p.getOrder()));
		return PRIFIX + "adminList";
	}

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	@RequestMapping("/toExcel")
	@RequiresPermissions("sys:admin:toExcel")
	public void toExcel(HttpServletRequest request, HttpServletResponse response, Params p, Admin admin) {
		String path = getPath(request, "WEB-INF/export/excel/adminList");
		Column columns = new Column();
		columns.addColumn(Admin.class, "username");
		columns.addColumn(Admin.class, "name");
		columns.addColumn(Admin.class, "sex");
		columns.addColumn(Admin.class, "phone");
		columns.addColumn(Admin.class, "lastIp");
		columns.addColumn(Admin.class, "lastTime");
		columns.addColumn(Admin.class, "rolesStr");
		columns.addColumn(Admin.class, "validStr");
		adminService.toExcel(path, "系统用户", admin, p.getDateMap(), p.getField(), p.getOrder(), columns, response);
	}

	/**
	 * 系统用户详情
	 */
	@RequestMapping("/adminDetail")
	@RequiresPermissions("sys:admin:detail")
	public String adminDetail(ModelMap map, int id) {
		map.put("admin", adminService.findAdminAndRoleById(id));
		return PRIFIX + "adminDetail";
	}

	/**
	 * 添加系统用户
	 */
	@RequestMapping("/adminAdd")
	@RequiresPermissions("sys:admin:add")
	public String adminAdd(ModelMap map) {
		map.put("list", roleService.findAll());
		return PRIFIX + "adminAdd";
	}

	/**
	 * 添加系统用户保存
	 */
	@RequestMapping("/adminAddSave")
	@ResponseBody
	@RequiresPermissions("sys:admin:add")
	public R adminAddSave(Admin admin, Integer[] role) {
		if (admin != null) {
			Admin temp = adminService.findAdminByUsername(admin.getUsername());
			if (temp != null)
				return R.err("登录名已存在");
			Admin a = new Admin();
			a.setName(admin.getName());
			a.setUsername(admin.getUsername());
			a.setPhone(admin.getPhone());
			a.setSex(admin.getSex());
			a.setValid(admin.getValid());
			a.setCreateTime(new Date());
			a.setPassword(PwdUtils.getPwd("123456"));
			adminService.saveAdmin(a, role);
			return R.ok("添加成功");
		}
		return R.err("添加失败");
	}

	/**
	 * 修改系统用户
	 */
	@RequestMapping("/adminUpdate")
	@RequiresPermissions("sys:admin:update")
	public String adminUpdate(ModelMap map, int id) {
		map.put("list", roleService.findAll());
		map.put("admin", adminService.findAdminAndRoleById(id));
		return PRIFIX + "adminUpdate";
	}

	/**
	 * 修改系统用户保存
	 */
	@RequestMapping("/adminUpdateSave")
	@ResponseBody
	@RequiresPermissions("sys:admin:update")
	public R adminUpdateSave(Admin admin, Integer[] role) {
		Admin temp = adminService.findAdminAndRoleById(admin.getId());
		Admin a = adminService.findAdminByUsername(admin.getUsername());
		if (temp != null && (a == null || admin.getId().equals(a.getId()))) {
			temp.setUsername(admin.getUsername());
			temp.setName(admin.getName());
			temp.setSex(admin.getSex());
			temp.setPhone(admin.getPhone());
			temp.setValid(admin.getValid());
			adminService.updateAdmin(temp, role);
			return R.ok("修改成功");
		} else if (temp == null)
			return R.err("系统用户不存在");
		else
			return R.err("登录名已存在");
	}

	/**
	 * 密码重置
	 */
	@RequestMapping("/pswReset")
	@ResponseBody
	@RequiresPermissions("sys:admin:pswReset")
	public R pswReset(int id) {
		Admin a = new Admin();
		a.setId(id);
		a.setPassword(PwdUtils.getPwd("123456"));
		adminService.updateAdminById(a);
		return R.ok("密码重置成功");
	}

	/**
	 * 删除系统用户
	 */
	@RequestMapping("/adminDelete")
	@ResponseBody
	@RequiresPermissions("sys:admin:delete")
	public R adminDelete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			adminService.deleteByIds(ids);
			return R.ok("删除成功");
		} else
			return R.err("删除失败");
	}

	/**
	 * 登录记录列表
	 * 
	 * @return
	 */
	@RequestMapping("/loginRecordList")
	@RequiresPermissions("sys:admin:loginRecordList")
	public String loginRecordList(ModelMap map, LoginRecord loginRecord, Params p,
                                  @RequestParam(defaultValue = "0") int adminId) {
		if (p.getFlag() == 0) {
			map.put("adminId", adminId);
			return PRIFIX + "loginRecordQuery";
		}
		loginRecord.setAdminId(adminId);
		map.put("list", loginRecordService.findLoginRecordList(p.getPager(), loginRecord, p.getDateMap(), p.getField(),
				p.getOrder()));
		return PRIFIX + "loginRecordList";
	}

	/**
	 * 检查用户名是否存在
	 */
	@RequestMapping("/checkUserName")
	@ResponseBody
	@RequiresPermissions(value = { "sys:admin:add", "sys:admin:update" }, logical = Logical.OR)
	public boolean checkUserName(int flag, Admin admin) {
		boolean result = false;
		Admin temp = adminService.findAdminByUsername(admin.getUsername());
		if (flag == 0 && temp == null) {// 添加
			result = true;
		} else if (flag == 1 && (temp == null || temp.getId().equals(admin.getId()))) {// 修改
			result = true;
		}
		return result;
	}

	/**
	 * 权限详情
	 */
	@RequestMapping("/adminPerms")
	@RequiresPermissions("sys:admin:perms")
	public String adminPerms(ModelMap map, int id) {
		System.out.println(id);
		List<UserMenu> menus = menuService.selectUserMenuByAdminId(id);
		List<UserMenu> list = MenuUtills.getUserMenuList(menus);
		System.out.println(list);
		map.put("list", list);
		JSONArray jsonaArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (UserMenu m : list) {
			jsonObject = new JSONObject();
			jsonObject.put("id", m.getId());
			jsonObject.put("pId", m.getParent() == null ? 0 : m.getParent());
			jsonObject.put("name", m.getName());
			jsonObject.put("orderNo", m.getOrderNo());
			jsonObject.put("menuType", m.getMenuType());
			jsonObject.put("open", true);
			jsonaArray.add(jsonObject);
		}
		map.put("mtree", jsonaArray.toString());
		return PRIFIX + "adminPerms";
	}
}
