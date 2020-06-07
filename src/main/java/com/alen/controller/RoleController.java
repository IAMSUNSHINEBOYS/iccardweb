package com.alen.controller;


import com.alen.entity.Menu;
import com.alen.entity.Role;
import com.alen.service.AdminService;
import com.alen.service.RoleService;
import com.alen.utils.MenuUtills;
import com.alen.utils.R;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 角色权限
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {
	private static final String PRIFIX = "sysManage/role/";
	@Autowired
	private RoleService roleService;
	@Autowired
	private AdminService adminService;

	/**
	 * 角色权限查询
	 */
	@RequestMapping("/roleList")
	@RequiresPermissions("sys:role:list")
	public String roleList(ModelMap map, Params p, Role role, Integer adminId) {
		if (p.getFlag() == 0) {
			map.put("list", adminService.findAdminsAll());
			return PRIFIX + "roleQuery";
		}
		map.put("list",
				roleService.findRoleList(p.getPager(), role, p.getField(),
						p.getOrder()));
		return PRIFIX + "roleList";
	}

	/**
	 * 添加角色
	 */
	@RequestMapping("/roleAdd")
	@RequiresPermissions("sys:role:add")
	public String roleAdd(ModelMap map) {
		List<Menu> list = MenuUtills.getMenuList();
		map.put("list", list);
		JSONArray jsonaArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", 0);
		jsonObject.put("pId", -1);
		jsonObject.put("name", "功能菜单");
		jsonObject.put("open", true);
		jsonaArray.add(jsonObject);
		for (Menu m : list) {
			jsonObject = new JSONObject();
			jsonObject.put("id", m.getId());
			jsonObject.put("pId", m.getParent() == null ? 0 : m.getParent());
			jsonObject.put("name", m.getName());
			jsonObject.put("perms", m.getPerms());
			jsonObject.put("orderNo", m.getOrderNo());
			jsonObject.put("menuType", m.getMenuType());
			jsonObject.put("menuUrl", m.getMenuUrl());
			jsonObject.put("micon", m.getIcon());
			jsonObject.put("open", true);
			jsonaArray.add(jsonObject);
		}
		map.put("mtree", jsonaArray.toString());
		return PRIFIX + "roleAdd";
	}

	/**
	 * 添加角色保存
	 */
	@RequestMapping("/roleAddSave")
	@ResponseBody
	@RequiresPermissions("sys:role:add")
	public R roleAddSave(Role role, Integer[] menu) {
		Role temp = roleService.findRoleByName(role.getName());
		if (temp == null) {
			roleService.saveRole(role, menu);
			return R.ok("添加成功");
		} else
			return R.err("角色名称已存在");
	}

	/**
	 * 修改角色
	 */
	@RequestMapping("/roleUpdate")
	@RequiresPermissions("sys:role:update")
	public String roleUpdate(ModelMap map, int id) {
		Role role = roleService.findRoleById(id);
		Set<Integer> mIds = new HashSet<Integer>();
		for (Integer m : role.getMenus())
			mIds.add(m);
		map.put("role", role);
		List<Menu> list = MenuUtills.getMenuList();
		map.put("list", list);
		JSONArray jsonaArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", 0);
		jsonObject.put("pId", -1);
		jsonObject.put("name", "功能菜单");
		jsonObject.put("open", true);
		jsonaArray.add(jsonObject);
		for (Menu m : list) {
			jsonObject = new JSONObject();
			jsonObject.put("id", m.getId());
			jsonObject.put("pId", m.getParent() == null ? 0 : m.getParent());
			jsonObject.put("name", m.getName());
			jsonObject.put("perms", m.getPerms());
			jsonObject.put("orderNo", m.getOrderNo());
			jsonObject.put("menuType", m.getMenuType());
			jsonObject.put("menuUrl", m.getMenuUrl());
			jsonObject.put("micon", m.getIcon());
			jsonObject.put("open", true);
			if (mIds.contains(m.getId()))
				jsonObject.put("checked", true);
			jsonaArray.add(jsonObject);
		}
		map.put("mtree", jsonaArray.toString());
		return PRIFIX + "roleUpdate";
	}

	/**
	 * 修改角色保存
	 */
	@RequestMapping("/roleUpdateSave")
	@ResponseBody
	@RequiresPermissions("sys:role:update")
	public R roleUpdateSave(Role role, Integer[] menu) {
		Role temp = roleService.findRoleById(role.getId());
		Role r = roleService.findRoleByName(role.getName());
		if (temp != null && (r == null || role.getId().equals(r.getId()))) {
			roleService.updateRole(role, menu);
			return R.ok("修改成功");
		} else if (temp == null)
			return R.err("角色不存在");
		else
			return R.err("角色名称已存在");
	}

	/**
	 * 删除角色
	 */
	@RequestMapping("/roleDelete")
	@ResponseBody
	@RequiresPermissions("sys:role:delete")
	public R roleDelete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			roleService.deleteRoleById(ids);
			return R.ok("删除成功");
		} else
			return R.err("删除失败");
	}

	/**
	 * 检查角色名是否存在
	 */
	@RequestMapping("/checkRoleName")
	@ResponseBody
	@RequiresPermissions(value = { "sys:role:add", "sys:role:update" }, logical = Logical.OR)
	public boolean checkRoleName(int flag, Role role) {
		boolean result = false;
		Role temp = roleService.findRoleByName(role.getName());
		if (flag == 0 && temp == null) {// 添加
			result = true;
		} else if (flag == 1
				&& (temp == null || temp.getId().equals(role.getId()))) {// 修改
			result = true;
		}
		return result;
	}
}
