package com.cache.zk.example;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: ZkWatchAPI.java
 * @Package com.cache.redis.zk
 * @Description: TODO
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author careergong
 * @date 2016��6��28�� ����3:17:21
 * @version V1.0
 */
public class ZkWatchAPI implements Watcher {

    public static final Logger LOG = LoggerFactory.getLogger(ZkWatchAPI.class);

    private static final int SESSION_TIMEOUT = 10000;

    private ZooKeeper zk = null;

    private CountDownLatch connectedSemaphore = new CountDownLatch( 1 );

    /**
     * ����Zookeeper
     * @param connectString  Zookeeper�����ַ
     */
    public void connectionZookeeper(String connectString){
        connectionZookeeper(connectString,SESSION_TIMEOUT);
    }

    /**
     * <p>����Zookeeper</p>
     * <pre>
     *     [����connectString��������ַ����]
     *     ��ʽ: 192.168.1.1:2181,192.168.1.2:2181,192.168.1.3:2181
     *     �����ַ�����ж��ip:port֮�䶺�ŷָ�,�ײ����
     *     ConnectStringParser connectStringParser =  new ConnectStringParser(��192.168.1.1:2181,192.168.1.2:2181,192.168.1.3:2181��);
     *     �������Ҫ���ǽ��������ַ�б��ַ�����������������һ��ArrayList��
     *     ArrayList<InetSocketAddress> serverAddresses = new ArrayList<InetSocketAddress>();
     *     ����ȥ�������ַ�б�ᱻ��һ����װ��StaticHostProvider���󣬲��������й����У�һֱ�����������ά��������ַ�б�
     *     ZK�ͻ��˽�����Server������һ��List�У�Ȼ���������(������������һ���Ե�)�������γ�һ����������ʹ�õ�ʱ�򣬴�0��λ��ʼһ��һ��ʹ�á�
     *     ��ˣ�Server��ַ�ܹ��ظ����ã������ܹ��ֲ��ͻ����޷�����ServerȨ�ص�ȱ�ݣ�����Ҳ��Ӵ���ա�
     *
     *     [�ͻ��˺ͷ���˻Ự˵��]
     *     ZooKeeper�У��ͻ��˺ͷ���˽������Ӻ󣬻Ự��֮����������һ��ȫ��Ψһ�ĻỰID(Session ID)��
     *     �������Ϳͻ���֮��ά�ֵ���һ�������ӣ���SESSION_TIMEOUTʱ���ڣ���������ȷ���ͻ����Ƿ���������(�ͻ��˻ᶨʱ�����������heart_beat�������������´�SESSION_TIMEOUTʱ��)��
     *     ��ˣ�����������£�Sessionһֱ��Ч������ZK��Ⱥ���л����϶��������Session��Ϣ��
     *     �ڳ��������������������£�����ͻ��������ӵ���̨ZK�������ˣ���������ԭ����������ϣ�,�ͻ����뵱ǰ���ӵ���̨������֮�����Ӷ���,
     *     ���ʱ��ͻ��˻������ڵ�ַ�б�ʵ����ZK�����ʱ���빹�췽�����Ǹ�����connectString����ѡ���µĵ�ַ�������ӡ�
     *
     *     [�Ựʱ��]
     *     �ͻ��˲����ǿ���������������Ự��ʱʱ�䣬��ZK�������˶ԻỰ��ʱʱ���������Ƶģ���Ҫ��minSessionTimeout��maxSessionTimeout�������������õġ�
     *     ����ͻ������õĳ�ʱʱ�䲻�������Χ����ô�ᱻǿ������Ϊ������Сʱ�䡣 Ĭ�ϵ�Session��ʱʱ������2 * tickTime ~ 20 * tickTime
     * </pre>
     * @param connectString  Zookeeper�����ַ
     * @param sessionTimeout Zookeeper���ӳ�ʱʱ��
     */
    public void connectionZookeeper(String connectString, int sessionTimeout){
        this.releaseConnection();
        try {
            // ZK�ͻ����������ǽ�ZK�����������е�ַ������������
            zk = new ZooKeeper(connectString, sessionTimeout, this );
            // ʹ��CountDownLatch.await()���̣߳���ǰ�̣߳�����ֱ����������ӵ��CountDownLatch���߳�ִ����ϣ�countDown()���Ϊ0��
            connectedSemaphore.await();
        } catch ( InterruptedException e ) {
            LOG.error("���Ӵ���ʧ�ܣ����� InterruptedException , e " + e.getMessage(), e);
        } catch ( IOException e ) {
            LOG.error( "���Ӵ���ʧ�ܣ����� IOException , e " + e.getMessage(), e );
        }
    }

