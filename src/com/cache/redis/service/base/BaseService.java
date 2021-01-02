package com.cache.redis.service.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;

import com.cache.redis.pool.RedisSentinelConnectionPool;

/**
 * @Title: BaseService.java
 * @Package com.cache.redis.service
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author Comsys-gongdj
 * @date 2016年6月27日 下午10:26:31
 * @version V1.0
 */
public class BaseService {
	
	protected final static Logger logger = LoggerFactory.getLogger(BaseService.class);
	
	/**
	  * getConnection(从Redis Sentinel中获取一个连接)
	  *
	  * @Title: getConnection
	  * @Description: TODO
	  * @param @return    设定文件
	  * @return Jedis    返回类型
	  * @throws
	 */
	protected Jedis getConnection() {
		return RedisSentinelConnectionPool.getConnection();
	}
	
	/**
	  * closedJedis(关闭Jedis)
	  *
	  * @Title: closedJedis
	  * @Description: TODO
	  * @param @param jedis    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	protected void closedJedis(Jedis jedis){
		if(null != jedis) {
			jedis.close();
		}
	}
	
	/**
	  * delSerializedObject(根据缓存key删除序列化缓存对象)
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
	  * existObject(这里用一句话描述这个方法的作用)
	  *
	  * @Title: existObject
	  * @param @param jedis
	  * @param @param key
	  * @param @return    设定文件
	  * @return boolean    返回类型
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