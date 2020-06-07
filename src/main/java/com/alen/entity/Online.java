package com.alen.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 13:55
 */
@Data
public class Online {
    private Integer id;// 主键ID
    private Integer adminId;// 系统用户ID
    private String adminName;// 系统用户真实姓名
    private String adminUserName;// 系统用户登录名
    private String sex;// 性别
    private String phone;// 电话号码
    private Date loginTime;// 登录时间
    private String sessionId;// sessionID
    private String ip;// 登录IP

    public Online() {
    }

    public Online(Admin a,String sessionId) {
        if (a != null) {
            this.adminId = a.getId();
            this.adminName = a.getName();
            this.adminUserName = a.getUsername();
            this.sex = a.getSex();
            this.phone = a.getPhone();
            this.loginTime = a.getLastTime();
            this.sessionId = sessionId;
            this.ip = a.getLastIp();
        }
    }

    public String getTime() {
        if (loginTime != null) {
            Date nowTime = new Date();
            long len = nowTime.getTime() - loginTime.getTime();
            return len / (1000 * 60) > 0 ? len / (1000 * 60) + "分钟"
                    : (len % (1000 * 60)) / 1000 + "秒";
        }
        return "0秒";
    }
}
