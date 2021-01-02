package com.example.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.example.service.UserService;

/**
 * @Title: UserManager.java
 * @Package com.example
 * @Description: TODO
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author careergong
 * @date 2016年7月10日 下午4:13:21
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


