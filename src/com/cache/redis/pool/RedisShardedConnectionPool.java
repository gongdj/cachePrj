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
 * redis连接池，支持分布式连接
 * 如果要平滑扩容，可以在一台服务器上装3个redis实例（A服务器），扩容的话可以再找2台新服务器安装redis，分别
 * 叫（B服务器）（C服务器），然后把这2台新机上的redis作为A服务器中其中两个redis实例的备机，然后主从进行同步，
 * 主从同步完后将客户端分片列表中A服务器的两个实例的IP和端口分别改为新物理机上Redis-Server的IP和端口，注意顺序是不变的，
 * 因为客户端jedis的一致性哈稀进行分片原理：初始化ShardedJedisPool的时候，会将上面程序中的jdsInfoList数据进行一个算法技术，
 * 主要计算依据为list中的index位置来计算
 * 其实这样也只能平滑扩容3台服务器，所以前期要预估好，可以前期安装3台redis，每台redis上3个实例，那么后期最多就能扩容9台redis了
 * http://my.oschina.net/hanshubo/blog/377910
 * @author zuojunning
 *
 */
public class RedisShardedConnectionPool {
	private static ShardedJedisPool pool;
	static {
		JedisPoolConfig config = new JedisPoolConfig();// Jedis池配置
		config.setMaxTotal(500);// 最大活动的对象个数
		config.setMaxIdle(1000 * 60);// 对象最大空闲时间
		config.setMaxWaitMillis(1000 * 10);// 获取对象时最大等待时间
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
