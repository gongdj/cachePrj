package com.example.service;

import org.springframework.stereotype.Component;

/**
 * @Title: UserService.java
 * @Package com.cache.service
 * @Description: TODO
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author careergong
 * @date 2016年7月8日 下午4:04:23
 * @version V1.0
 */
@Component
public class UserService extends AbstractService{

	public void testCache(){
		System.out.println("testCache");
	}
	
	public void sayHello(String name) {
		// TODO Auto-generated method stub
		System.out.println("Hello " + name);
	}
	
	public void sayHello(String name, String sex) {
		// TODO Auto-generated method stub
		System.out.println("Hello " + name + "|| sex=" + sex);
	}
	
}


