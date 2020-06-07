package com.alen.service;


import com.alen.mapper.RoleMapper;
import com.alen.entity.Role;
import com.alen.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/16 16:56
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 通过菜单ID查找对应的角色
     *
     * @param menuId
     *            菜单ID
     * @return
     */
    public List<Role> findRolesByMeunId(Integer menuId) {
        return roleMapper.getRolesByMeunId(menuId);
    }

    /**
     * 查询角色列表
     *
     * @param pager
     *            分页
     * @param role
     *            角色对象
     * @return
     */
    public List<Role> findRoleList(Pager pager, Role role, String field,
                                   int order) {
        pager.addSortable("name").addOrder(field, order).startPage();
        return roleMapper.getRoleList(role);
    }

    /**
     * 通过角色名称查询
     *
     * @param name
     *            角色名称
     * @return
     */
    public Role findRoleByName(String name) {
        return roleMapper.getRoleByName(name);
    }

    /**
     * 保存角色
     *
     * @param role
     *            角色
     * @param menu
     *            角色拥有的菜单
     * @return
     */
    public void saveRole(Role role, Integer[] menu) {
        roleMapper.saveRole(role);
        if (menu != null) {
            for (Integer m : menu) {
                roleMapper.saveRoleMenu(role.getId(), m);
            }
        }
    }

    /**
     * 通过角色Id查询
     *
     * @param id
     *            角色Id
     * @return
     */
    public Role findRoleById(Integer id) {
        return roleMapper.getRoleById(id);
    }

    /**
     * 更新角色
     *
     * @param role
     *            角色
     * @param menu
     *            角色拥有的菜单
     * @return
     */
    public void updateRole(Role role, Integer[] menu) {
        roleMapper.updateRoleById(role);
        roleMapper.deleteRoleMenuByRoleId(role.getId());
        if (menu != null) {
            for (Integer m : menu) {
                roleMapper.saveRoleMenu(role.getId(), m);
            }
        }
    }

    /**
     * 删除角色通过ID
     *
     * @param ids
     *            角色Id集合
     * @return
     */
    public void deleteRoleById(Integer[] ids) {
        if (ids != null) {
            for (Integer id : ids) {
                roleMapper.deleteAdminRoleByRoleId(id);
                roleMapper.deleteRoleMenuByRoleId(id);
                roleMapper.deleteRoleById(id);
            }
        }
    }

    /**
     * 获取全部角色
     *
     * @return
     */
    public List<Role> findAll() {
        return roleMapper.getAll();
    }
}
