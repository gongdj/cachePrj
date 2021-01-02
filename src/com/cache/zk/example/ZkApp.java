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
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author gongdj
 * @date 2016��6��28�� ����10:08:36
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
	 * ����watch���߳�
	 */
	@Override
	public void run() {

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
					String data = new String( ZkApp.zk.getData( REDIS_PATH, false, null ) );
					if(data.equals("1")) {
						ZkApp.flag = true;
					} else {
						ZkApp.flag = false;
					}
					zk.exists(REDIS_PATH, true);// ��Ҫ��ص������
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
		 * �������PATH�µĽ��
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
				zk.exists(REDIS_PATH, wc);// ��Ҫ��ص������
			} catch (Exception e) {
				e.printStackTrace();
				errNum++;
			}

			try {
				Thread.sleep(3000);// sleepһ�ᣬ����CUPռ����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * main(������һ�仰�����������������)
	 * 
	 * @Title: main
	 * @Description: TODO
	 * @param @param args �趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ZkApp client = new ZkApp();
		Thread th = new Thread(client);
		th.start();
	}

}
