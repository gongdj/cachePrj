package com.cache.redis.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Title: SerializeUtil.java
 * @Package com.cache.redis.util
 * @Description: TODO
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author gongdj
 * @date 2016��6��27�� ����10:53:14
 * @version V1.0
 */
public class SerializeUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);
	
	/**
	  * serialize(���л�����)
	  *
	  * @Title: serialize
	  * @Description: TODO
	  * @param @param object
	  * @param @return    �趨�ļ�
	  * @return byte[]    ��������
	  * @throws
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			
			return bytes;
		} catch (Exception e) {
			logger.error("cache object serialize error:",e);
		} finally {
			try {
				baos.close();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("cache object serialize io close error:",e);
			}
		}
		
		return null;
	}

	/**
	 * 
	  * unserialize(�����л�����)
	  *
	  * @Title: unserialize
	  * @Description: TODO
	  * @param @param bytes
	  * @param @return    �趨�ļ�
	  * @return Object    ��������
	  * @throws
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			logger.error("cache object unserialize error:",e);
		} finally {
			try {
				bais.close();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("cache object unserialize io close error:",e);
			}
		}
		return null;
	}
}
