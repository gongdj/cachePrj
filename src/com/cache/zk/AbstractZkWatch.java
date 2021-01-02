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
 * @Description: zk������
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author careergong
 * @date 2016��6��29�� ����4:53:20
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
			logger.warn("��ʼ��ʼ��zk�ͻ���");
			zk = new ZooKeeper(HOSTPORT, sessionTimeout, new Watcher() {
				public void process(WatchedEvent event) {
					//Ĭ��ʵ����
				}
			});
			
			if(zk.getState() == ZooKeeper.States.CONNECTED) {
				logger.warn("������ʼ��zk�ͻ���");
			} else {
				logger.warn("�޷����ӵ�zk������:" + HOSTPORT);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("��ʼ��zk�ͻ���ʧ�ܣ�ԭ��" + e.getMessage(), e);
		}
	}
	
	/** 
     *  
     *<b>function:</b>�����־�̬��znode,��֧�ֶ�㴴��.�����ڴ���/parent/child�������,��/parent.�޷�ͨ�� 
     *@param path 
     *@param data 
     *@throws KeeperException 
     *@throws InterruptedException 
     */  
	protected void create(String path,byte[] data)throws KeeperException, InterruptedException{  
        /** 
         * �˴����õ���CreateMode��PERSISTENT  ��ʾThe znode will not be automatically deleted upon client's disconnect. 
         * EPHEMERAL ��ʾThe znode will be deleted upon the client's disconnect. 
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
     *<b>function:</b>��ȡ�ڵ���Ϣ 
     *@param path 
     *@throws KeeperException 
     *@throws InterruptedException 
     */  
	protected void getChild(String path) throws KeeperException, InterruptedException{     
        try{  
            List<String> list=zk.getChildren(path, false);  
            if(list.isEmpty()){  
            	logger.debug(path+"��û�нڵ�");  
            }else{  
            	logger.debug(path+"�д��ڽڵ�");  
                for(String child:list){  
                	logger.debug("�ڵ�Ϊ��"+child);  
                }  
            }  
        }catch (KeeperException.NoNodeException e) {  
             throw e;     
  
        }  
    }  
    
	/**
	 * 
	  * getData(������һ�仰�����������������)
	  *
	  * @Title: getData
	  * @Description: ��ȡ�ڵ��ֵ
	  * @param @param path
	  * @param @return
	  * @param @throws KeeperException
	  * @param @throws InterruptedException    �趨�ļ�
	  * @return byte[]    ��������
	  * @throws
	 */
	protected byte[] getData(String path) throws KeeperException, InterruptedException {     
        return  zk.getData(path, false,null);     
    } 
	
//	@Override
//	public void run() {
//		//�ص����෽��
//		call();
//	}
	
	/**
	  * call(����ķ������)
	  *
	 */
//	public abstract void call();
	
}


