package com.cache.redis.service.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;

import com.cache.redis.pool.RedisSentinelConnectionPool;

/**
 * @Title: BaseService.java
 * @Package com.cache.redis.service
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author Comsys-gongdj
 * @date 2016��6��27�� ����10:26:31
 * @version V1.0
 */
public class BaseService {
	
	protected final static Logger logger = LoggerFactory.getLogger(BaseService.class);
	
	/**
	  * getConnection(��Redis Sentinel�л�ȡһ������)
	  *
	  * @Title: getConnection
	  * @Description: TODO
	  * @param @return    �趨�ļ�
	  * @return Jedis    ��������
	  * @throws
	 */
	protected Jedis getConnection() {
		return RedisSentinelConnectionPool.getConnection();
	}
	
	/**
	  * closedJedis(�ر�Jedis)
	  *
	  * @Title: closedJedis
	  * @Description: TODO
	  * @param @param jedis    �趨�ļ�
	  * @return void    ��������
	  * @throws
	 */
	protected void closedJedis(Jedis jedis){
		if(null != jedis) {
			jedis.close();
		}
	}
	
	/**
	  * delSerializedObject(���ݻ���keyɾ�����л��������)
	  *
	  * @Title: delSerializedObject
	  * @param @param jedis
	  * @param @param key
	  * @throws
	 */
	public boolean delObject(String key) {
		Jedis jedis = this.getConnection();
		
		boolean flag = false;
		if(null == jedis || StringUtils.isEmpty(key)) {
			logger.error(this.getClass().getName() + "addObject param is uncorrect: " + jedis + "##" + key);
			return flag;
		}
		
		try {
			jedis.del(key);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName() + " jedis delete cache object by key error: " + e.getMessage());
		} finally {
			jedis.close();
		}
		
		return flag;
	}
	
	/**
	  * existObject(������һ�仰�����������������)
	  *
	  * @Title: existObject
	  * @param @param jedis
	  * @param @param key
	  * @param @return    �趨�ļ�
	  * @return boolean    ��������
	  * @throws
	 */
	public boolean existObject(String key) {
		Jedis jedis = this.getConnection();
		
		boolean flag = false;
		if(null == jedis || StringUtils.isEmpty(key)) {
			logger.error(this.getClass().getName() + "addObject param is uncorrect: " + jedis + "##" + key);
			return flag;
		}
		
		try {
			flag = jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		
		return flag;
	}
	
}