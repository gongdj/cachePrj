package com.cache.memcached.example;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Project Name:cachePrj<br>
 * File Name:MemcachedUtilTest.java<br>
 * Package Name:com.cache.memcached.example<br>
 * Date:2015年12月20日下午12:35:59<br>
 *
 * @author gongdj<br>
 * @version <br>
 * @Copyright (c) 2015, gdj_career2003@163.com All Rights Reserved.
 *
 */
public class MemcachedUtilTest {
	 
    @Test
    public void testMemcached() {
    	MemCachedManager.put("hello", "world", 60);
        String hello = (String) MemCachedManager.get("hello");
        System.out.println(hello);
        Assert.assertEquals("world", hello);
         
        for(int i = 0; i < 1000; ++i) {
            UserBean userBean = new UserBean("Jason" + i, "123456-" + i);
            MemCachedManager.put("user" + i, userBean, 0);
            Object obj = MemCachedManager.get("user" + i);
            Assert.assertEquals(userBean, obj);
        }
    }
}

