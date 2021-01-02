package com.cache.redis.pool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.*;
import redis.clients.util.Pool;

//import com.penglecode.common.redis.jedis.ms.MasterSlaveJedis;
//import com.penglecode.common.redis.jedis.ms.MasterSlaveJedisSentinelPool;
//import com.penglecode.common.redis.jedis.ms.ShardedMasterSlaveJedis;
//import com.penglecode.common.redis.jedis.ms.ShardedMasterSlaveJedisSentinelPool;

/**
 * �ɲ�������redis
 * 
 * @author zuojunning http://itindex.net/detail/52975-jedis-redis-sentinel
 */
public class RedisShardedSentinelConnectionPool {
//	private static Pool<ShardedMasterSlaveJedis> pool;

	/**
	 * ��ʼ��redis����
	 */
	static {
		JedisPoolConfig config = new JedisPoolConfig();// Jedis������
		config.setMaxTotal(500);// ����Ķ������
		config.setMaxIdle(1000 * 60);// ����������ʱ��
		config.setMaxWaitMillis(1000 * 10);// ��ȡ����ʱ���ȴ�ʱ��
		config.setTestOnBorrow(true);
		 Set<String> sentinels = new LinkedHashSet<String>();
		// 2��sentines��ip�Ͷ˿�
		 sentinels.add("10.11.40.22:26379");
		 sentinels.add("10.11.40.22:26380");
		/***  �������start   ***/
		Set<String> masterNames = new LinkedHashSet<String>();
		masterNames.add("master-1");
		masterNames.add("master-2");
//		pool = new ShardedMasterSlaveJedisSentinelPool(
//				masterNames, sentinels, config);
		/***  �������end   ***/
	}
	
	/**
	 * ��ȡredis����
	 * 
	 * @return
	 */
//	public static ShardedMasterSlaveJedis getConnection() {
//		if (null != pool) {
//			return pool.getResource();
//		}
//		return null;
//	}

	/**
	 * �ر�redis
	 * 
	 * @param jedis
	 */
//	public static void closeConnection(ShardedMasterSlaveJedis jedis) {
//		if (null != jedis) {
//			pool.returnResource(jedis);
//		}
//	}
	
	public static void main(String[] args){
		RedisShardedSentinelConnectionPool rscp = new RedisShardedSentinelConnectionPool();
//		ShardedMasterSlaveJedis jd = rscp.getConnection();
//		jd.set("fff","fff");
		
		
//		System.out.println(jd.get("ccc"));
//		System.out.println(jd.get("ddd"));
//		System.out.println(jd.get("eee"));
	}
}
