package com.example;

/**
 * @Title: Test.java
 * @Package com.example
 * @Description: TODO
 * @Copyright: Copyright (c) 2016 Company:�㶫�����Ƽ��������޹�˾
 * 
 * @author gongdj
 * @date 2016��7��3��	����11:43:33
 * @version V1.0
 */
public class Test {

	/**
	 * main(������һ�仰�����������������)
	 *
	 * @Title: main
	 * @Description: TODO
	 * @param @param args    �趨�ļ�
	 * @return void    ��������
	 * @throws
	 */
	public static void main(String[] args) {
		
		String path = "/wms/alog/redisEnable";
		String[] strs = path.split("/");
		String separator = "/", newPath = "";
		for(String str : strs) {
			if(str.equals("")) continue;
			newPath += separator + str;
			System.out.println(newPath);
		}
	}

}