    /**
     * <p>����zNode�ڵ�, String create(path<�ڵ�·��>, data[]<�ڵ�����>, List(ACL���ʿ����б�), CreateMode<zNode��������>) </p><br/>
     * <pre>
     *     �ڵ㴴������(CreateMode)
     *     1��PERSISTENT:�־û��ڵ�
     *     2��PERSISTENT_SEQUENTIAL:˳���Զ���ų־û��ڵ㣬���ֽڵ����ݵ�ǰ�Ѵ��ڵĽڵ����Զ��� 1
     *     3��EPHEMERAL:��ʱ�ڵ�ͻ���,session��ʱ����ڵ�ͻᱻ�Զ�ɾ��
     *     4��EPHEMERAL_SEQUENTIAL:��ʱ�Զ���Žڵ�
     * </pre>
     * @param path zNode�ڵ�·��
     * @param data zNode��������
     * @return �����ɹ�����true, ��֮����false.
     */
    public boolean createPath( String path, String data ) {
        try {
            String zkPath =  this.zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            LOG.info( "�ڵ㴴���ɹ�, Path: " + zkPath + ", content: " + data );
            return true;
        } catch ( KeeperException e ) {
            LOG.error( "�ڵ㴴��ʧ��, ����KeeperException! path: " + path + ", data:" + data
                    + ", errMsg:" + e.getMessage(), e );
        } catch ( InterruptedException e ) {
            LOG.error( "�ڵ㴴��ʧ��, ���� InterruptedException! path: " + path + ", data:" + data
                    + ", errMsg:" + e.getMessage(), e );
        }
        return false;
    }

    /**
     * <p>ɾ��һ��zMode�ڵ�, void delete(path<�ڵ�·��>, stat<���ݰ汾��>)</p><br/>
     * <pre>
     *     ˵��
     *     1���汾�Ų�һ��,�޷���������ɾ������.
     *     2������汾����znode�İ汾�Ų�һ��,���޷�ɾ��,��һ���ֹۼ�������;������汾������Ϊ-1,����ȥ���汾,ֱ��ɾ��.
     * </pre>
     * @param path zNode�ڵ�·��
     * @return ɾ���ɹ�����true,��֮����false.
     */
    public boolean deletePath( String path ){
        try {
            this.zk.delete(path,-1);
            LOG.info( "�ڵ�ɾ���ɹ�, Path: " + path);
            return true;
        } catch ( KeeperException e ) {
            LOG.error( "�ڵ�ɾ��ʧ��, ����KeeperException! path: " + path
                    + ", errMsg:" + e.getMessage(), e );
        } catch ( InterruptedException e ) {
            LOG.error( "�ڵ�ɾ��ʧ��, ���� InterruptedException! path: " + path
                    + ", errMsg:" + e.getMessage(), e );
        }
        return false;
    }

    /**
     * <p>����ָ���ڵ���������, Stat setData(path<�ڵ�·��>, data[]<�ڵ�����>, stat<���ݰ汾��>)</p>
     * <pre>
     *     ����ĳ��znode�ϵ�����ʱ���Ϊ-1�������汾���
     * </pre>
     * @param path zNode�ڵ�·��
     * @param data zNode��������
     * @return ���³ɹ�����true,���ط���false
     */
    public boolean writeData( String path, String data){
        try {
            Stat stat = this.zk.setData(path, data.getBytes(), -1);
            LOG.info( "�������ݳɹ�, path��" + path + ", stat: " + stat );
            return true;
        } catch (KeeperException e) {
            LOG.error( "��������ʧ��, ����KeeperException! path: " + path + ", data:" + data
                    + ", errMsg:" + e.getMessage(), e );
        } catch (InterruptedException e) {
            LOG.error( "��������ʧ��, ����InterruptedException! path: " + path + ", data:" + data
                    + ", errMsg:" + e.getMessage(), e );
        }
        return false;
    }

    /**
     * <p>��ȡָ���ڵ���������,byte[] getData(path<�ڵ�·��>, watcher<������>, stat<���ݰ汾��>)</p>
     * @param path zNode�ڵ�·��
     * @return �ڵ�洢��ֵ,��ֵ����,��ֵ����null
     */
    public String readData( String path ){
        String data = null;
        try {
            data = new String( this.zk.getData( path, false, null ) );
            LOG.info( "��ȡ���ݳɹ�, path:" + path + ", content:" + data);
        } catch (KeeperException e) {
            LOG.error( "��ȡ����ʧ��,����KeeperException! path: " + path
                    + ", errMsg:" + e.getMessage(), e );
        } catch (InterruptedException e) {
            LOG.error( "��ȡ����ʧ��,����InterruptedException! path: " + path
                    + ", errMsg:" + e.getMessage(), e );
        }
        return  data;
    }

