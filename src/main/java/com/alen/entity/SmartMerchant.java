package com.alen.entity;

import lombok.Data;

import java.util.Date;

/**
 * 商户组织信息实体
 * @author LK
 * @version 1.0
 * @date 2020/6/9 18:26
 */
@Data
public class SmartMerchant {

    private String smtUnitId; //使用单位ID
    private String smtMerchantId; //组织代码
    private String smtMerchantName;	//组织名称
    private String smtParentId;//父组织代码
    private Integer smtLevel; //级别
    private Integer smtLeast; //是否最小级
    private String smtDailyCode; //日志类型代码
    private String smtDailyNo; //日志编号
    private Date smtDateTime; 	//变更时间
    private String smtUserId; //用户ID

}
