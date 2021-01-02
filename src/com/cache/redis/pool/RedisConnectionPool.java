package com.cache.redis.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis链接池，面单单机情况
 * 
 * @author zuojunning
 * 
 */
public class RedisConnectionPool {
	private static JedisPool pool = null;
	private Jedis jedis;
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		// 一个pool可分配多少个jedis实例,-1则表示不限制
		config.setMaxTotal(500);
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例
		config.setMaxIdle(5);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWaitMillis(1000 * 100);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(true);
		pool = new JedisPool(config, "10.11.40.13", 6379);
	}

	/**
	 * 获取链接
	 * @return
	 */
	public static Jedis getConnection(){
		if(null != pool){
			return pool.getResource();
		}
		return null;
	}
	
	/**
	 * 释放链接
	 * @param jedis
	 */
	public static void closeConnection(Jedis jedis){
		if(null != jedis){
			pool.returnResource(jedis);
		}
	}
}
