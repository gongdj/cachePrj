package com.example.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.manager.UserManager;
import com.example.service.UserService;

/**
 * @Title: HomeController.java
 * @Package com.example
 * @Description: test
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author gongdj
 * @date 2016��6��30�� ����9:25:04
 * @version V1.0
 */
@Controller
public class HelloController {

	@Resource
	private UserService userService;
	
	@Resource
	private UserManager userManager;
	

	@RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
	public String home() {

		this.userManager.testCache();
		this.userManager.sayHello("zhang");

		return "helloWorld";
	}

	@RequestMapping("/index")
	public ModelAndView index() {
		// ����ģ�͸���ͼ��������Ⱦҳ�档����ָ��Ҫ���ص�ҳ��Ϊhomeҳ��
		ModelAndView mav = new ModelAndView("helloWorld");
		return mav;
	}

}
