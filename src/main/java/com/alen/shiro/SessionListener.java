package com.alen.shiro;


import com.alen.service.LoginRecordService;
import com.alen.utils.DateUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * session监听器
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Component("sessionListener")
public class SessionListener extends SessionListenerAdapter {
	@Autowired
	private LoginRecordService loginRecordService;

	@Override
	public void onExpiration(Session session) {
		loginRecordService.saveLoginRecord(String.valueOf(session.getId()),
				DateUtils.addMilliSeconds(new Date(), -session.getTimeout()));
	}

	@Override
	public void onStop(Session session) {
		loginRecordService.saveLoginRecord(String.valueOf(session.getId()),
				new Date());
	}
}
