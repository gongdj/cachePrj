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
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author gongdj
 * @date 2016年6月27日 下午10:53:14
 * @version V1.0
 */
public class SerializeUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);
	
	/**
	  * serialize(序列化对象)
	  *
	  * @Title: serialize
	  * @Description: TODO
	  * @param @param object
	  * @param @return    设定文件
	  * @return byte[]    返回类型
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
	  * unserialize(反序列化对象)
	  *
	  * @Title: unserialize
	  * @Description: TODO
	  * @param @param bytes
	  * @param @return    设定文件
	  * @return Object    返回类型
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
