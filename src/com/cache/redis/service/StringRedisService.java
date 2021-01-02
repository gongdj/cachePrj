package com.cache.redis.service;

import com.cache.redis.service.base.BaseService;

import redis.clients.jedis.Jedis;

/**
 * @Title: StringRedisService.java
 * @Package com.cache.redis.service
 * @Description: String Redis service
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author gongdj
 * @date 2016��6��27��	����11:49:09
 * @version V1.0
 */
public class StringRedisService extends BaseService {
	
	public boolean addObject(String key, String value) {
		boolean flag = false;
		Jedis jedis = super.getConnection();
		if(null == jedis) {
			logger.error(this.getClass().getName() + ".addObject error: redis is null");
			return false;
		}
		try {
			jedis.set(key, value);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName() + ".addObject error: " + e.getMessage());
		}
		
		return flag;
	}
	
}


