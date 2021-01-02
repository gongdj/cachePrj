package com.cache.redis.pool;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * 支持主从自动切换连接池（只支持单主单从）
 * @author zuojunning
 *
 */
public class RedisSentinelConnectionPool {
	private static JedisSentinelPool pool;
	
	/**
	 * 初始化redis连接
	 */
	static {
		try {
			GenericObjectPoolConfig config = new GenericObjectPoolConfig();
			config.setMaxIdle(50);
			config.setMaxTotal(100);
			config.setMaxWaitMillis(1000*30);
			config.setTestOnBorrow(true);
			Set<String> sentinels = new LinkedHashSet<String>();
//			sentinels.add("10.10.111.15:26390");
//			sentinels.add("10.10.111.21:26391");
//			sentinels.add("10.132.4.114:26390");
//			sentinels.add("10.160.10.179:26391");
			sentinels.add("192.168.1.110:26379");
			sentinels.add("192.168.1.110:26380");
			String password = "pwd";
			
//			sentinels.add("10.11.40.22:26379");
//			sentinels.add("10.11.40.22:26380");
//			String password = "alog@CyB#$20160620";//redis3.0.7版本设置
			pool = new JedisSentinelPool("mymaster", sentinels,config, password);
//			pool = new JedisSentinelPool("mymaster", sentinels,config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		RedisSentinelConnectionPool rsc = new RedisSentinelConnectionPool();
		Jedis jedis = rsc.getConnection();
		
		jedis.set("test", "123");
		
		rsc.closeConnection(jedis);
	}

	/**
	 * 获取redis链接
	 * 
	 * @return
	 */
	public static Jedis getConnection() {
		if (null != pool) {
			return pool.getResource();
		}
		return null;
	}

	/**
	 * 关闭redis
	 * 
	 * @param jedis
	 */
	public static void closeConnection(Jedis jedis) {
		if (null != jedis) {
			jedis.close();
//			pool.returnResource(jedis);
//			pool.returnResourceObject(jedis);
		}
	}
}
