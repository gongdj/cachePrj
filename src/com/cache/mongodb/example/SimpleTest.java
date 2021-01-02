package com.cache.mongodb.example;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

/**
 * Project Name:cachePrj<br>
 * File Name:SimpleTest.java<br>
 * Package Name:com.cache.mongodb.example<br>
 * Date:2016��1��6������9:41:51<br>
 * 
 * @author gongdj<br>
 * @version <br>
 * @Copyright (c) 2016, gdj_career2003@163.com All Rights Reserved.
 * 
 */
public class SimpleTest {

	private void test1() {
		Mongo mg = new Mongo("192.168.1.119", 27017);
		// ��ѯ���е�Database
		for (String name : mg.getDatabaseNames()) {
			System.out.println("dbName: " + name);
		}
		DB db = mg.getDB("test");
		// ��ѯ���еľۼ�����
		for (String name : db.getCollectionNames()) {
			System.out.println("collectionName: " + name);
		}
		DBCollection users = db.getCollection("users");
		// ��ѯ���е�����
		DBCursor cur = users.find();
		while (cur.hasNext()) {
			System.out.println(cur.next());
		}
		System.out.println(cur.count());
		System.out.println(cur.getCursorId());
		System.out.println(JSON.serialize(cur));
	}

	public static void main(String[] args) {

		SimpleTest example = new SimpleTest();
		example.test1();
	}

}
