package com.jwzt.datagener.model;

import com.mysql.fabric.xmlrpc.base.Data;

public class NodeNews {

	private int nodeId;
	private int newsId;
	private int inresultpage  ;
	private int status;
	private String onlinetime;
	private String offlinetime;
	
	
	public String getOnlinetime() {
		return onlinetime;
	}
	public void setOnlinetime(String onlinetime) {
		this.onlinetime = onlinetime;
	}
	public String getOfflinetime() {
		return offlinetime;
	}
	public void setOfflinetime(String offlinetime) {
		this.offlinetime = offlinetime;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public int getInresultpage() {
		return inresultpage;
	}
	public void setInresultpage(int inresultpage) {
		this.inresultpage = inresultpage;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	

}
