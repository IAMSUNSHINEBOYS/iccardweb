package com.alen.entity;

import lombok.Data;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 13:51
 */
@Data
public class Menu {
    private Integer id;// 主键ID
    private Integer parent;// 父类对象
    private String name;// 功能名称
    private String menuType;// 菜单类型：1一级菜单，2二级菜单，0其他
    private String menuUrl;// 菜单请求地址
    private String perms;// 授权标识
    private Integer orderNo;// 排序
    private String enable;// 1启用 0禁用
    private String icon;// 一级、二级菜单icon类
    private int level;

    public String getNameStr() {
        name = "┣" + name;
        while (--level > 0) {
            name = "　" + name;
        }
        return name;
    }
}
