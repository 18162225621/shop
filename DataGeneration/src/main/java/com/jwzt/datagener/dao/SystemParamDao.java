package com.jwzt.datagener.dao;

import com.jwzt.datagener.model.SystemParam;

public interface SystemParamDao {
	public SystemParam getIncrementVersion();
	
	public SystemParam getOpenAllAsset();
	
	public int update(SystemParam systemParam);

}
