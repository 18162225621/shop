package com.jwzt.datagener.model;

import java.util.Map;

import org.wltea.analyzer.lucene.IKAnalyzer;



public class AppConextConfig {

	
	/**默认每页大小*/
	public  final static int DEFAULT_PAPE_SIZE = 40;

	
	private  static IKAnalyzer myAnalyzer = null;
	
	private  static IKAnalyzer seachAnalyzer = null;

	private static ConfigurationReader reader;
	


	static{
		initConfig();
	}
	
	private AppConextConfig() {
		
	}
	/***
	 * 具体的加载配置信息的方法,所有的配置可以在这里加
	 * @return
	 */
    public synchronized static void initConfig(){

    	reader = new ConfigurationReader();
    }
    
    /***
     * 获取搜索的配置信息
     * @return
     */
	public static SearchConfigure getSearchConfig() {
		return  reader.getSearchConfig();
	}
	
	/**百度各频道配置信息*/
	public static Map<String, BaiduChannelProperty> getBaiduChannels() {
		return reader.getBaiduChannels();
	}
	
	public static Map<String ,BaiDuType> getBaiduTypes(){
		return reader.getBaiduTypes();
	}
	
	public static Map<String ,VarietyList> getVarietyLists(){
		return reader.getVarietyLists();
	}

	/***
	 * 最细粒度分词器
	 * @return
	 */
	public static IKAnalyzer getAnalyzer() {
		
		if(myAnalyzer != null){
			return myAnalyzer;
		}
		myAnalyzer = new IKAnalyzer();
		return myAnalyzer;
	}
	
	/***
	 * 最智能分词器
	 * @return
	 */
	public static IKAnalyzer getSearchAnalyzer() {
		
		if(seachAnalyzer != null){
			return seachAnalyzer;
		}
		seachAnalyzer = new IKAnalyzer(true);
		return seachAnalyzer;
	}

}
