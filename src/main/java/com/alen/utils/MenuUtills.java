package com.alen.utils;


import com.alen.entity.MainMenu;
import com.alen.entity.Menu;
import com.alen.entity.UserMenu;
import com.alen.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Component
public class MenuUtills {
	private static MenuService menuService;

	@Autowired
	public void setModuleService(MenuService menuService) {
		MenuUtills.menuService = menuService;
	}

	/**
	 * 菜单
	 */
	public static List<Menu> getMenuList() {
		List<Menu> list = menuService.findAllMenu();
		List<Menu> topList = new ArrayList<Menu>();
		List<Menu> childList = new ArrayList<Menu>();
		List<Menu> listdest = new ArrayList<Menu>();
		for (Menu m : list) {
			if (m.getParent() == null)
				topList.add(m);
			else
				childList.add(m);
		}
		for (Menu m : topList) {
			listdest.add(m);
			m.setLevel(1);
			recur(childList, 1, listdest, m.getId());
		}
		return listdest;
	}

	/**
	 * 菜单递归
	 */
	private static void recur(List<Menu> listsrc, int level,
			List<Menu> listdest, Integer parentId) {
		level++;
		for (Menu m : listsrc) {
			if (parentId.equals(m.getParent())) {
				listdest.add(m);
				m.setLevel(level);
				recur(listsrc, level, listdest, m.getId());
			}
		}
	}

	/**
	 * 主菜单
	 * 
	 * @param adminId
	 *            用户ID
	 * @return
	 */
	public static List<MainMenu> getMainMenuList(int adminId) {
		List<MainMenu> list = menuService.selectMainMenuByAdminId(adminId);
		List<MainMenu> topList = new ArrayList<MainMenu>();
		List<MainMenu> childList = new ArrayList<MainMenu>();
		for (MainMenu m : list) {
			if ("1".equals(m.getMenuType()))
				topList.add(m);
			else
				childList.add(m);
		}
		for (MainMenu m : topList) {
			recur(childList, m, m.getId());
		}
		return topList;
	}

	/**
	 * 主菜单递归
	 */
	private static void recur(List<MainMenu> childList, MainMenu topMenu,
			Integer parentId) {
		for (MainMenu m : childList) {
			if (m.getParent().equals(parentId)) {
				topMenu.getChildMenus().add(m);
				recur(childList, topMenu, m.getId());
			}
		}
	}

	/**
	 * 用户菜单
	 * 
	 * @param list
	 *            菜单列表
	 * @return
	 */
	public static List<UserMenu> getUserMenuList(List<UserMenu> list) {
		List<UserMenu> topList = new ArrayList<UserMenu>();
		List<UserMenu> childList = new ArrayList<UserMenu>();
		List<UserMenu> listdest = new ArrayList<UserMenu>();
		for (UserMenu m : list) {
			if (m.getParent() == null)
				topList.add(m);
			else
				childList.add(m);
		}
		for (UserMenu m : topList) {
			listdest.add(m);
			m.setLevel(1);
			recurUser(childList, 1, listdest, m.getId());
		}
		return listdest;
	}

	/**
	 * 用户权限递归
	 */
	private static void recurUser(List<UserMenu> listsrc, int level,
			List<UserMenu> listdest, Integer parentId) {
		level++;
		for (UserMenu m : listsrc) {
			if ((m.getParent().equals(parentId))) {
				listdest.add(m);
				m.setLevel(level);
				recurUser(listsrc, level, listdest, m.getId());
			}
		}
	}
}
