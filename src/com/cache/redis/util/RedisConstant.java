package com.cache.redis.util;

/**
 * @Title: RedisConstant.java
 * @Package com.cache.redis.util
 * @Description: Redis 常量类
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author gongdj
 * @date 2016年6月27日	下午11:14:38
 * @version V1.0
 */
public final class RedisConstant {
	
	/**
	 * 键值有效：存在的情况下(NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist)
	 */
	public static final String XX = "xx";
	
	/**
	 * 键值有效：不存在的情况下(NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist)
	 */
	public static final String NX = "nx";
	
	/**
	 * 键期过期时间单位：毫秒(EX|PX, expire time units: EX = seconds; PX = milliseconds)
	 */
	public static final String PX = "px";
	
	/**
	 * 键期过期时间单位：毫秒(EX|PX, expire time units: EX = seconds; PX = milliseconds)
	 */
	public static final String EX = "ex";
	
	// Redis info section command
	public class InfoSection {
		//一般 Redis 服务器信息
		public static final String INFO_SECTION_SERVER = "Server";
		
		//已连接客户端信息
		public static final String INFO_SECTION_CLIENTS = "Clients";
		
		//内存信息
		public static final String INFO_SECTION_MEMORY = "Memory";
		
		//RDB 和 AOF 的相关信息
		public static final String INFO_SECTION_PERSISTENCE = "Persistence";
		
		//一般统计信息
		public static final String INFO_SECTION_STATS = "Stats";
		
		//主/从复制信息
		public static final String INFO_SECTION_REPLICATION = "Replication";
		
		//CPU 计算量统计信息
		public static final String INFO_SECTION_CPU = "CPU";
		
		//Redis 命令统计信息
		public static final String INFO_SECTION_COMMANDSTATS = "Commandstats";
		
		// Redis 集群信息
		public static final String INFO_SECTION_CLUSTER = "Cluster";
		
		//数据库相关的统计信息
		public static final String INFO_SECTION_KEYSPACE = "Keyspace";
		
		//返回所有信息
		public static final String INFO_SECTION_ALL = "all";
		
		//返回默认选择的信息(当不带参数直接调用 INFO 命令时，使用 default 作为默认参数)
		public static final String INFO_SECTION_DEFAULT = "default";
		
	}

}