    /**
     * <p>��ȡĳ���ڵ��µ������ӽڵ�,List getChildren(path<�ڵ�·��>, watcher<������>)�÷����ж������</p>
     * @param path zNode�ڵ�·��
     * @return �ӽڵ�·������ ˵��,���ﷵ�ص�ֵΪ�ڵ���
     * <pre>
     *     eg.
     *     /node
     *     /node/child1
     *     /node/child2
     *     getChild( "node" )���ļ����е�ֵΪ["child1","child2"]
     * </pre>
     *
     *
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChild( String path ){
        try{
            List<String> list=this.zk.getChildren( path, false );
            if(list.isEmpty()){
                LOG.info( "��û�нڵ�" + path );
            }
            return list;
        }catch (KeeperException e) {
            LOG.error( "��ȡ�ӽڵ�����ʧ��,����KeeperException! path: " + path
                    + ", errMsg:" + e.getMessage(), e );
        } catch (InterruptedException e) {
            LOG.error( "��ȡ�ӽڵ�����ʧ��,����InterruptedException! path: " + path
                    + ", errMsg:" + e.getMessage(), e );
        }
        return null;
    }

    /**
     * <p>�ж�ĳ��zNode�ڵ��Ƿ����, Stat exists(path<�ڵ�·��>, watch<�������Ƿ������Ŀ¼�ڵ㣬����� watcher ���ڴ��� ZooKeeper ʵ��ʱָ���� watcher>)</p>
     * @param path zNode�ڵ�·��
     * @return ���ڷ���true,��֮����false
     */
    public boolean isExists( String path ){
        try {
            Stat stat = this.zk.exists( path, false );
            return null != stat;
        } catch (KeeperException e) {
            LOG.error( "��ȡ����ʧ��,����KeeperException! path: " + path
                    + ", errMsg:" + e.getMessage(), e );
        } catch (InterruptedException e) {
            LOG.error( "��ȡ����ʧ��,����InterruptedException! path: " + path
                    + ", errMsg:" + e.getMessage(), e );
        }
        return false;
    }

    /**
     * Watcher Server,�����յ��ı��
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        LOG.info("�յ��¼�֪ͨ��" + watchedEvent.getState() );
        if ( Event.KeeperState.SyncConnected == watchedEvent.getState() ) {
            connectedSemaphore.countDown();
        }
    }

    /**
     * �ر�ZK����
     */
    public void releaseConnection() {
        if ( null != zk ) {
            try {
                this.zk.close();
            } catch ( InterruptedException e ) {
                LOG.error("release connection error ," + e.getMessage() ,e);
            }
        }
    }

    public static void main(String [] args){

        // ���常����ڵ�·��
        String rootPath = "/nodeRoot";
        String child1Path = rootPath + "/nodeChildren1";
        String child2Path = rootPath + "/nodeChildren2";

        ZkWatchAPI zkWatchAPI = new ZkWatchAPI();
        
        // ����zk������
        zkWatchAPI.connectionZookeeper("10.11.40.20:2181");
        
        String redisPath = "/wms/alog/config/redisEnable";
        System.out.println( "���ڵ�[" + redisPath + "]�Ƿ����:" + zkWatchAPI.isExists(redisPath) + "ֵΪ��" + zkWatchAPI.readData( redisPath ) );

        // �����ڵ�����
        if ( zkWatchAPI.createPath( rootPath, "<��>�ڵ�����" ) ) {
            System.out.println( "�ڵ�[" + rootPath + "]��������[" + zkWatchAPI.readData( rootPath ) + "]" );
        }
        // �����ӽڵ�, ��ȡ + ɾ��
        if ( zkWatchAPI.createPath( child1Path, "<��-��(1)>�ڵ�����" ) ) {
            System.out.println( "�ڵ�[" + child1Path + "]��������[" + zkWatchAPI.readData( child1Path ) + "]" );
            zkWatchAPI.deletePath(child1Path);
            System.out.println( "�ڵ�[" + child1Path + "]ɾ��ֵ��[" + zkWatchAPI.readData( child1Path ) + "]" );
        }

        // �����ӽڵ�, ��ȡ + �޸�
        if ( zkWatchAPI.createPath( child2Path, "<��-��(2)>�ڵ�����" ) ) {
            System.out.println( "�ڵ�[" + child2Path + "]��������[" + zkWatchAPI.readData( child2Path ) + "]" );
            zkWatchAPI.writeData( child2Path, "<��-��(2)>�ڵ�����,���º������" );
            System.out.println( "�ڵ�[" + child2Path+ "]�������ݸ��º�[" + zkWatchAPI.readData( child2Path ) + "]" );
        }

        // ��ȡ�ӽڵ�
        List<String> childPaths = zkWatchAPI.getChild(rootPath);
        if(null != childPaths){
            System.out.println( "�ڵ�[" + rootPath + "]�µ��ӽڵ���[" + childPaths.size() + "]" );
            for(String childPath : childPaths){
                System.out.println(" |--�ڵ���[" +  childPath +  "]");
            }
        }

        // �жϽڵ��Ƿ����
        System.out.println( "���ڵ�[" + rootPath + "]�Ƿ����:" + zkWatchAPI.isExists(rootPath)  );
        System.out.println( "���ڵ�[" + child1Path + "]�Ƿ����:" + zkWatchAPI.isExists(child1Path)  );
        System.out.println( "���ڵ�[" + child2Path + "]�Ƿ����:" + zkWatchAPI.isExists(child2Path)  );


//        zkWatchAPI.releaseConnection();
    }

}


