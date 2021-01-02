package com.cache.memcached.example;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.danga.MemCached.MemCachedClient;

/**
 * Project Name:cachePrj<br>
 * File Name:MemcachedSpringTest.java<br>
 * Package Name:com.cache.memcached.example<br>
 * Date:2015年12月20日下午12:56:46<br>
 *
 * @author gongdj<br>
 * @version <br>
 * @Copyright (c) 2015, gdj_career2003@163.com All Rights Reserved.
 *
 */
public class MemcachedSpringTest {
	 
    private MemCachedClient cachedClient;
    
    @SuppressWarnings("all")
    @Before
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("com/cache/memcached/example/beans.xml");
        cachedClient = (MemCachedClient)context.getBean("memcachedClient");
    }
     
    @Test
    public void testMemcachedSpring() {
        UserBean user = new UserBean("lou", "jason");
        cachedClient.set("user", user);
        UserBean cachedBean = (UserBean)user;
        Assert.assertEquals(user, cachedBean);
    }
}

