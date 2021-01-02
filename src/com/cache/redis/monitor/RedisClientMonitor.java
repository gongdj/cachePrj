package com.cache.redis.monitor;

//import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;

import com.cache.redis.service.base.BaseService;
import com.cache.redis.util.RedisConstant;

/**
 * @Title: RedisClientMonitor.java
 * @Package com.cache.redis.monitor
 * @Description: redis monitor service
 * @Copyright: Copyright (c) 2016 Company:广东心怡科技物流有限公司
 * 
 * @author gongdj
 * @date 2016年7月2日	下午11:13:36
 * @version V1.0
 */
@Component("redisClientMonitor")
public class RedisClientMonitor extends BaseService{

//	@Resource
//	private BaseService redisBaseService;
	
	public RedisClientMonitor(){
		
	}
	
	/**
	  * getServerInfo(获取Redis Info信息里面的server段)
	  *
	  * @Title: getServerInfo
	  * @Description: TODO
	  * @param @return    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	private String getInfoBySection(final String section){
		String sectionInfo = "";
		
		Jedis jedis =  null;
		try {
			jedis = super.getConnection();
			if(StringUtils.isEmpty(section)) {
				sectionInfo = jedis.info();
			} else {
				sectionInfo = jedis.info(section);
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.closedJedis(jedis);
		}
		
		super.logger.info("redis " + section + " Info server:" + sectionInfo);
		return sectionInfo;
	}
	
	public static void main(String[] args) throws Exception {
		RedisClientMonitor monitor = new RedisClientMonitor();
		monitor.getInfoBySection("server");
		monitor.getInfoBySection(RedisConstant.InfoSection.INFO_SECTION_KEYSPACE);
	}
	
}


