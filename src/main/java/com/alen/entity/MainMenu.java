package com.alen.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 14:21
 */
@Data
public class MainMenu {
    private Integer id;// ID
    private Integer parent;// 父类ID
    private String name;// 菜单名称
    private String menuType;// 菜单类型：1一级菜单，2二级菜单
    private String menuUrl;// 菜单请求地址
    private Integer orderNo;// 排序
    private String icon;// 菜单icon类
    private List<MainMenu> childMenus = new ArrayList<MainMenu>();
}
