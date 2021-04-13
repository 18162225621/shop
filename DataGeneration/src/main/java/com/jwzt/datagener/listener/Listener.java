package com.jwzt.datagener.listener;


import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jwzt.datagener.helper.JsonUtil;
import com.jwzt.datagener.lucence.FileIndexCreater;
import com.jwzt.datagener.lucence.MyIndexReader;
import com.jwzt.datagener.mgr.DataManager;
import com.jwzt.datagener.mgr.ProgramParser;
import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.ConfigurationReader;
import com.jwzt.datagener.model.Node;
import com.jwzt.datagener.model.NodeTree;
import com.jwzt.datagener.model.Program;
import com.jwzt.datagener.model.SystemParam;
import com.jwzt.datagener.service.NodeNewService;
import com.jwzt.datagener.service.SystemParamService;
import com.jwzt.datagener.service.impl.AllAssetMgr;
import com.jwzt.datagener.service.impl.IncrementMgr;
import com.jwzt.datagener.service.impl.SearchIndexSynService;

@Service
public class Listener implements ApplicationListener<ContextRefreshedEvent> {
	
	private static Logger logger = Logger.getLogger(Listener.class);
	
	private static boolean endAll = false;

	@Autowired
	SystemParamService systemParamService;

	@Autowired
	NodeNewService nodeNewService;
	
	@Autowired
	SearchIndexSynService searchIndexSynService;
	
	@Autowired
	AllAssetMgr allAssetMgr;
	
