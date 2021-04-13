package com.jwzt.datagener.model;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;


public class SearchConfigure {
	
	public String getWidgetUrl() {
		return widgetUrl;
	}

	public void setWidgetUrl(String widgetUrl) {
		this.widgetUrl = widgetUrl;
	}

	public String getSend_all() {
		return send_all;
	}

	public void setSend_all(String send_all) {
		this.send_all = send_all;
	}

	public String getSend_increment() {
		return send_increment;
	}

	public void setSend_increment(String send_increment) {
		this.send_increment = send_increment;
	}

	public String getSendinfo() {
		return sendinfo;
	}

	public void setSendinfo(String sendinfo) {
		this.sendinfo = sendinfo;
	}

	private static final Logger logger = Logger.getLogger(SearchConfigure.class);
	
	private static final String SEPRATOR = "#";
	
	public static final String DEFAULT_PIC_LOCATION_EPG = "epg";
	
	private int siteId;
	
	private String newsXml;
	
	private String proXml;

	private String nodeXml;
	
	private String incrementXml;
	
	/**扫描xml时间间隔 (分)*/
	private int threadInterval;

	/**	分词器选择: 0：自带的分词器，1：庖丁分词器，2：IKAnalyzer3.1.6，3：mmseg4j-1.8，4：JE分词器 ，最好选自带的*/
	private int analyzer = 0;
	
	/**建立了索引，可以通过id搜到该栏目下节目，但不需要在搜索结果中展现的栏目，如果有子栏目，系统会包含它的子栏目，如果有多个id请以#隔开*/
	private String notSearchNodes;

	private Set<Integer> notSearchNodeList;

	/**这个ppvId的节目是免费的*/
	private String freePpvId;
	
	/**图片的http前缀，图片访问的是picXml里面的图片,以"http://"开头，结尾不要带"/"*/
	private String picHttp;

	
	/**多长时间清除一下视频节目缓存，单位：小时 ，默认1小时*/
	private int clearCache;
	
	/**保留多少天之内的数据*/
	private int clearIncrecement;
	
	/**加在播放串后面的域名，如：html5-epg.wasu.tv（http://xxx.com/xxx.ts?domain=xxx），提供给思华cncms用*/
	private String domain;
	
	/**图片的位置在哪：可取值xml、epg，xml表示从xml目录里取，epg表示从epg目录取图片*/
	private String picLocation = DEFAULT_PIC_LOCATION_EPG; 
			
	/** 合作方 固定值，百度给 **/
	private String partner;
	
	/** 固定值 **/
	private String provider;
	
	/** 秘钥，固定值，百度给 **/
	private String key;
	
	/** 索引存放目录 **/
	private String indexPath;
	
	/** 定时扫描推送资产任务**/
	private int threadDataTime;
	
	/** 百度接口地址，固定值，百度给 **/
	private String baiDuUrl;
	
	/** 综艺壳存放的栏目id **/
	private String varietyShellNodeId;
	
	/**播放串加密模式*/
	private int module;
	
	private String sendinfo;
	
	private String send_all;
	private String send_increment;
	
	private String widgetUrl;
	
	public String getPicLocation() {
		return picLocation;
	}

	public void setPicLocation(String picLocation) {
		if(picLocation != null && picLocation.trim().length() > 0){
			this.picLocation = picLocation;
		}
		
	}


	
	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getSiteId() {
		return siteId;
	}

	private void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getNewsXml() {
		return newsXml;
	}

	private void setNewsXml(String newsXml) {
		this.newsXml = newsXml;
	}

	public String getNodeXml() {
		return nodeXml;
	}

	/***
	 * 从nodeXml 如：D:/www225/xml236/nodeXml/236.xml里获取战点信息
	 * @param nodeXml
	 */
	public void setNodeXml(String nodeXml) {
		this.nodeXml = nodeXml;
		try{
			File file = new File(this.nodeXml);
			setNewsXml(file.getParentFile().getParentFile().getParentFile().getPath().replace("\\", "/"));
			setProXml(this.getNewsXml());
			setIncrementXml(file.getParentFile().getParentFile().getPath().replace("\\", "/") + "/incrementXml");
			setSiteId(Integer.parseInt(file.getName().replaceAll("\\D", "")));
		}catch (Exception e) {
			logger.error("配置的nodeXml地址：" + this.nodeXml + "有误！");
		}
		logger.info("从[" + nodeXml + "]里获取到的站点信息为：{siteId:"+getSiteId()+",newsXml:"+getNewsXml()+"}");
	}

	public int getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(int analyzer) {
		this.analyzer = analyzer;
	}

	public String getFreePpvId() {
		return freePpvId;
	}

	public void setFreePpvId(String freePpvId) {
		this.freePpvId = freePpvId;
	}

	public String getPicHttp() {
		return picHttp;
	}

	public void setPicHttp(String picHttp) {
		this.picHttp = picHttp;
	}

	public int getClearCache() {
		return clearCache;
	}

	public void setClearCache(int clearCache) {
		this.clearCache = clearCache;
	}

	public int getThreadInterval() {
		return threadInterval;
	}

	public void setThreadInterval(int threadInterval) {
		this.threadInterval = threadInterval;
	}

	public String getNotSearchNodes() {
		return notSearchNodes;
	}
	
	public Set<Integer> getNotSearchNodeList() {
		return notSearchNodeList;
	}

	
	/***
	 * 设置不需要搜索的栏目
	 * @param notSearchNodes
	 */
	public void setNotSearchNodes(String notSearchNodes) {
		
		this.notSearchNodeList = getNodeList(notSearchNodes);
		this.notSearchNodes = notSearchNodes;
	}
	
	/***
	 * 获取栏目列表
	 * @param nodes
	 * @return
	 */
	private   Set<Integer> getNodeList(String nodes){
		
		 Set<Integer> set = new HashSet<Integer>();
		 if(nodes != null && !"".equals(nodes)){
			 String nodeInt [] = nodes.split(SEPRATOR);
			 for(String n : nodeInt){
				 set.add(Integer.parseInt(n.trim()));
			 }
		 }
		 return set;
	}

	public String getProXml() {
		return proXml;
	}

	private void setProXml(String proXml) {
		this.proXml = proXml;
	}

	public void setNotSearchNodeList(Set<Integer> notSearchNodeList) {
		this.notSearchNodeList = notSearchNodeList;
	}

	public String getIncrementXml() {
		return incrementXml;
	}

	public void setIncrementXml(String incrementXml) {
		this.incrementXml = incrementXml;
	}

	public int getClearIncrecement() {
		return clearIncrecement;
	}

	public void setClearIncrecement(int clearIncrecement) {
		this.clearIncrecement = clearIncrecement;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public int getThreadDataTime() {
		return threadDataTime;
	}

	public void setThreadDataTime(int threadDataTime) {
		this.threadDataTime = threadDataTime;
	}

	public String getBaiDuUrl() {
		return baiDuUrl;
	}

	public void setBaiDuUrl(String baiDuUrl) {
		this.baiDuUrl = baiDuUrl;
	}

	public String getVarietyShellNodeId() {
		return varietyShellNodeId;
	}

	public void setVarietyShellNodeId(String varietyShellNodeId) {
		this.varietyShellNodeId = varietyShellNodeId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
	
}
