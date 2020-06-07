package com.alen.shiro;

import com.alen.utils.RedisUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;


/**
 * session DAO
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class ShiroSessionDAO extends EnterpriseCacheSessionDAO {
	private final String KEY_PRIFIX = "sessionId:";
	@Autowired
	private RedisUtils<Session> redisUtils;
	private long sessionTimeout;// 超时时间

	// 创建session
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = super.doCreate(session);
		String key = KEY_PRIFIX + sessionId;
		redisUtils.set(key, session, sessionTimeout);
		return sessionId;
	}

	// 获取session
	@Override
	protected Session doReadSession(Serializable sessionId) {
		String key = KEY_PRIFIX + sessionId;
		return redisUtils.get(key, sessionTimeout);
	}

	// 更新session
	@Override
	protected void doUpdate(Session session) {
		String key = KEY_PRIFIX + session.getId();
		redisUtils.set(key, session, sessionTimeout);
	}

	// 删除session
	@Override
	protected void doDelete(Session session) {
		String key = KEY_PRIFIX + session.getId();
		redisUtils.delete(key);
	}

	public void setSessionTimeout(long sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
}
