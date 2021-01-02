package com.cache.redis.test;

import java.util.ArrayList;
import java.util.List;
import com.cache.redis.service.StringRedisService;

/**
 * @Title: RedisClientTest.java
 * @Package com.cache.redis.test
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author gongdj
 * @date 2016年6月27日	下午11:48:06
 * @version V1.0
 */
public class RedisClientTest {
	
	public void testAddString(){
		StringRedisService service = new StringRedisService();
		service.addObject("name", "李有才");
		
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