	@Autowired
	IncrementMgr incrementMgr;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() != null){
			return;
		}
		
		final File indexParentPath = searchIndexSynService.getIndexStoreDirectoryFile();
		
		//初始化使用旧索引
		MyIndexReader.initWithOldIndexPath(indexParentPath);

		new Thread() {
			
			public void run() {
				
					while(true){

						FileIndexCreater fileIndex = null;
						logger.info("【构建索引线程】启动监听器");
						try {
							//是否创建索引,创建树是必须的,所以放前面
							if(createNodeTree()){
								doCalculateNotSearchNodeIds();
								if(NodeTree.nodeTree.keySet().size()<0){
									logger.info("【构建索引线程】构造的树集合为0，不正常，不会进行索引重建，程序停止。");
									return ;
								}
								fileIndex = new FileIndexCreater();					
								//获取一个新的搜索目录
								File buildingPath = searchIndexSynService.newIndexBuildingFile();
								if(fileIndex.createIndexWriter(buildingPath)){
									long start = System.currentTimeMillis();
									for(String id : NodeTree.nodeTree.keySet()){
										if(DataManager.nodeInResult(id)){
											//只有放行的栏目，才会去解析和放入缓存
											List<Program> list = fileIndex.createIndex(NodeTree.nodeTree.get(id).getXmlurl(),id);
											fileIndex.addTem(list);

										}
									}
									
									logger.info("【构建索引线程】一共有"+ProgramParser.notRepeatNes+"个资产");
									//关闭IndexWriter;
									fileIndex.closeIndexWriter();
									logger.info("【构建索引线程】关闭indexWriter 结束");
									if(fileIndex.totalNum > 0){
										//重新改名
										File renamedFile = searchIndexSynService.reNameIndexBuildingFile(buildingPath);
							
										//只有负责生成索引才能清除旧的索引目录
										File currentFile = MyIndexReader.getCurrentPath() == null ? null : new File(MyIndexReader.getCurrentPath()) ;
										MyIndexReader.clearOldIndexFiles(renamedFile,currentFile);
										
										//替换旧索引 
										MyIndexReader.replaceReader(renamedFile);

										logger.info("【构建索引线程】建立索引"+renamedFile+"结束!消耗时间:"+(System.currentTimeMillis() - start)/1000 + "s，节目数：" + fileIndex.totalNum);
									//该日志作为关键词监测工程更新
										logger.info("【底量】开始处理底量");
										SystemParam systemParam = systemParamService.getOpenAllAsset();
										if("true".equals(systemParam.getParam_value())){
											logger.info("【底量】执行底量的逻辑开始");
											allAssetMgr.sendAllAsset();
											logger.info("【底量】执行底量的逻辑结束，开启执行增量开关");
											
											//底量只需要在程序上线执行，修改数据库中的是否执行底量为false
											systemParam.setParam_name("openAllAsset");
											systemParam.setParam_value("false");
											systemParamService.update(systemParam);//底量资产只需要处理一次
											
											JsonUtil.showMessage();
											
										}else{
											
											logger.info("【底量】数据库中配置的是不需要处理底量");
										}
										
										endAll = true;
										logger.info("【底量】结束处理底量");
									}else{
										logger.info("【构建索引线程】获得的索引个数为："+fileIndex.totalNum +"，不会使用新的索引");
									}
								}else{
									logger.info("【构建索引线程】创建 indexWrite 失败，不会创建新的索引");
								}
							}else{
								//该日志作为关键词监测工程更新
								logger.info("【构建索引线程】不需要重新创建索引，构造树失败或者不需要自己创建索引，版本号相同");
							}
							//清除n天之前的增量数据
							clearIncrecement();
						} catch (Exception e) {
							logger.error("【构建索引线程】定时构造索引出错!", e);
						}finally{
							if(fileIndex != null){
								fileIndex.closeIndexWriter();
							}else{
								//logger.info("fileIndex == null");
							}
							try {
								sleep(60*1000*AppConextConfig.getSearchConfig().getThreadInterval());
							} catch (InterruptedException e) {
								logger.error("【构建索引线程】定时构造睡眠被打断!", e);
							}
						}
					}
			}
		}.start();
		
		
		//定时启动增量任务线上
		new Thread() {
			public void run() {
				
					while(true){
						logger.info("【增量定时线程】启动定时扫描增量任务推送的线程");
						try {
							if(!endAll){
								logger.info("【增量定时线程】建索引线程还没有处理完，不执行增量");
							}else{
								SystemParam sp = systemParamService.getOpenAllAsset();//查找底量是否要执行
								if("false".equals(sp.getParam_value())){//不执行底量，就执行增量
									logger.info("【增量定时线程】开始处理增量任务");
									incrementMgr.sendIncrementTask();
								}else{
									logger.info("【增量定时线程】不允许处理增量任务，请排查是否是底量还没有执行完");
								}
							}
							logger.info("【增量定时线程】启动定时扫描增量任务推送的线程结束");
						} catch (Exception e) { 
							logger.error("【增量定时线程】启动定时扫描增量任务推送的线程出错!", e);
						}finally{
							try {
								sleep(60*1000*AppConextConfig.getSearchConfig().getThreadDataTime());
							} catch (InterruptedException e) {
								logger.error("【增量定时线程】启动定时扫描增量任务推送的线程线程!", e);
							}
						}
					}
			}
		}.start();
		
		
		
		//定时启动增量任务线上
				new Thread() {
					public void run() {
						
						while(true){
							try {
								
								ConfigurationReader con = new ConfigurationReader();

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}finally{
								
								try {
									sleep(60*1000*30);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
							

					}
				}.start();
			
	}
	
	
	

	
	/***
	 * 重新构造栏目树结构
	 * @return
	 */
	public boolean createNodeTree(){
		boolean flag = false;
		try {
			flag = NodeTree.createNodeTree(AppConextConfig.getSearchConfig().getNodeXml());
			logger.info("构造树结束");
		} catch (Exception e) {
			logger.error("构造树时出错",e);
		}
		return flag;
	}
	
	
	private void doCalculateNotSearchNodeIds(){
		//计算并设置不需要在搜索中搜索到的栏目id
		Set<Integer> notNodeId = AppConextConfig.getSearchConfig().getNotSearchNodeList();
		Set<Integer> newNodeId = new HashSet<Integer>();
		
		for(Integer i : notNodeId){
			newNodeId.add(i);
			List<Node> nodes = NodeTree.getAllChildrenNode(i);
			if(nodes != null){
				for(Node p : nodes){
					newNodeId.add(p.getId());
				}
			}
		}
		//重新赋值
		AppConextConfig.getSearchConfig().setNotSearchNodeList(newNodeId);
	}

	/***
	 * 清除n天之前的增量节目xml数据
	 */
	private void clearIncrecement(){
		
		int days = AppConextConfig.getSearchConfig().getClearIncrecement();

		try{
			
			Calendar cal = Calendar.getInstance();
			
			cal.add(Calendar.DATE, -days);
			
			String beforeDay = cal.get(Calendar.YEAR) + String.valueOf(cal.get(Calendar.MONTH)) + cal.get(Calendar.DAY_OF_MONTH);
			
			File increcementFile = new File(AppConextConfig.getSearchConfig().getIncrementXml());
			
			if(increcementFile.exists() && increcementFile.isDirectory()){
				
				File[] files = increcementFile.listFiles();
				for(File f : files){
					if(NumberUtils.toLong(f.getName(),0) < NumberUtils.toLong(beforeDay,1)){
						FileUtils.deleteDirectory(f);
					}
				}
			}
		}catch (Exception e) {
			logger.error("删除" + days + "天之前的数据时出现错误!",e);
		}
	}
}
