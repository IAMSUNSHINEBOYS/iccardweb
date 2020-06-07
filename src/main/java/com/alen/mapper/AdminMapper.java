package com.alen.mapper;

import com.alen.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public interface AdminMapper {
    /**
     * 查找用户授权标识通过用户ID
     *
     * @param adminId
     *            用户id
     */
    List<String> getPermsByAdminId(Integer adminId);

    /**
     * 查找用户通过用户名
     *
     * @param username
     *            用户名
     */
    Admin getAdminByUsername(String username);

    /**
     * 查找用户通过Id
     *
     * @param id
     *            用户名id
     */
    Admin getAdminById(Integer id);

    /**
     * 更新登录用户
     *
     * @param admin
     *            用户
     */
    int updateLoginAdmin(Admin admin);

    /**
     * 通过菜单ID查找对应的用户
     *
     * @param menuId
     *            菜单ID
     * @return
     */
    List<Admin> getAdminsByMeunId(Integer menuId);

    /**
     * 获取所有用户
     *
     */
    List<Admin> getAdminsAll();

    /**
     * 查询用户列表
     *
     *
     * @param admin
     *            系统用户
     * @param dateMap
     *            时间条件
     * @return
     */
    List<Admin> getAdminList(@Param("admin") Admin admin,
                             @Param("dateMap") Map<String, Date> dateMap);

    /**
     * 查找用户以及角色通过Id
     *
     * @param id
     *            用户名id
     */
    Admin getAdminAndRoleById(Integer id);

    /**
     * 保存系统用户
     *
     * @param admin
     *            用户对象
     */
    int saveAdmin(Admin admin);

    /**
     * 保存用户角色
     *
     * @param admin
     *            用户对象
     */
    int saveAdminRole(@Param("adminId") Integer adminId,
                      @Param("roleId") Integer roleId);

    /**
     * 修改用户
     *
     * @param admin
     *            用户对象
     */
    int updateAdminById(Admin admin);

    /**
     * 删除用户角色通过用户ID
     *
     * @param id
     *            用户ID
     */
    int deleteAdminRoleById(Integer id);

    /**
     * 删除用户角色通过用户ID集合
     *
     * @param ids
     *            用户ID集合
     */
    int deleteAdminRoleByIds(@Param("ids") Integer[] ids);

    /**
     * 删除用户通过用户ID集合
     *
     * @param ids
     *            用户ID集合
     */
    int deleteByIds(@Param("ids") Integer[] ids);
}
