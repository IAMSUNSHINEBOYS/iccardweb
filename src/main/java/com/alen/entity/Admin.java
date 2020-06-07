package com.alen.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author LK
 * @version 1.0
 * @date 2020/5/15 15:23
 */
@SuppressWarnings("serial")
@Data
public class Admin implements Serializable {

    private Integer id;// 主键ID
    private String username;// 登录名
    private String name;// 真实姓名
    private String password;// 登录密码
    private String sex;// 性别
    private String phone;// 电话号码
    private Date createTime;// 创建时间
    private String lastIp;// 最后登录IP
    private Date lastTime;// 最后登录时间
    private String valid;// 是否禁用，1可用 0和其他禁用
    private String headImg;// 头像
    private List<String> roles = new ArrayList<String>();
    private Integer roleId;// 角色ID

    public Admin() {
    }

    public Admin(Integer id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public String getValidStr() {
        if ("1".equals(valid))
            return "是";
        else
            return "否";
    }

    public String getHeadImgPath() {
        if (headImg == null || headImg.trim().equals(""))
            return "default.jpg";
        return headImg;
    }

    public String getRolesStr() {
        String str = "";
        for (String r : roles)
            if (str.length() == 0)
                str += r;
            else
                str += "," + r;
        return str;
    }
}
