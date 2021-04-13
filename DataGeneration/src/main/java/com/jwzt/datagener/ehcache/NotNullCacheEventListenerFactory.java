package com.jwzt.datagener.ehcache;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

public class NotNullCacheEventListenerFactory extends CacheEventListenerFactory {

	@Override
	public CacheEventListener createCacheEventListener(Properties arg0) {
		// TODO Auto-generated method stub
		return NotNullCacheEventListener.INSTANCE;
	}

}
