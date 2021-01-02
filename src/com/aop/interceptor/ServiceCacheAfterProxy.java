package com.aop.interceptor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Title: ServiceCacheAfterProxy.java
 * @Package com.taobao.wmpbasic.aop.interceptor
 * @Description: TODO
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author gongdj
 * @date 2016年7月11日	下午9:12:28
 * @version V1.0
 */
public class ServiceCacheAfterProxy implements AfterReturningAdvice, ApplicationContextAware {

	private final static Logger logger = LoggerFactory.getLogger(ServiceCacheProxy.class);

	private ApplicationContext ctx;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
	}
	
	/*
	 * <p>Title: afterReturning</p>
	 * <p>Description: </p>
	 * @param returnValue
	 * @param method
	 * @param args
	 * @param target
	 * @throws Throwable
	 * @see org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		System.out.println("==============after return==============");  
        System.out.println("result:" + returnValue + ";after method:" + method.getName()
                + ";parameter:" + args + ";target:" + target); 

	}
	
	private String getTypeValue(String beanName, String methodName, Class[] paramClasses) {
		StringBuffer buf = new StringBuffer(32);
		buf.append(beanName).append(".").append(methodName).append("(");
		for(int i=0; paramClasses!=null && i!=paramClasses.length; i++) {
			if(i!=0)  buf.append(",");
			buf.append(paramClasses[i].getSimpleName());
		}
		buf.append(")");
		return buf.toString(); 
	}

}


