package com.jwzt.datagener.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.jwzt.datagener.dao.NodeNewDao;
import com.jwzt.datagener.model.NodeNews;

public class NodeNewImpl extends SqlMapClientDaoSupport implements NodeNewDao {

	@Override
	public void insert(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		 getSqlMapClientTemplate().insert("addNodeNew", nodeNews);
	}

	@Override
	public List getAllNodeNews() {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("getAllNodeNews");
	}

	@Override
	public List<NodeNews> getOnlineNew(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("getOnLine", nodeNews);
	}

	@Override
	public int getOnLineColumn(NodeNews nodeNews) {
		// TODO Auto-generated method stub  
		return (Integer) getSqlMapClientTemplate().queryForObject("getOnLineColumn", nodeNews) ;
	}

	@Override
	public int updateStatus(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateStatus", nodeNews);
	}

	@Override
	public NodeNews getNodeNews(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		return (NodeNews) getSqlMapClientTemplate().queryForObject("getNodeNews", nodeNews);
	}

	@Override
	public List<NodeNews> getNodeNewsByNodeId(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		
		return getSqlMapClientTemplate().queryForList("getNodeNewsByNodeId",nodeNews);
	}

	@Override
	public int updateInresultpage(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateInresultpage", nodeNews);
	}

}
