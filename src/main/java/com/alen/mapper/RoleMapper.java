package com.alen.mapper;

import com.alen.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 14:03
 */
public interface RoleMapper {

    /**
     * 通过菜单ID查找对应的角色
     *
     * @param menuId
     *            菜单ID
     * @return
     */
    List<Role> getRolesByMeunId(Integer menuId);

    /**
     * 查询角色列表
     *
     * @param role
     *            角色
     * @return
     */
    List<Role> getRoleList(Role role);

    /**
     * 通过角色名称查询
     *
     * @param name
     *            角色名称
     * @return
     */
    Role getRoleByName(String name);

    /**
     * 保存角色
     *
     * @param role
     *            角色
     * @return
     */
    int saveRole(Role role);

    /**
     * 保存角色菜单
     *
     * @param roleId
     *            角色ID
     * @param menuId
     *            菜单ID
     * @return
     */
    int saveRoleMenu(@Param("roleId") Integer roleId,
                     @Param("menuId") Integer menuId);

    /**
     * 通过角色Id查询
     *
     * @param id
     *            角色Id
     * @return
     */
    Role getRoleById(Integer id);

    /**
     * 通过角色Id删除角色菜单
     *
     * @param roleId
     *            角色Id
     * @return
     */
    int deleteRoleMenuByRoleId(Integer roleId);
    /**
     * 通过角色Id删除用户角色
     *
     * @param roleId
     *            角色Id
     * @return
     */
    int deleteAdminRoleByRoleId(Integer roleId);

    /**
     * 删除角色通过ID
     *
     * @param id
     *            角色Id
     * @return
     */
    int deleteRoleById(Integer id);

    /**
     * 更新角色Id查询
     *
     * @param role
     *            角色
     * @return
     */
    int updateRoleById(Role role);

    /**
     * 获取全部角色
     *
     * @return
     */
    List<Role> getAll();
}
