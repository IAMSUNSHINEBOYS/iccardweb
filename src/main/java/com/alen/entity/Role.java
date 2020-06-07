package com.alen.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 13:51
 */
@Data
public class Role {

    private Integer id;// 主键ID
    private String name;// 角色名称
    private String remark;// 备注
    private Integer adminId;// 用户ID
    private Set<Integer> menus = new HashSet<Integer>();
}
