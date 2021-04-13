package com.jwzt.datagener.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.jwzt.datagener.dao.SystemParamDao;
import com.jwzt.datagener.model.SystemParam;

public class SystemParamImpl extends SqlMapClientDaoSupport implements SystemParamDao {

	@Override
	public SystemParam getIncrementVersion() {
		// TODO Auto-generated method stub
		return (SystemParam) getSqlMapClientTemplate().queryForObject("getIncrementVersion");
	}

	@Override
	public SystemParam getOpenAllAsset() {
		// TODO Auto-generated method stub
		return (SystemParam) getSqlMapClientTemplate().queryForObject("getOpenAllAsset");
	}

	@Override
	public int update(SystemParam systemParam) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateParamValue",systemParam);
	}

}
