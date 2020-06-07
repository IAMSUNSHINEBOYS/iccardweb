package com.alen.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author LK
 * @version 1.0
 * @date 2020/5/16 13:53
 */
@Data
public class LoginRecord {

    private Integer id;// 主键ID
    private String recordType;// 记录类型 1登录 2退出
    private Integer adminId;// 系统用户ID
    private String adminName;// 系统用户真实姓名
    private String adminUserName;// 系统用户登录名
    private String sex;// 性别
    private String phone;// 电话号码
    private Date loginTime;// 登录时间
    private Date exitTime;// 退出时间
    private String sessionId;// sessionID
    private String ip;// 登录IP

    public LoginRecord() {
    }

    public LoginRecord(Online online) {
        this.adminId = online.getAdminId();
        this.adminName = online.getAdminName();
        this.adminUserName = online.getAdminUserName();
        this.sex = online.getSex();
        this.phone = online.getPhone();
        this.loginTime = online.getLoginTime();
        this.sessionId = online.getSessionId();
        this.ip = online.getIp();
    }

    public String getTime() {
        if (exitTime != null) {
            long len = exitTime.getTime() - loginTime.getTime();
            return len / (1000 * 60) > 0 ? len / (1000 * 60) + "分钟"
                    : (len % (1000 * 60)) / 1000 + "秒";
        }
        return "";
    }

    public String getRecordTypeStr() {
        if ("1".equals(recordType))
            return "登录";
        if ("2".equals(recordType))
            return "退出";
        return recordType;
    }
}
