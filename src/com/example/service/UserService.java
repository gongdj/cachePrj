package com.example.service;

import org.springframework.stereotype.Component;

/**
 * @Title: UserService.java
 * @Package com.cache.service
 * @Description: TODO
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author careergong
 * @date 2016��7��8�� ����4:04:23
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


