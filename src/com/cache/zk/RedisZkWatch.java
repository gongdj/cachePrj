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
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author careergong
 * @date 2016年6月29日 下午5:05:43
 * @version V1.0
 */
@Component("redisZkWatch")
public class RedisZkWatch extends AbstractZkWatch /*implements InitializingBean */{

	private static boolean flag = false;

//	private static volatile int errNum = 0;

	// Redis缓存键所在路径
	private static String REDIS_NODE = "/wms/alog/config/redisEnable";
	
	private static String EHCACHE_NODE = "/wms/alog/config/ehcacheEnable";
	
	// 如果未设置Redis缓存键，则赋于默认值
	private static String CACHE_DISABLE_VALUE = "0";
		
	// 如果未设置Redis缓存键，则赋于默认值
	private static String CACHE_ENABLE_VALUE = "1";
	
	//@Override
	public void afterPropertiesSet() throws Exception {
		
		super.initZk();
		
		//如果不存在，则赋于默认值,存在则不进行赋值
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
				// 主结点的数据发生改变时
				if (event.getType() == EventType.NodeDataChanged) {
					logger.info("Node data changed:" + event.getPath());
				}
				if (event.getType() == EventType.NodeDeleted) {
					logger.info("Node deleted:" + event.getPath());
				}
				if (event.getType() == EventType.NodeCreated) {
					logger.info("Node created:" + event.getPath());
				}

				// 获取更新后的nodelist
				try {
					String redisEnable = new String(zk.getData(REDIS_NODE, false, null));
					if (redisEnable.equals("1")) {
						RedisCacheUtils.redisEnable = true;
					} else {
						RedisCacheUtils.redisEnable = false;
					}
					zk.exists(REDIS_NODE, this);// 所要监控的主结点
					logger.error("RedisEnable changed, RedisEnable=" + redisEnable);
					System.out.println("RedisEnable changed, RedisEnable=" + redisEnable);
					
					String ehcacheEnable = new String(zk.getData(EHCACHE_NODE, false, null));
					if (ehcacheEnable.equals("1")) {
						RedisCacheUtils.ehcacheEnable = true;
					} else {
						RedisCacheUtils.ehcacheEnable = false;
					}
					zk.exists(EHCACHE_NODE, this);// 所要监控的主结点
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
			zk.exists(REDIS_NODE, wc);// 所要监控的主结点
			zk.exists(EHCACHE_NODE, wc);// 所要监控的主结点
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
//				// 主结点的数据发生改变时
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
//				// 获取更新后的nodelist
//				try {
//					String data = new String(
//							zk.getData(REDIS_PATH, false, null));
//					if (data.equals("1")) {
//						RedisZkWatch.flag = true;
//					} else {
//						RedisZkWatch.flag = false;
//					}
//					zk.exists(REDIS_PATH, true);// 所要监控的主结点
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
//			zk.exists(REDIS_PATH, wc);// 所要监控的主结点
//		} catch (KeeperException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		/**
		 * 持续监控PATH下的结点
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
//				zk.exists(REDIS_PATH, wc);// 所要监控的主结点
//			} catch (Exception e) {
//				e.printStackTrace();
//				errNum++;
//			}
//
//			try {
//				Thread.sleep(3000);// sleep一会，减少CUP占用率
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

//		}
//	}
	
}
