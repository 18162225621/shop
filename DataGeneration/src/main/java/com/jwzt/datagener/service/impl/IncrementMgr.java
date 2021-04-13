package com.jwzt.datagener.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.LoginContext;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jwzt.datagener.helper.DateHelper;
import com.jwzt.datagener.helper.StringHelper;
import com.jwzt.datagener.helper.XmlUtil;
import com.jwzt.datagener.lucence.FileIndexCreater;
import com.jwzt.datagener.lucence.NewsSearch;
import com.jwzt.datagener.mgr.DataManager;
import com.jwzt.datagener.mgr.FileMgr;
import com.jwzt.datagener.mgr.ProgramParser;
import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.MyHelper;
import com.jwzt.datagener.model.NodeNews;
import com.jwzt.datagener.model.NodeTree;
import com.jwzt.datagener.model.Pg;
import com.jwzt.datagener.model.Program;
import com.jwzt.datagener.model.ProgramType;
import com.jwzt.datagener.model.SystemParam;
import com.jwzt.datagener.service.NodeNewService;
import com.jwzt.datagener.service.OffLineService;
import com.jwzt.datagener.service.OnLineService;
import com.jwzt.datagener.service.SystemParamService;


public class IncrementMgr{
	private static Logger logger = Logger.getLogger(IncrementMgr.class);
	
	private String filePath = AppConextConfig.getSearchConfig().getSendinfo();

	@Autowired
	SystemParamService systemParamService;
	
	@Autowired
	NodeNewService nodeNewService;
	
	@Autowired
	OnLineService onLineService;
	
	@Autowired
	OffLineService offLineService;
	
