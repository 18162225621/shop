package com.jwzt.datagener.ehcache;

import org.apache.log4j.Logger;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class NotNullCacheEventListener implements CacheEventListener {
	private static Logger logger = Logger.getLogger(NotNullCacheEventListener.class);
	
	public static final CacheEventListener INSTANCE = new NotNullCacheEventListener();

	public void notifyElementRemoved(Ehcache arg0, Element arg1)
		throws CacheException {
		// TODO Auto-generated method stub
		logger.info("通知元素移除"+arg1.getKey());

	}
	
	public void notifyElementPut(Ehcache arg0, Element arg1)
		throws CacheException {
		// TODO Auto-generated method stub
		logger.info("通知元素添加"+arg1.getKey());

	}
	
	public void notifyElementUpdated(Ehcache arg0, Element arg1)
		throws CacheException {
		// TODO Auto-generated method stub
		logger.info("通知元素修改"+arg1.getKey());

	}
	
	public void notifyElementEvicted(Ehcache arg0, Element arg1) {
		// TODO Auto-generated method stub
		logger.info("通知元素驱赶"+arg1.getKey());
	}

	public void notifyElementExpired(Ehcache arg0, Element arg1) {
		// TODO Auto-generated method stub
		logger.info("元素["+arg1.getKey()+"]到达有效时间,系统自动从缓存中清除");

	}
	
	public void notifyRemoveAll(Ehcache arg0) {
		// TODO Auto-generated method stub
		logger.info("通知元素移除所有");
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
		logger.info("处理，安排");
	}
	
	@Override  
    public Object clone() throws CloneNotSupportedException {  
        throw new CloneNotSupportedException("Singleton instance");  
    } 

}
