package com.jwzt.datagener.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

public class ConfigurationReader {





	private static Logger logger = Logger.getLogger(ConfigurationReader.class);

	
	private Digester digester;
	
	private SearchConfigure searchConfig;
	
	private Map<String, BaiduChannelProperty> baiduChannels;
	
	private Map<String ,BaiDuType>  baiduTypes;
	
	private Map<String ,VarietyList>  varietyLists;
	
	
	public ConfigurationReader() {
		
		this.loadConfig();
		//this.loadBauDuTypes();
		this.loadvarietyLists();
	}
	
	
	
	private synchronized void initChannelMap(){
		
		if(baiduChannels == null)
			baiduChannels = Collections.synchronizedMap(new HashMap<String, BaiduChannelProperty>(15));
	}
	
	private synchronized void initBaiduType(){
		if(null == baiduTypes){
			baiduTypes = Collections.synchronizedMap(new HashMap<String,BaiDuType>(100));
		}
	}
	
	private synchronized void initvarietyLists(){
		if(null == varietyLists){
			varietyLists = Collections.synchronizedMap(new HashMap<String,VarietyList>(100));
		}
	}
	
	
	public void addBaiduChannel(BaiduChannelProperty channel){
		
		if(baiduChannels == null){
			initChannelMap();
		}
		if(channel.getName() != null) baiduChannels.put(channel.getName(), channel);
	}
	
	public void addBaibuTypes(BaiDuType baiduType){
		if(null == baiduTypes){
			//实例化baiduTypes
			initBaiduType();
		}
		if(!"".equals(baiduType.getId()) && !"".equals(baiduType.getValue())){
			//logger.info("添加栏目配置信息："+baiduType.getId());
			baiduTypes.put(baiduType.getId(), baiduType);
		}
	}
	
	public void addvarietyLists(VarietyList varietyList){
		if(null == varietyLists){
			//实例化baiduTypes
			initvarietyLists();
		}
		if(!"".equals(varietyList.getId())){
			//logger.info("添加综艺壳配置信息："+varietyList.getId());
			varietyLists.put(varietyList.getId(), varietyList);
		}
	}
	
	public void addSearchConfig(SearchConfigure searchConfig){
		this.searchConfig = searchConfig;
	}
	
	
	public Map<String, BaiduChannelProperty> getBaiduChannels() {
		return baiduChannels;
	}

	public SearchConfigure getSearchConfig() {
		return searchConfig;
	}
	
	public Map<String, BaiDuType> getBaiduTypes(){
		return baiduTypes;
	}
	
	public Map<String, VarietyList> getVarietyLists() {
		return varietyLists;
	}



	/***
	 * 加载配置信息
	 * @return
	 */
	private void loadConfig(){
		
		logger.info("========================");
		//初始化数据
		digester = new Digester();
		digester.setClassLoader(Thread.currentThread().getContextClassLoader());
		digester.push(this);
		digester.setValidating(false);
		
		//加载系统配置信息
		final String sysXpath = "system/system-config";
		digester.addObjectCreate(sysXpath, SearchConfigure.class);
		digester.addSetProperties(sysXpath);
		digester.addSetNext(sysXpath, "addSearchConfig");
		
		//加载百度各频道首页配置信息
		final String channelsXpath = "system/baidu_config/channels/channel";
		digester.addObjectCreate(channelsXpath, BaiduChannelProperty.class);
		digester.addSetProperties(channelsXpath);
		digester.addSetNext(channelsXpath, "addBaiduChannel");
		
		try {
			digester.parse(new ClassPathResource("config/system.xml").getInputStream());
			logger.info("加载配置文件信息成功!");
		} catch (Exception e) {
    		logger.error("加载配置文件system.xml信息失败!",e);
		}
	}
	
	private void loadBauDuTypes(){
		digester = new Digester();
		digester.setClassLoader(Thread.currentThread().getContextClassLoader());
		digester.push(this);
		digester.setValidating(false);
		
		
		final String baiduTypepath = "baidu_types/baiDuType";
		digester.addObjectCreate(baiduTypepath, BaiDuType.class);
		digester.addSetProperties(baiduTypepath);
		digester.addSetNext(baiduTypepath, "addBaibuTypes");
		
		try {
			digester.parse(new ClassPathResource("config/nodeid.xml").getInputStream());
			logger.info("加载百度type配置文件信息成功!");
		} catch (Exception e) {
    		logger.error("加载百度type信息失败!",e);
		}
	}
	
	
	
	private void loadvarietyLists(){
		digester = new Digester();
		digester.setClassLoader(Thread.currentThread().getContextClassLoader());
		digester.push(this);
		digester.setValidating(false);
		
		
		final String varietyinfo = "varietyListFilter/varietyList";
		digester.addObjectCreate(varietyinfo, VarietyList.class);
		digester.addSetProperties(varietyinfo);
		digester.addSetNext(varietyinfo, "addvarietyLists");
		
		try {
			digester.parse(new ClassPathResource("config/ahgplayinfo.xml").getInputStream());
			logger.info("加载综艺壳配置文件信息成功!");
		} catch (Exception e) {
    		logger.error("加载综艺壳信息失败!",e);
		}
	}
}