	@SuppressWarnings("unused")
	public void sendIncrementTask() {
		// TODO Auto-generated method stub
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		boolean createfalg = false;
		String fullPath = filePath + File.separator+"wasujx_"+ sdf.format(new Date())+ "_increment.json";

		try {
			
			// 保证创建一个新文件
			File filepath = new File(fullPath);
			if (!filepath.getParentFile().exists()) { // 如果父目录不存在，创建父目录
				filepath.getParentFile().mkdirs();
			}
			
			
			Document doc = null;
			Pg pg = null;
			//先获得数据库中存储的版本号
			SystemParam systemParam = systemParamService.getIncrementVersion();
			String version = DateHelper.getDate();//默认从系统当前时间的增量文件开始解析
			String incrementVersion = DateHelper.getDate()+"000000000";//默认从系统当前时间的增量文件开始解析
			
			if(null != systemParam){
				//系统中已经存入了版本，从系统中的版本开始解析
				if(systemParam.getParam_value().length() >= 8){
					incrementVersion = systemParam.getParam_value();
					version = incrementVersion.substring(0, 8);
				}
				logger.info("【增量任务】数据库记录增量版本号为："+incrementVersion);
			}
			
			logger.info("【增量任务】程序使用的增量版本号为："+ incrementVersion);
			List<File> xmlfileList = FileMgr.getFileList(version, incrementVersion);
			
			if(null == xmlfileList || xmlfileList.size() <= 0){
				logger.info("【增量任务】目录"+version+"下没有需要处理的增量文件");
				String systemCurrentData = DateHelper.getDate();
				//将字符串日期转换成时间，比较大小
				if(DateHelper.isGt(systemCurrentData,version)){//currentData 是否大于version
					logger.info("【增量任务】目录"+ version+"已经没有增量文件，且现在是:"+ systemCurrentData+",开始处理"+systemCurrentData+"天的增量任务");
					String defaulIncrementVersion = DateHelper.getDate()+"000000000";//默认从系统当前时间的增量文件开始解析
					SystemParam sp = new SystemParam();
					sp.setParam_name("incrementVersion");
					sp.setParam_value(defaulIncrementVersion);
					int temp = systemParamService.update(sp);
					logger.info("【增量任务】修改数据库中incrementVersion="+ defaulIncrementVersion+"为默认值的结果是："+ temp);
				}
			}
			//解析增量任务
			for(File file: xmlfileList){
				//循环任务列表，处理任务
				String xmlPath = FileMgr.getIncrementXmlPath(file.getName());
				logger.info("【增量任务】开始解析增量xml,当前xml地址："+xmlPath.toString());
				incrementVersion = file.getName().substring(0, file.getName().indexOf(".xml"));
				logger.info("【增量任务】当前任务的版本号：incrementVersion:"+ incrementVersion);
				doc = MyHelper.getProgramDocument(xmlPath.toString());
				if(null == doc){
					logger.error("【增量任务】doc 对象为空");
					continue;
				}
				try{
					List<Element> elements = doc.getRootElement().element("programs").elements("program");
					
					for (Element element : elements) {
						JSONObject baiduAssetStatus = null;
						pg = XmlUtil.parseIncrementXml(element);
						
						//先判断是不是需要屏蔽的栏目
						if(!DataManager.nodeInResult(pg.getNode_id()+"")){  
							logger.info("【增量任务】当前栏目【"+pg.getNode_id()+"】是被屏蔽的栏目，任务丢弃");
							continue;
						}
						
						//添加资产对应的栏目上线的记录
						NodeNews nodeNew = new NodeNews();
						if(1 == pg.getIs_online()){//上线
							logger.info("【增量任务】资产【"+pg.getId()+"==>nodeid:"+pg.getNode_id()+"】需要上线");
							
							//判断是否为需要发送给易视腾的栏目资产--非影视剧
							if(!AppConextConfig.getVarietyLists().containsKey(String.valueOf(pg.getNode_id())) &&((pg.getVideo_type()).equals("column") ||(pg.getVideo_type()).equals("news"))){
								logger.info(AppConextConfig.getVarietyLists().containsKey(pg.getNode_id())+"此栏目资产还未进行栏目ID报备"+pg.getNode_id()+"==>"+pg.getVideo_type());
								continue ;
							}
							Date date = new Date(pg.getOnlinetime());//用Date构造方法，将long转Date
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String stringDate = format.format(date);//用SimpleDateFormat将Date转xxxx-xx-xx格式的字符
								nodeNew.setNewsId(Integer.parseInt(pg.getId()));
								nodeNew.setNodeId(pg.getNode_id());
								
								NodeNews querynn = nodeNewService.getNodeNews(nodeNew);
								if(querynn == null){
									
									if(!createfalg){
										createfalg = true;
										filepath.createNewFile();
									}
									
									baiduAssetStatus =onLineService.movieAndNewsOnLine(pg);
									nodeNew.setOnlinetime(stringDate);
									nodeNew.setOfflinetime(null);
									nodeNew.setInresultpage(1);
									nodeNew.setStatus(1);
									nodeNewService.insert(nodeNew);
									logger.info("【增量任务】新增资产【"+pg.getId()+"==>nodeid:"+pg.getNode_id()+",上线或下线："+pg.getIs_online()+"】添加资产对应的栏目记录");
										
								}else{
									int status =querynn.getStatus();
									if(status == 1){
										logger.info("数据库中已存在，该任务丢弃！");
										continue;
									}else{
										if(!createfalg){
											createfalg = true;
											filepath.createNewFile();
										}
										baiduAssetStatus =onLineService.movieAndNewsOnLine(pg);
										nodeNew.setStatus(1);
										int updateNum = nodeNewService.updateStatus(nodeNew);
										logger.info("【增量任务】修改资产【"+pg.getId()+"==>nodeid:"+pg.getNode_id()+",上线或下线："+pg.getIs_online()+"】添加资产对应的栏目记录");
									
									}
								}
							
						}else if(0 == pg.getIs_online()){//下线 只用填写id,pid，partner，resource_status，name
							logger.info("【增量任务】资产【"+pg.getId()+"==>nodeid:"+pg.getNode_id()+"】需要下线");
							int offNewId = StringHelper.stringParseInt(pg.getId(),-1);
							pg.setOfflinetime(DateHelper.getDateString(incrementVersion));
							if(0 != offNewId){
								//根据资产id，搜索资产，获得资产类型
								NewsSearch newSearch = new NewsSearch();
								logger.info("offNewId"+offNewId);
								newSearch.setId(offNewId);
								
							}else{
								logger.info("【增量任务】【newsid:"+pg.getId()+"==>nodeid:"+pg.getNode_id()+"】 offNewId = "
										+offNewId+"被当做非综艺下线");
							}
							
							nodeNew.setNewsId(Integer.parseInt(pg.getId()));
							nodeNew.setNodeId(pg.getNode_id());
							
							NodeNews querynn = nodeNewService.getNodeNews(nodeNew);
							
							if(null != querynn){
								if(querynn.getStatus() == 1){
									
									
									NewsSearch newquery = new NewsSearch();
									newquery.setId(Integer.parseInt(pg.getId()));
									
									if(null != newquery.getProgramList() && newquery.getProgramList().size() > 0){
										Program program =newquery.getProgramList().get(0);
										if(null != program){
											
											if(!createfalg){
												createfalg = true;
												filepath.createNewFile();
											}
											
											  pg.setTitle(program.getTitle());
											  pg.setPpvid(program.getPpv_id());
											  pg.setVideo_type(program.getVideo_type());
										   }
										logger.info("下线资产完成");
									}else{
										logger.info("没有找到需要下线的资产，该任务丢弃！");
										continue ;
									}

									//修改状态
									nodeNew.setStatus(2);
									nodeNew.setOfflinetime(pg.getOfflinetime());
									baiduAssetStatus = offLineService.offLineMovieAndService(pg);
									int updateNum = nodeNewService.updateStatus(nodeNew);

									logger.info("【增量任务】资产【"+pg.getId()+"==>nodeid:"+pg.getNode_id()+",上线或下线："+pg.getIs_online()+"】修改状态为："
											+ nodeNew.getStatus()+",结果为："+ updateNum);
								}else{
									logger.info("【增量任务】资产【"+pg.getId()+"==>nodeid:"+pg.getNode_id()+",上线或下线："+pg.getIs_online()+"】在数据库中已经存在关联关系");
									continue;
								}								
							}else{
								logger.info("【增量任务】资产【"+pg.getId()+"==>nodeid:"+pg.getNode_id()+"没有上线，无法处理下线！");
								continue;
							}						
							
						}
						
						Writer write = new OutputStreamWriter(
								new FileOutputStream(filepath, true), "UTF-8");
						try {
							write.write(baiduAssetStatus.toString() + "\n");
						} catch (Exception e) {
							// TODO: handle exception
						}finally{
							write.flush();
							write.close();
						}
							
						}
						
						//修改数据库中版本号的记录
						if(incrementVersion.length() > 8){
							SystemParam sp = new SystemParam();
							sp.setParam_name("incrementVersion");
							sp.setParam_value(incrementVersion);
							int temp = systemParamService.update(sp);
							logger.info("【增量任务】修改数据库中incrementVersion="+ incrementVersion+"的结果是："+ temp);
						}
					
				}catch(Exception e){
					logger.error("【增量任务】处理增量任务出现异常。"+e.toString());
					e.printStackTrace();
				}
			
			}	
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		
	}
}
  