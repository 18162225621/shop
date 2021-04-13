package com.jwzt.datagener.dao;

import java.util.List;

import com.jwzt.datagener.model.NodeNews;



public interface NodeNewDao {

	public void insert(NodeNews nodeNews);
	public List<NodeNews> getAllNodeNews();
	public NodeNews getNodeNews(NodeNews nodeNews);
	public List<NodeNews> getOnlineNew(NodeNews nodeNews);
	public List<NodeNews> getNodeNewsByNodeId(NodeNews nodeNews);
	public int getOnLineColumn(NodeNews nodeNews);
	public int updateStatus(NodeNews nodeNews);
	public int updateInresultpage(NodeNews nodeNews);
}
