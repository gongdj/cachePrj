package com.cache.redis.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis���ӳأ��浥�������
 * 
 * @author zuojunning
 * 
 */
public class RedisConnectionPool {
	private static JedisPool pool = null;
	private Jedis jedis;
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		// һ��pool�ɷ�����ٸ�jedisʵ��,-1���ʾ������
		config.setMaxTotal(500);
		// ����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ��
		config.setMaxIdle(5);
		// ��ʾ��borrow(����)һ��jedisʵ��ʱ�����ĵȴ�ʱ�䣬��������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException��
		config.setMaxWaitMillis(1000 * 100);
		// ��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue����õ���jedisʵ�����ǿ��õģ�
		config.setTestOnBorrow(true);
		pool = new JedisPool(config, "10.11.40.13", 6379);
	}

	/**
	 * ��ȡ����
	 * @return
	 */
	public static Jedis getConnection(){
		if(null != pool){
			return pool.getResource();
		}
		return null;
	}
	
	/**
	 * �ͷ�����
	 * @param jedis
	 */
	public static void closeConnection(Jedis jedis){
		if(null != jedis){
			pool.returnResource(jedis);
		}
	}
}
