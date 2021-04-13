package com.jwzt.datagener.ehcache;

import org.apache.log4j.Logger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;


public class JwztEhcache {
	final static CacheManager manager = CacheManager.create(getSystemPath()+"config/ehcache-setting.xml");
	final static Logger logger = Logger.getLogger(JwztEhcache.class);
	static Cache userTokenCache = manager.getCache("userToken");
	
	public static String getSystemPath(){
		String classPath =  JwztEhcache.class.getResource("").getPath();
		return classPath.substring(0,(classPath.lastIndexOf("classes")+8));
	}
	
//	/**
//	 * 向缓存中添加对象
//	 * @param userTokenInfo
//	 */
//	public static void addCache(UserTokenWMToken utwm){
//		String key = utwm.getTvid()+"_"+utwm.getNewsid()+"_"+utwm.getBandwidth();
//		Element userTokenElement = userTokenCache.get(key);
//		if(userTokenElement ==  null){
//			userTokenCache.acquireWriteLockOnKey(key);
//			try {
//				userTokenElement = new Element(key, utwm);
//				userTokenCache.put(userTokenElement);
//				logger.info("【usertoken】放入缓存中成功");
//			} catch (Exception e) {
//				// TODO: handle exception
//			}finally{
//				userTokenCache.releaseWriteLockOnKey(key);
//			}
//		}
//	}
//	
//	/**
//	 * 删除缓存中对象
//	 * @param userTokenInfo
//	 */
//	public static void deleteCach(UserTokenWMToken utwm){
//		String key = utwm.getTvid()+"_"+utwm.getNewsid()+"_"+utwm.getBandwidth();
//		Element userTokenElement = userTokenCache.get(key);
//		if(userTokenElement !=  null){
//			userTokenCache.remove(key);
//			logger.info("移除"+key+"成功！");
//		}else{
//			logger.info(key+"不存在！");
//		}
//	}

}
