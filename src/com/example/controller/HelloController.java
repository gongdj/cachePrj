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
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author gongdj
 * @date 2016年6月30日 下午9:25:04
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
		// 创建模型跟视图，用于渲染页面。并且指定要返回的页面为home页面
		ModelAndView mav = new ModelAndView("helloWorld");
		return mav;
	}

}
