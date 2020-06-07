package com.alen.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
@Component
public class RedisUtils<T> {
	@Autowired
	private RedisTemplate<String, T> redisTemplate;
	@Resource(name = "redisTemplate")
	private ValueOperations<String, T> valueOps;
	@Resource(name = "redisTemplate")
	private HashOperations<String, String, T> hashOps;

	public void set(String key, T value, long expire) {
		valueOps.set(key, value, expire, TimeUnit.SECONDS);
	}

	public void set(String key, T value) {
		valueOps.set(key, value);
	}

	public T get(String key) {
		return valueOps.get(key);
	}

	public T get(String key, long expire) {
		T value = valueOps.get(key);
		redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		return value;
	}

	public boolean isExists(String key) {
		return redisTemplate.hasKey(key);
	}

	public long incr(String key) {
		return valueOps.increment(key, 1);
	}

	public void delete(String key) {
		redisTemplate.delete(key);
	}

	public void setHash(String key, String field, T value, long expire) {
		setHash(key, field, value);
		redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}

	public void setHash(String key, String field, T value) {
		hashOps.put(key, field, value);
	}

	public Map<String, T> getHash(String key, long expire) {
		Map<String, T> value = getHash(key);
		redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		return value;
	}

	public Map<String, T> getHash(String key) {
		return hashOps.entries(key);
	}

	public void delete(String key, String field) {
		hashOps.delete(key, field);
	}
}
