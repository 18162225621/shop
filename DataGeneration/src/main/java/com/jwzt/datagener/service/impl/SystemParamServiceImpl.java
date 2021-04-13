package com.jwzt.datagener.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.jwzt.datagener.dao.SystemParamDao;
import com.jwzt.datagener.model.SystemParam;
import com.jwzt.datagener.service.SystemParamService;

public class SystemParamServiceImpl implements SystemParamService {

	@Autowired
	SystemParamDao systemParamDao;
	
	@Override
	public SystemParam getIncrementVersion() {
		// TODO Auto-generated method stub
		return systemParamDao.getIncrementVersion();
	}

	@Override
	public SystemParam getOpenAllAsset() {
		// TODO Auto-generated method stub
		return systemParamDao.getOpenAllAsset();
	}

	@Override
	public int update(SystemParam systemParam) {
		// TODO Auto-generated method stub
		return systemParamDao.update(systemParam);
	}

}
