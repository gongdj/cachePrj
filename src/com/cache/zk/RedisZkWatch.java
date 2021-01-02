package com.cache.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cache.redis.util.RedisCacheUtils;

/**
 * @Title: RedisZkWatch.java
 * @Package com.cache.zk
 * @Description: redis zk Watcher
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author careergong
 * @date 2016��6��29�� ����5:05:43
 * @version V1.0
 */
@Component("redisZkWatch")
public class RedisZkWatch extends AbstractZkWatch /*implements InitializingBean */{

	private static boolean flag = false;

//	private static volatile int errNum = 0;

	// Redis���������·��
	private static String REDIS_NODE = "/wms/alog/config/redisEnable";
	
	private static String EHCACHE_NODE = "/wms/alog/config/ehcacheEnable";
	
	// ���δ����Redis�����������Ĭ��ֵ
	private static String CACHE_DISABLE_VALUE = "0";
		
	// ���δ����Redis�����������Ĭ��ֵ
	private static String CACHE_ENABLE_VALUE = "1";
	
	//@Override
	public void afterPropertiesSet() throws Exception {
		
		super.initZk();
		
		//��������ڣ�����Ĭ��ֵ,�����򲻽��и�ֵ
		initRedisNodeParamValue(REDIS_NODE, CACHE_DISABLE_VALUE);
		initRedisNodeParamValue(EHCACHE_NODE, CACHE_ENABLE_VALUE);
		
		String redisEnable = new String( zk.getData( REDIS_NODE, false, null ) );
		String ehcacheEnable = new String( zk.getData( EHCACHE_NODE, false, null ) );
		
		super.logger.error("init RedisEnable=" + redisEnable);
		super.logger.error("init ehcacheEnable=" + ehcacheEnable);
		
		System.out.println("init RedisEnable=" + redisEnable);
		System.out.println("init ehcacheEnable=" + ehcacheEnable);
		
		Watcher wc = new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// ���������ݷ����ı�ʱ
				if (event.getType() == EventType.NodeDataChanged) {
					logger.info("Node data changed:" + event.getPath());
				}
				if (event.getType() == EventType.NodeDeleted) {
					logger.info("Node deleted:" + event.getPath());
				}
				if (event.getType() == EventType.NodeCreated) {
					logger.info("Node created:" + event.getPath());
				}

				// ��ȡ���º��nodelist
				try {
					String redisEnable = new String(zk.getData(REDIS_NODE, false, null));
					if (redisEnable.equals("1")) {
						RedisCacheUtils.redisEnable = true;
					} else {
						RedisCacheUtils.redisEnable = false;
					}
					zk.exists(REDIS_NODE, this);// ��Ҫ��ص������
					logger.error("RedisEnable changed, RedisEnable=" + redisEnable);
					System.out.println("RedisEnable changed, RedisEnable=" + redisEnable);
					
					String ehcacheEnable = new String(zk.getData(EHCACHE_NODE, false, null));
					if (ehcacheEnable.equals("1")) {
						RedisCacheUtils.ehcacheEnable = true;
					} else {
						RedisCacheUtils.ehcacheEnable = false;
					}
					zk.exists(EHCACHE_NODE, this);// ��Ҫ��ص������
					logger.error("ehcacheEnable changed, ehcacheEnable=" + ehcacheEnable);
					System.out.println("ehcacheEnable changed, ehcacheEnable=" + ehcacheEnable);
				} catch (KeeperException e) {
					System.out.println(event.getPath()
							+ " has no child, deleted.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		try {
			zk.exists(REDIS_NODE, wc);// ��Ҫ��ص������
			zk.exists(EHCACHE_NODE, wc);// ��Ҫ��ص������
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void initRedisNodeParamValue(final String path, final String value) throws Exception {
		if(StringUtils.isEmpty(path)) return;
		super.create(path, value.getBytes());
	}
	
//	@Override
//	public void call() {
//
//		Watcher wc = new Watcher() {
//			@Override
//			public void process(WatchedEvent event) {
//				// ���������ݷ����ı�ʱ
//				if (event.getType() == EventType.NodeDataChanged) {
//					logger.info("Node data changed:" + event.getPath());
//				}
//				if (event.getType() == EventType.NodeDeleted) {
//					logger.info("Node deleted:" + event.getPath());
//				}
//				if (event.getType() == EventType.NodeCreated) {
//					logger.info("Node created:" + event.getPath());
//				}
//
//				// ��ȡ���º��nodelist
//				try {
//					String data = new String(
//							zk.getData(REDIS_PATH, false, null));
//					if (data.equals("1")) {
//						RedisZkWatch.flag = true;
//					} else {
//						RedisZkWatch.flag = false;
//					}
//					zk.exists(REDIS_PATH, true);// ��Ҫ��ص������
//					System.out.println("RedisEnable=" + data);
//				} catch (KeeperException e) {
//					System.out.println(event.getPath()
//							+ " has no child, deleted.");
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		};
//		
//		try {
//			zk.exists(REDIS_PATH, wc);// ��Ҫ��ص������
//		} catch (KeeperException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		/**
		 * �������PATH�µĽ��
		 */
//		while (true) {
//			try {
//				if (errNum > 0) {
//					super.initZk();
//					String data = new String(zk.getData(REDIS_PATH, false, null));
//					if (data.equals("1")) {
//						RedisZkWatch.flag = true;
//					} else {
//						RedisZkWatch.flag = false;
//					}
//					System.out.println("RedisEnable=" + data);
//
//					errNum = 0;
//				}
//				zk.exists(REDIS_PATH, wc);// ��Ҫ��ص������
//			} catch (Exception e) {
//				e.printStackTrace();
//				errNum++;
//			}
//
//			try {
//				Thread.sleep(3000);// sleepһ�ᣬ����CUPռ����
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

//		}
//	}
	
}
