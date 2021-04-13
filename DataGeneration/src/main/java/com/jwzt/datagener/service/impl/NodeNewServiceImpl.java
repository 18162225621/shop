package com.jwzt.datagener.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jwzt.datagener.dao.NodeNewDao;
import com.jwzt.datagener.helper.StringHelper;
import com.jwzt.datagener.lucence.NewsSearch;
import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.NodeNews;
import com.jwzt.datagener.model.Pg;
import com.jwzt.datagener.model.Program;
import com.jwzt.datagener.model.ProgramType;
import com.jwzt.datagener.model.SearchConfigure;
import com.jwzt.datagener.service.NodeNewService;


public class NodeNewServiceImpl implements NodeNewService {
	private static Logger logger = Logger.getLogger(NodeNewServiceImpl.class);
	
	@Autowired
	NodeNewDao nodeNewsDao;

	@Override
	public List<NodeNews> getAllNodeNews() {
		// TODO Auto-generated method stub
		System.out.println("调用方法");
		List<NodeNews> nnlist = nodeNewsDao.getAllNodeNews();
		for (NodeNews nodeNews : nnlist) {
			logger.info(nodeNews.getNodeId()+";"+nodeNews.getNewsId());
			System.out.println(nodeNews.getNodeId()+";"+nodeNews.getNewsId());
		}
		return nodeNewsDao.getAllNodeNews();
	}

	@Override
	public void insert(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		if(null == nodeNewsDao){
			logger.info("nodeNews is null");
		}
		nodeNewsDao.insert(nodeNews);
	}

	@Override
	public List<NodeNews> getOnlineNew(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		return nodeNewsDao.getOnlineNew(nodeNews);
	}

	@Override
	public int getOnLineColumn(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		return nodeNewsDao.getOnLineColumn(nodeNews);
	}

	@Override
	public int updateStatus(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		return nodeNewsDao.updateStatus(nodeNews);
	}

	@Override
	public NodeNews getNodeNews(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		return nodeNewsDao.getNodeNews(nodeNews);
	}

	@Override
	public List<NodeNews> getNodeNewsByNodeId(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		return nodeNewsDao.getNodeNewsByNodeId(nodeNews);
	}

	@Override
	public int updateInresultpage(NodeNews nodeNews) {
		// TODO Auto-generated method stub
		return nodeNewsDao.updateStatus(nodeNews);
	}

}
