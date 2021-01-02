package com.cache.zk.example;

/**
 * @Title: WatchClient.java
 * @Package com.cache.zk
 * @Description: TODO
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author gongdj
 * @date 2016��6��28��	����9:35:54
 * @version V1.0
 */
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;

public class WatchClient implements Runnable {

  private static final Log LOG = LogFactory.getLog(WatchClient.class);
  public static final int CLIENT_PORT = 2181;
  public static final String PATH = "/app1";// ��Ҫ��صĽ��
  private static ZooKeeper zk;
  private static List<String> nodeList;// ��Ҫ��صĽ����ӽ���б�

  public static void main(String[] args) throws Exception {
    WatchClient client = new WatchClient();
    Thread th = new Thread(client);
    th.start();
  }

  public WatchClient() throws IOException {

    zk = new ZooKeeper("192.168.255.133:" + CLIENT_PORT, 5000,
        new Watcher() {
          public void process(WatchedEvent event) {
          }
        });
  }

  /**
   * ����watch���߳�
   */
  @Override
  public void run() {
    
    Watcher wc = new Watcher() {
      @Override
      public void process(WatchedEvent event) {
        // ������ݸı�֮ǰ�Ľ���б�
        List<String> nodeListBefore = nodeList;
        // ���������ݷ����ı�ʱ
        if (event.getType() == EventType.NodeDataChanged) {
          LOG.info("Node data changed:" + event.getPath());
        }
        if (event.getType() == EventType.NodeDeleted){
          LOG.info("Node deleted:" + event.getPath());
        }
        if(event.getType()== EventType.NodeCreated){
          LOG.info("Node created:"+event.getPath());
        }

        // ��ȡ���º��nodelist
        try {
          nodeList = zk.getChildren(event.getPath(), false);
        } catch (KeeperException e) {
          System.out.println(event.getPath()+" has no child, deleted.");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        List<String> nodeListNow = nodeList;
        // ���ӽ��
        if (nodeListBefore.size() < nodeListNow.size()) {
          for (String str : nodeListNow) {
            if (!nodeListBefore.contains(str)) {
              LOG.info("Node created:" + event.getPath() + "/" + str);
            }
          }
        }
      }
    };

    /**
     * �������PATH�µĽ��
     */
    while (true) {
      try {
        zk.exists(PATH, wc);//��Ҫ��ص������
      } catch (KeeperException | InterruptedException e) {
        e.printStackTrace();
      }
      try {
        nodeList = zk.getChildren(PATH, wc);
      } catch (KeeperException | InterruptedException e) {
        e.printStackTrace();
      }
      // ��PATH�µ�ÿ����㶼����һ��watcher

      for (String nodeName : nodeList) {
        try {
          zk.exists(PATH + "/" + nodeName, wc);
        } catch (KeeperException | InterruptedException e) {
          e.printStackTrace();
        }
      }
      
      try {
        Thread.sleep(3000);// sleepһ�ᣬ����CUPռ����
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

