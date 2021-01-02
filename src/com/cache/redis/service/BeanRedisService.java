package com.cache.redis.service;

import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

import com.cache.redis.service.base.BaseService;
import com.cache.redis.util.SerializeUtil;

/**
 * @Title: BeanRedisService.java
 * @Package com.cache.redis.service
 * @Description: TODO
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author careergong
 * @date 2016��7��10�� ����2:29:34
 * @version V1.0
 */
public class BeanRedisService extends BaseService {
	
	/**
	  * addObject(����һ�����󵽻���)
	  *
	  * @Title: addObject
	  * @param @param jedis
	  * @param @param key
	  * @param @param obj
	  * @param @return    �趨�ļ�
	  * @return boolean    ��������
	  * @throws
	 */
	protected <T> boolean addObjectViaSerialize(String key, T obj) {
		Jedis jedis = this.getConnection();
		
		if(null == jedis || StringUtils.isEmpty(key) || null == obj) {
			logger.error(this.getClass().getName() + "addObject param is uncorrect: " + jedis + "##" + key + "##" + obj);
			return false;
		}
		
		try {
			jedis.set(SafeEncoder.encode(key), SerializeUtil.serialize(obj));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName() + " jedis set error: " + e.getMessage());
		} finally {
			jedis.close();
		}
		
		return true;
	}
	
	/**
	  * addObject(����һ�����󵽻���,�����û������ʱ��)
	  *
	  * @Title: addObject
	  * @param @param jedis
	  * @param @param key
	  * @param @param obj
	  * @param @param expire
	  * @param @return    �趨�ļ�
	  * @return boolean    ��������
	  * @throws
	 */
	protected <T> boolean addObjectViaSerialize(String key, T obj, int expire) {
		Jedis jedis = this.getConnection();
		if(null == jedis || StringUtils.isEmpty(key) || null == obj) {
			logger.error(this.getClass().getName() + "addObject param is uncorrect: " + jedis + "##" + key + "##" + obj);
			return false;
		}
		
		try {
			jedis.set(SafeEncoder.encode(key), SerializeUtil.serialize(obj));
			jedis.expire(key, expire);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName() + " jedis set error: " + e.getMessage());
		} finally {
			jedis.close();
		}
		
		return true;
	}
	
	/**
	  * getObjectViaSerialize(���ݻ���key��ȡ���л�����)
	  *
	  * @Title: getObjectViaSerialize
	  * @param @param key
	  * @param @return    �趨�ļ�
	  * @return Object    ��������
	  * @throws
	 */
	protected Object getObjectViaSerialize(String key) {
		Jedis jedis = this.getConnection();
		
		Object obj = null;
		if(null == jedis || StringUtils.isEmpty(key)) {
			logger.error(this.getClass().getName() + "addObject param is uncorrect: " + jedis + "##" + key);
			return null;
		}
		
		try {
			obj = SerializeUtil.unserialize(jedis.get(SafeEncoder.encode(key)));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName() + " jedis set error: " + e.getMessage());
		} finally {
			jedis.close();
		}
		
		return obj;
	}
}


