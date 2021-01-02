package com.cache.redis.util;

/**
 * @Title: RedisConstant.java
 * @Package com.cache.redis.util
 * @Description: Redis ������
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author gongdj
 * @date 2016��6��27��	����11:14:38
 * @version V1.0
 */
public final class RedisConstant {
	
	/**
	 * ��ֵ��Ч�����ڵ������(NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist)
	 */
	public static final String XX = "xx";
	
	/**
	 * ��ֵ��Ч�������ڵ������(NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist)
	 */
	public static final String NX = "nx";
	
	/**
	 * ���ڹ���ʱ�䵥λ������(EX|PX, expire time units: EX = seconds; PX = milliseconds)
	 */
	public static final String PX = "px";
	
	/**
	 * ���ڹ���ʱ�䵥λ������(EX|PX, expire time units: EX = seconds; PX = milliseconds)
	 */
	public static final String EX = "ex";
	
	// Redis info section command
	public class InfoSection {
		//һ�� Redis ��������Ϣ
		public static final String INFO_SECTION_SERVER = "Server";
		
		//�����ӿͻ�����Ϣ
		public static final String INFO_SECTION_CLIENTS = "Clients";
		
		//�ڴ���Ϣ
		public static final String INFO_SECTION_MEMORY = "Memory";
		
		//RDB �� AOF �������Ϣ
		public static final String INFO_SECTION_PERSISTENCE = "Persistence";
		
		//һ��ͳ����Ϣ
		public static final String INFO_SECTION_STATS = "Stats";
		
		//��/�Ӹ�����Ϣ
		public static final String INFO_SECTION_REPLICATION = "Replication";
		
		//CPU ������ͳ����Ϣ
		public static final String INFO_SECTION_CPU = "CPU";
		
		//Redis ����ͳ����Ϣ
		public static final String INFO_SECTION_COMMANDSTATS = "Commandstats";
		
		// Redis ��Ⱥ��Ϣ
		public static final String INFO_SECTION_CLUSTER = "Cluster";
		
		//���ݿ���ص�ͳ����Ϣ
		public static final String INFO_SECTION_KEYSPACE = "Keyspace";
		
		//����������Ϣ
		public static final String INFO_SECTION_ALL = "all";
		
		//����Ĭ��ѡ�����Ϣ(����������ֱ�ӵ��� INFO ����ʱ��ʹ�� default ��ΪĬ�ϲ���)
		public static final String INFO_SECTION_DEFAULT = "default";
		
	}

}


