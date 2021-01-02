package com.cache.zk;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: AbstractZkWatch.java
 * @Package com.cache.zk
 * @Description: zk抽象类
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author careergong
 * @date 2016年6月29日 下午4:53:20
 * @version V1.0
 */
public abstract class AbstractZkWatch {
//public abstract class AbstractZkWatch implements Runnable {	

	protected static ZooKeeper zk = null;
	
	protected final static Logger logger = LoggerFactory.getLogger(AbstractZkWatch.class);
	
	private static String HOSTPORT = "10.11.40.20:2181";
	
//	private static String HOSTPORT = "127.0.0.1:2181";
	
	private static int sessionTimeout = 5000;
	
	
//	private void init() {
//		initZk();
//	}
	
	protected void initZk(){
		try {
			logger.warn("开始初始化zk客户端");
			zk = new ZooKeeper(HOSTPORT, sessionTimeout, new Watcher() {
				public void process(WatchedEvent event) {
					//默认实现类
				}
			});
			
			if(zk.getState() == ZooKeeper.States.CONNECTED) {
				logger.warn("结束初始化zk客户端");
			} else {
				logger.warn("无法连接到zk服务器:" + HOSTPORT);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("初始化zk客户端失败，原因：" + e.getMessage(), e);
		}
	}
	
	/** 
     *  
     *<b>function:</b>创建持久态的znode,比支持多层创建.比如在创建/parent/child的情况下,无/parent.无法通过 
     *@param path 
     *@param data 
     *@throws KeeperException 
     *@throws InterruptedException 
     */  
	protected void create(String path,byte[] data)throws KeeperException, InterruptedException{  
        /** 
         * 此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect. 
         * EPHEMERAL 表示The znode will be deleted upon the client's disconnect. 
         */   
//        zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);  
        
        Stat stat = null;
        stat = zk.exists(path, false);
        if(null == stat) {
        	String[] strs = path.split("/");
    		String separator = "/", newPath = "";
    		for(String str : strs) {
    			if(str.equals("")) continue;
    			newPath += separator + str;
    			System.out.println(newPath);
    			stat = zk.exists(newPath, false);
    			if(null == stat) {
    				zk.create(newPath, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);  
    			}
    		}
    		zk.setData(newPath, data, -1);
        }
        
    }  
    /** 
     *  
     *<b>function:</b>获取节点信息 
     *@param path 
     *@throws KeeperException 
     *@throws InterruptedException 
     */  
	protected void getChild(String path) throws KeeperException, InterruptedException{     
        try{  
            List<String> list=zk.getChildren(path, false);  
            if(list.isEmpty()){  
            	logger.debug(path+"中没有节点");  
            }else{  
            	logger.debug(path+"中存在节点");  
                for(String child:list){  
                	logger.debug("节点为："+child);  
                }  
            }  
        }catch (KeeperException.NoNodeException e) {  
             throw e;     
  
        }  
    }  
    
	/**
	 * 
	  * getData(这里用一句话描述这个方法的作用)
	  *
	  * @Title: getData
	  * @Description: 获取节点的值
	  * @param @param path
	  * @param @return
	  * @param @throws KeeperException
	  * @param @throws InterruptedException    设定文件
	  * @return byte[]    返回类型
	  * @throws
	 */
	protected byte[] getData(String path) throws KeeperException, InterruptedException {     
        return  zk.getData(path, false,null);     
    } 
	
//	@Override
//	public void run() {
//		//回调子类方法
//		call();
//	}
	
	/**
	  * call(具体的服务调用)
	  *
	 */
//	public abstract void call();
	
}


