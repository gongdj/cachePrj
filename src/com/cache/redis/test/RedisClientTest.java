package com.cache.redis.test;

import java.util.ArrayList;
import java.util.List;
import com.cache.redis.service.StringRedisService;

/**
 * @Title: RedisClientTest.java
 * @Package com.cache.redis.test
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author gongdj
 * @date 2016��6��27��	����11:48:06
 * @version V1.0
 */
public class RedisClientTest {
	
	public void testAddString(){
		StringRedisService service = new StringRedisService();
		service.addObject("name", "���в�");
		
//		List<String> list = new ArrayList<String>();
//		list.add("a");
//		list.add("b");
//		service.addObjectViaSerialize("listObject", list);
//		List<String> listObject = (List<String>)service.getObjectViaSerialize("listObject");
//		System.out.println(listObject);
	}
	
	public static void main(String[] args) throws Exception {
		RedisClientTest test = new RedisClientTest();
		test.testAddString();
	}
	
	
	
}


