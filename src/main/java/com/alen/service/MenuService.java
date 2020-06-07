package com.alen.service;

import com.alen.mapper.MenuMapper;
import com.alen.entity.MainMenu;
import com.alen.entity.Menu;

import com.alen.entity.UserMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户菜单
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/16 16:53
 */
@Service
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 通过用户ID查找菜单
     *
     * @param adminId
     *            用户ID
     */
    public List<MainMenu> selectMainMenuByAdminId(Integer adminId) {
        return menuMapper.getMainMenuByAdminId(adminId);
    }

    /**
     * 查询全部菜单
     *
     */
    public List<Menu> findAllMenu() {
        return menuMapper.getAllMenu();
    }

    /**
     * 查询用户菜单
     *
     * @param adminId
     *            用户ID
     */
    public List<UserMenu> selectUserMenuByAdminId(Integer adminId) {
        return menuMapper.getUserMenuByAdminId(adminId);
    }

    /**
     * 保存菜单
     *
     * @param menu
     *            用户菜单
     */
    public void saveMenu(Menu menu) {
        menuMapper.saveMenu(menu);
    }

    /**
     * 更新菜单
     *
     * @param menu
     *            用户菜单
     */
    public void updateMenu(Menu menu) {
        menuMapper.updateMenu(menu);
    }

    /**
     * 删除菜单
     *
     * @param id
     *            菜单ID
     */
    public void deleteMenuById(Integer id) {
        menuMapper.deleteRoleMenuById(id);
        menuMapper.deleteMenuById(id);
    }
}
