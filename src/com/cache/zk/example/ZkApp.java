package com.cache.zk.example;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: ZkApp.java
 * @Package com.cache.zk
 * @Description: TODO
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author gongdj
 * @date 2016年6月28日 下午10:08:36
 * @version V1.0
 */
public class ZkApp implements Runnable {

	private static ZooKeeper zk;
	
	protected final static Logger logger = LoggerFactory.getLogger(ZkApp.class);
	
	private static String REDIS_PATH = "/wms/alog/redisEnable";
	
	private static boolean flag = false;
	
	private static int errNum = 0;

	public ZkApp() throws Exception {

		zk = new ZooKeeper("10.10.111.21:2181", 5000, new Watcher() {
			public void process(WatchedEvent event) {
			}
		});
		
		String data = new String( zk.getData( REDIS_PATH, false, null ) );
		System.out.println("init RedisEnable=" + data);
	}

	/**
	 * 设置watch的线程
	 */
	@Override
	public void run() {

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
					String data = new String( ZkApp.zk.getData( REDIS_PATH, false, null ) );
					if(data.equals("1")) {
						ZkApp.flag = true;
					} else {
						ZkApp.flag = false;
					}
					zk.exists(REDIS_PATH, true);// 所要监控的主结点
					System.out.println("RedisEnable=" + data);
				} catch (KeeperException e) {
					System.out.println(event.getPath()
							+ " has no child, deleted.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		/**
		 * 持续监控PATH下的结点
		 */
		while (true) {
			try {
				if(errNum > 1) {
					zk = new ZooKeeper("10.10.111.21:2181", 5000, new Watcher() {
						public void process(WatchedEvent event) {
						}
					});
					String data = new String( ZkApp.zk.getData( REDIS_PATH, false, null ) );
					if(data.equals("1")) {
						ZkApp.flag = true;
					} else {
						ZkApp.flag = false;
					}
					System.out.println("RedisEnable=" + data);
					
					errNum = 0;
				}
				zk.exists(REDIS_PATH, wc);// 所要监控的主结点
			} catch (Exception e) {
				e.printStackTrace();
				errNum++;
			}

			try {
				Thread.sleep(3000);// sleep一会，减少CUP占用率
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * main(这里用一句话描述这个方法的作用)
	 * 
	 * @Title: main
	 * @Description: TODO
	 * @param @param args 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ZkApp client = new ZkApp();
		Thread th = new Thread(client);
		th.start();
	}

}
