package com.jwzt.datagener.mgr;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.jwzt.datagener.helper.FileUtil;
import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.SearchConfigure;

public class FileMgr {
	
	private static Logger logger = Logger.getLogger(FileMgr.class);
	
	public static List<File> getFileList(String version,String incrementVersion){
		logger.info("【增量任务】定时线程开始处理第"+version+"天的增量资产");
		List<File> xmlfileList = null ;
		SearchConfigure  searchConfig =  AppConextConfig.getSearchConfig();
		//先找出所有的增量文件
		String incrementPath = searchConfig.getIncrementXml();
		File incrementFile = new File(incrementPath);
		if(incrementFile.exists() && incrementFile.isDirectory() && version != null && version.length() >= 8){
			Long currentfolder = Long.parseLong(version);
			Long currentFilePrefix = Long.parseLong(incrementVersion);
			
			/**获取到需要的文件夹*/
			List<File> folderList =FileUtil.filterFolder(incrementFile,currentfolder);
			logger.info("【增量任务】在"+incrementPath+"/"+currentfolder+"一共获得"+folderList.size()+"个文件夹");
			/**获取到需要的文件*/
			xmlfileList = FileUtil.filterFile(folderList,currentFilePrefix);
			logger.info("【增量任务】获取目录"+incrementPath+"/"+currentfolder+"下"+currentFilePrefix+"文件后面的增量文件");
			/**排序*/
			Collections.sort(xmlfileList);
			logger.info("【增量任务】文件排序");

		}else{
			logger.info("【增量任务】增量目录【"+incrementPath+"】不存在，或不是目录格式");
		}
		
		return xmlfileList;
	}

	
	public static String getIncrementXmlPath (String fileName){
		String date = fileName.substring(0,8);
		SearchConfigure  searchConfig =  AppConextConfig.getSearchConfig();
		StringBuffer xmlPath = new StringBuffer();
		xmlPath.append(searchConfig.getNewsXml())
				.append("/xml")
				.append(searchConfig.getSiteId())
				.append("/incrementXml/")
				.append(date).append("/")
				.append(fileName);
		return xmlPath.toString();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
