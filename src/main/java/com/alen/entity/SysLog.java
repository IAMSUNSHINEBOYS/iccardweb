package com.alen.entity;

import lombok.Data;

import java.util.Date;

/**
 * 系统日志
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/16 13:51
 */
@Data
public class SysLog {

	private String id;// 主键ID
	private Integer operId;// 操作人ID
	private String operName;// 操作人名字
	private String operUsername;// 操作人登录名
	private Date operTime;// 操作时间
	private String requestUrl;// 请求地址
	private String requestData;// 请求数据
	private String ip;// 操作IP
	private Long time;// 执行时长(毫秒

	public SysLog() {
	}

	public SysLog(Integer operId, String operName,
			String operUsername, Date operTime, String requestUrl,
			String requestData,String ip,Long time) {
		this.operId = operId;
		this.operName = operName;
		this.operUsername = operUsername;
		this.operTime = operTime;
		this.requestUrl = requestUrl;
		this.requestData = requestData;
		this.ip = ip;
		this.time = time;
	}

}