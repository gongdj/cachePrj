package com.cache.redis.pool;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

/**
 * redis���ӳأ�֧�ֲַ�ʽ����
 * ���Ҫƽ�����ݣ�������һ̨��������װ3��redisʵ����A�������������ݵĻ���������2̨�·�������װredis���ֱ�
 * �У�B����������C����������Ȼ�����2̨�»��ϵ�redis��ΪA����������������redisʵ���ı�����Ȼ�����ӽ���ͬ����
 * ����ͬ����󽫿ͻ��˷�Ƭ�б���A������������ʵ����IP�Ͷ˿ڷֱ��Ϊ���������Redis-Server��IP�Ͷ˿ڣ�ע��˳���ǲ���ģ�
 * ��Ϊ�ͻ���jedis��һ���Թ�ϡ���з�Ƭԭ����ʼ��ShardedJedisPool��ʱ�򣬻Ὣ��������е�jdsInfoList���ݽ���һ���㷨������
 * ��Ҫ��������Ϊlist�е�indexλ��������
 * ��ʵ����Ҳֻ��ƽ������3̨������������ǰ��ҪԤ���ã�����ǰ�ڰ�װ3̨redis��ÿ̨redis��3��ʵ������ô��������������9̨redis��
 * http://my.oschina.net/hanshubo/blog/377910
 * @author zuojunning
 *
 */
public class RedisShardedConnectionPool {
	private static ShardedJedisPool pool;
	static {
		JedisPoolConfig config = new JedisPoolConfig();// Jedis������
		config.setMaxTotal(500);// ����Ķ������
		config.setMaxIdle(1000 * 60);// ����������ʱ��
		config.setMaxWaitMillis(1000 * 10);// ��ȡ����ʱ���ȴ�ʱ��
		config.setTestOnBorrow(true);
		String hostA = "10.11.40.13";
		int portA = 6379;
		String hostB = "10.11.40.22";
		int portB = 6379;
		List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>(2);
		JedisShardInfo infoA = new JedisShardInfo(hostA, portA);
		JedisShardInfo infoB = new JedisShardInfo(hostB, portB);
		jdsInfoList.add(infoA);
		jdsInfoList.add(infoB);
		pool = new ShardedJedisPool(config, jdsInfoList, Hashing.MURMUR_HASH,
				Sharded.DEFAULT_KEY_TAG_PATTERN);
	}

	public static ShardedJedis getConnection() {
		if (null != pool) {
			return pool.getResource();
		}
		return null;
	}

	public static void closeConnection(ShardedJedis jedis) {
		if (null != jedis) {
			pool.returnResource(jedis);
		}
	}
}
