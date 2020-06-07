package com.alen.entity;

import lombok.Data;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 13:50
 */
@Data
public class UserMenu {

    private Integer id;// ID
    private Integer parent;// 父类ID
    private String name;// 菜单名称
    private String menuType;// 菜单类型：1一级菜单，2二级菜单
    private Integer orderNo;// 排序
    private int level;
}
