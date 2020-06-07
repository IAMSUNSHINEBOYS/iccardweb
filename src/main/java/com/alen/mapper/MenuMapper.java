package com.alen.mapper;

import com.alen.entity.MainMenu;
import com.alen.entity.Menu;
import com.alen.entity.UserMenu;

import java.util.List;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 14:01
 */
public interface MenuMapper {
    /**
     * 通过用户ID查找注菜单
     *
     * @param adminId
     *            用户ID
     */
    List<MainMenu> getMainMenuByAdminId(Integer adminId);

    /**
     * 查询全部菜单
     *
     */
    List<Menu> getAllMenu();

    /**
     * 查询用户菜单
     *
     * @param adminId
     *            用户ID
     */
    List<UserMenu> getUserMenuByAdminId(Integer adminId);

    /**
     * 保存菜单
     *
     * @param menu
     *            用户菜单
     */
    int saveMenu(Menu menu);

    /**
     * 更新菜单
     *
     * @param menu
     *            用户菜单
     */
    void updateMenu(Menu menu);

    /**
     * 删除菜单
     *
     * @param id
     *            菜单ID
     */
    void deleteMenuById(Integer id);
    /**
     * 删除角色菜单
     *
     * @param id
     *            菜单ID
     */
    void deleteRoleMenuById(Integer meunId);
}
