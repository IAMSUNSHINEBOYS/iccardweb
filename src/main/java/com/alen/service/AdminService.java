package com.alen.service;


import com.alen.mapper.AdminMapper;
import com.alen.entity.Admin;
import com.alen.entity.LoginRecord;
import com.alen.entity.Online;
import com.alen.utils.ExportExcel;
import com.alen.utils.Pager;
import com.alen.utils.SqlDateUtils;
import com.alen.utils.Column;
import com.alen.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 16:42
 */
@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private OnlineService onlineService;
    @Autowired
    private LoginRecordService loginRecordService;
    /**
     * 查找用户授权标识通过用户ID
     *
     * @param adminId
     *            用户id
     */
    public Set<String> selectPermsByAdminId(Integer adminId) {
        Set<String> permSet = new HashSet<String>();
        List<String> permList = adminMapper.getPermsByAdminId(adminId);
        if (permList != null && !permList.isEmpty()) {
            for (Object p : permList) {
                if (!StringUtils.isBlank(p)) {
                    permSet.addAll(Arrays.asList(String.valueOf(p).split(",")));
                }
            }
        }
        return permSet;
    }

    /**
     * 查找用户通过用户名
     *
     * @param username
     *            用户名
     */
    public Admin findAdminByUsername(String username) {
        return adminMapper.getAdminByUsername(username);
    }

    /**
     * 查找用户通过Id
     *
     * @param id
     *            用户名id
     */
    public Admin findAdminById(Integer id) {
        return adminMapper.getAdminById(id);
    }

    /**
     * 更新登录用户
     *
     * @param admin
     *            用户
     */
    public void saveLoginInfo(Admin admin, String sessionId) {
        adminMapper.updateLoginAdmin(admin);
        Online online = new Online(admin, sessionId);
        onlineService.saveOnline(online);
        LoginRecord loginRecord = new LoginRecord(online);
        loginRecord.setRecordType("1");
        loginRecordService.saveLoginRecord(loginRecord);
    }

    /**
     * 通过菜单ID查找对应的用户
     *
     * @param menuId
     *            菜单ID
     * @return
     */
    public List<Admin> findAdminsByMeunId(Integer menuId) {
        return adminMapper.getAdminsByMeunId(menuId);
    }

    /**
     * 获取所有用户
     *
     */
    public List<Admin> findAdminsAll() {
        return adminMapper.getAdminsAll();
    }

    /**
     * 通过条件查询系统用户列表
     *
     * @param pager
     *            分页对象
     * @param admin
     *            系统用户对象
     * @param dateMap
     *            时间条件
     * @param field
     *            排序属性
     * @param order
     *            0降序、1升序
     */
    public List<Admin> findAdminList(Pager pager, Admin admin, Map<String, String> dateMap, String field, int order) {
        pager.addSortable("a.sex", "a.createTime", "a.lastTime").addOrder(field, order).startPage();
        return adminMapper.getAdminList(admin, SqlDateUtils.getDateMap(dateMap));
    }

    /**
     * 导出用户excel
     *
     * @param path
     *            模板路径
     * @param filename
     *            导出的excel名字
     * @param admin
     *            用户对象
     * @param dateMap
     *            时间
     * @param field
     *            排序属性
     * @param order
     *            0降序、1升序
     * @param columns
     *            需要导出的列属性
     */
    public void toExcel(String path, String filename, final Admin admin, final Map<String, String> dateMap,
                        String field, int order, Column columns, HttpServletResponse response) {
        Pager pager = new Pager();
        pager.addSortable("a.sex", "a.createTime", "a.lastTime").addOrder(field, order);
        new ExportExcel(path, filename, pager, response) {
            @Override
            public List<Admin> getDataList() {
                pager.exportPage();
                return adminMapper.getAdminList(admin, SqlDateUtils.getDateMap(dateMap));

            };
        }.toExcel(columns);
    }

    /**
     * 查找用户以及角色通过Id
     *
     * @param id
     *            用户名id
     */
    public Admin findAdminAndRoleById(Integer id) {
        return adminMapper.getAdminAndRoleById(id);
    }

    /**
     * 保存系统用户
     *
     * @param admin
     *            用户对象
     * @param roles
     *            角色集合
     */
    public void saveAdmin(Admin admin, Integer[] roles) {
        adminMapper.saveAdmin(admin);
        if (roles != null) {
            for (Integer roleId : roles) {
                adminMapper.saveAdminRole(admin.getId(), roleId);
            }
        }
    }

    /**
     * 修改系统用户
     *
     * @param admin
     *            用户对象
     * @param roles
     *            角色集合
     */
    public void updateAdmin(Admin admin, Integer[] roles) {
        updateAdminById(admin);
        adminMapper.deleteAdminRoleById(admin.getId());
        if (roles != null) {
            for (Integer roleId : roles) {
                adminMapper.saveAdminRole(admin.getId(), roleId);
            }
        }
    }

    /**
     * 修改用户
     *
     * @param admin
     *            用户对象
     */
    public void updateAdminById(Admin admin) {
        adminMapper.updateAdminById(admin);
    }

    /**
     * 删除用户通过用户ID集合
     *
     * @param ids
     *            用户ID集合
     */
    public void deleteByIds(Integer[] ids) {
        adminMapper.deleteAdminRoleByIds(ids);
        adminMapper.deleteByIds(ids);
    }
}
