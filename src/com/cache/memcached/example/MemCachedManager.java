package com.cache.memcached.example;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * Project Name:cachePrj <br>
 * File Name:MemcachedUtil.java <br>
 * Package Name:com.cache.memcached.example <br>
 * Date:2015��12��20������12:16:12
 * 
 * @author gongdj
 * @version Copyright (c) 2015, gdj_career2003@163.com All Rights Reserved.
 * 
 */
public class MemCachedManager {

	/**
	 * memcached�ͻ��˵���
	 */
	protected static MemCachedClient cachedClient = new MemCachedClient();
	
	protected static MemCachedManager memCached = new MemCachedManager();

	/**
	 * ��ʼ�����ӳ�
	 */
	static {
		// ��ȡ���ӳص�ʵ��
		SockIOPool pool = SockIOPool.getInstance();

		// �������б���Ȩ��
		String[] servers = { "192.168.1.119:11211" };
		Integer[] weights = { 3 };

		// ���÷�������Ϣ
		pool.setServers(servers);
		pool.setWeights(weights);

		// ���ó�ʼ����������С������������������������ʱ��
		pool.setInitConn(10);
		pool.setMinConn(10);
		pool.setMaxConn(1000);
		pool.setMaxIdle(1000 * 60 * 60);

		// �������ӳ��ػ��̵߳�˯��ʱ��
		pool.setMaintSleep(60);

		// ����TCP���������ӳ�ʱ
		pool.setNagle(false);
		pool.setSocketTO(60);
		pool.setSocketConnectTO(0);

		// ��ʼ�����������ӳ�
		pool.initialize();

		// ѹ�����ã�����ָ����С����λΪK�������ݶ��ᱻѹ��
		// cachedClient.setCompressEnable(true);
		// cachedClient.setCompressThreshold(1024*1024);
	}

	/**
	 * �����͹��췽����������ʵ������
	 * 
	 */
	protected MemCachedManager() {
		
	}
	
	/**
     * ��ȡΨһʵ��.
     * @return
     */
    public static MemCachedManager getInstance()
    {
        return memCached;
    }

	public static boolean add(String key, Object value) {
		return cachedClient.add(key, value);
	}

	public static boolean add(String key, Object value, Integer expire) {
		return cachedClient.add(key, value, expire);
	}

	public static boolean put(String key, Object value) {
		return cachedClient.set(key, value);
	}

	public static boolean put(String key, Object value, Integer expire) {
		return cachedClient.set(key, value, expire);
	}

	public static boolean replace(String key, Object value) {
		return cachedClient.replace(key, value);
	}

	public static boolean replace(String key, Object value, Integer expire) {
		return cachedClient.replace(key, value, expire);
	}

	public static Object get(String key) {
		return cachedClient.get(key);
	}

}
