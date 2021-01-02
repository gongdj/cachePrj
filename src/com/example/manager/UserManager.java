package com.example.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.example.service.UserService;

/**
 * @Title: UserManager.java
 * @Package com.example
 * @Description: TODO
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author careergong
 * @date 2016��7��10�� ����4:13:21
 * @version V1.0
 */
@Component
public class UserManager {
	
	@Resource
	private UserService userService;
	
	public void testCache(){
		userService.testCache();
	}
	
	public void sayHello(String name) {
		userService.sayHello(name);
	}
}


