package com.example;

/**
 * @Title: Test.java
 * @Package com.example
 * @Description: TODO
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author gongdj
 * @date 2016年7月3日	上午11:43:33
 * @version V1.0
 */
public class Test {

	/**
	 * main(这里用一句话描述这个方法的作用)
	 *
	 * @Title: main
	 * @Description: TODO
	 * @param @param args    设定文件
	 * @return void    返回类型
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


