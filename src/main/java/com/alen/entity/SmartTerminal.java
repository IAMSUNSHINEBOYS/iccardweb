package com.alen.entity;

import lombok.Data;

import java.util.Date;

/**
 * 终端主参数信息表
 * @author LK
 * @version 1.0
 * @date 2020/6/14 18:39
 */
@Data
public class SmartTerminal {

    private String smtUnitId; //使用单位ID
    private Integer smtAuthNo; //授权号
    private String smtTerminalCode; //机型代码
    private String smtTerminalId; //设备id
    private String smtRegNo; //注册号
    private String  smtMachineNo; //机号
    private String  smtMachineVer; //设备版本号
    private String smtPsamcardNo; //PASM卡号
    private String smtProgramVer; //分控器版本号
    private String smtUseCard; //卡类型
    private String smtClass; //卡类
    private Integer smtPortCount; //服务器端口总数
    private Integer smtListMark; //名单属性
    private Integer smtParentId; //上级设备的唯一码
    private Integer smtPortNo; //服务器端口号
    private Integer smtLevel; //设备级数
    private String smtLevelArray; //级联参数
    private String smtLineNo; //配线架位号
    private String smtLocation; //安装位置
    private String smtMerchantId; //管理组织代码
    private String smtLocationId; //位置代码
    private Integer smtStatus; //设备状态
    private String smtChangeDate; //设备状态的设置时间
    private Integer smtComMode; //链路模式
    private String smtIpAddr; //通讯地址
    private String smtIpAddrdes; //通讯地址说明
    private String smtComPort; //通讯端口
    private Integer smtBaudrate; //波特率
    private Integer smtCollectWay; //数据通讯方式
    private Integer smtOnnet; //是否在网
    private Integer smtOnline; //是否联机交易
    private Integer smtStationIp; //站点IP地址
    private String smtComVer; //通信版本号
    private String smtDealCode; //交易类型
    private Date smtRegTime; //登记时间
    private Integer smtLinkForm; //通讯网络形式
    private String smtDailyCode; //日志类型代码
    private String smtDailyNo; //日志编号
    private Date smtDateTime; //变更时间
    private String smtOperator; //用户ID



}
