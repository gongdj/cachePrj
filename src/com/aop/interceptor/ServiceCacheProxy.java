package com.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

/**
 * @Title: interceptor.java
 * @Package com.cache
 * @Description: 缓存方法拦截器
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author careergong
 * @date 2016年7月8日 下午3:37:50
 * @version V1.0
 */
@SuppressWarnings("unchecked")
public class ServiceCacheProxy implements MethodInterceptor, ApplicationContextAware {
	
	private final static Logger logger = LoggerFactory.getLogger(ServiceCacheProxy.class);

	private ApplicationContext ctx;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Object result = null;
		String info = invocation.getMethod().getDeclaringClass() + "." + invocation.getMethod().getName() + "()";
		
		
		Method method = invocation.getMethod();
		String[] beans = ctx.getBeanNamesForType(method.getDeclaringClass());
		if(beans.length!=1) return invocation.proceed();
		
		String typeValue = getTypeValue(beans[0], method.getName(), method.getParameterTypes());
		
		Object[] args = invocation.getArguments();

		System.out.println(info);
		System.out.println(args);

		try {
			result = invocation.proceed();
			return result;
		} catch(Exception e) {
			logger.error("ServiceCacheProxy invoke error:" + e.getMessage(), e);
			throw new Exception("ServiceCacheProxy invokee MethodInvocation exception:" + e.getMessage(), e);
		}
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
