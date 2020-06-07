package com.alen.controller;


import com.alen.entity.Menu;
import com.alen.service.AdminService;
import com.alen.service.MenuService;
import com.alen.service.RoleService;
import com.alen.shiro.ShiroUtils;
import com.alen.utils.MenuUtills;
import com.alen.utils.R;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 菜单
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {
	private static final String PRIFIX = "sysManage/menu/";
	@Autowired
	private MenuService menuService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private RoleService roleService;

	/**
	 * 菜单列表
	 */
	@RequestMapping("/menuList")
	@RequiresPermissions("sys:menu:list")
	public String menuList(ModelMap map) {
		List<Menu> list = MenuUtills.getMenuList();
		map.put("list", list);
		JSONArray jsonaArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
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
			jsonObject.put("enable", m.getEnable());
			jsonaArray.add(jsonObject);
		}
		boolean hasPer = false;
		for (boolean per : ShiroUtils.getSubject().isPermitted("sys:menu:add",
				"sys:menu:update", "sys:menu:delete")) {
			hasPer = per;
			if (per)
				break;
		}
		map.put("hasPer", hasPer);
		map.put("mtree", jsonaArray.toString());
		return PRIFIX + "menuList";
	}

	/**
	 * 菜单的添加
	 */
	@RequestMapping("/menuAdd")
	@ResponseBody
	@RequiresPermissions("sys:menu:add")
	public R moduleAdd(Menu menu) {
		if (menu.getParent() == null || menu.getParent() <= 0) {
			menu.setParent(null);
		}
		menuService.saveMenu(menu);
		return R.ok("添加成功");
	}

	/**
	 * 菜单的修改
	 */
	@RequestMapping("/menuUpdate")
	@ResponseBody
	@RequiresPermissions("sys:menu:update")
	public R menuUpdate(Menu menu) {
		if (menu.getParent() != null && (menu.getParent() < 1)) {
			menu.setParent(null);
		} else {
			if (menu.getId().equals(menu.getParent()))
				return R.err("父ID和子ID不能相同");
			menu.setParent(menu.getParent());
		}
		menuService.updateMenu(menu);
		return R.ok("修改成功");
	}

	/**
	 * 菜单的删除
	 */
	@RequestMapping("/menuDelete")
	@ResponseBody
	@RequiresPermissions("sys:menu:delete")
	public R menuDelete(Integer id) {
		menuService.deleteMenuById(id);
		return R.ok("删除成功");
	}

	/**
	 * 功能对应的用户信息
	 */
	@RequestMapping("/menuAdminInfo")
	@RequiresPermissions("sys:menu:list")
	public String menuAdminInfo(ModelMap map, Params p,
			@ModelAttribute("id") Integer id) {
		p.getPager().setPageSize(10);
		map.put("list", adminService.findAdminsByMeunId(id));
		if (p.getFlag() == 0)
			return PRIFIX + "menuAdmin";
		return PRIFIX + "menuAdminList";
	}

	/**
	 * 功能对应的角色信息
	 */
	@RequestMapping("/menuRoleInfo")
	@RequiresPermissions("sys:menu:list")
	public String menuRoleInfo(ModelMap map, Params p,
			@ModelAttribute("id") Integer id) {
		p.getPager().setPageSize(10);
		map.put("list", roleService.findRolesByMeunId(id));
		if (p.getFlag() == 0)
			return PRIFIX + "menuRole";
		return PRIFIX + "menuRoleList";
	}
}
