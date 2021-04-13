package com.jwzt.datagener.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jwzt.datagener.helper.HttpUrlConnectionUtil;
import com.jwzt.datagener.helper.MD5Coder;
import com.jwzt.datagener.lucence.MyIndexReader;


public class SearchIndexSynService {

	//其他合作者
	private String teamMates;
	
	//索引存放目录
	private String indexStoreDirectory;
	
	//是否自己创建索引
	private boolean createIndexByMyself = true;
	
	private final static Logger logger = Logger.getLogger(SearchIndexSynService.class);
	
	private final static String MD5_STRING = "www.wasu.cn";

	
	/***
	 * 接收其他应用发送过来的更新索引目录通知 
	 * @param indexLocalPath 需要更改到的索引目录
	 * @param code md5值：indexLocalPath + "www.wasu.cn" 进行md5后的代码
	 * @return
	 */
	public  boolean changeIndexPath(String indexLocalPath, String code) {
		try {
			String md5code = MD5Coder.GetMD5Code((indexLocalPath + MD5_STRING).getBytes("utf-8"));
			if(StringUtils.equals(code, md5code)){
				logger.info("接收到更新索引目录指令:" + indexLocalPath);
				MyIndexReader.replaceReader(new File(indexLocalPath));
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("接收到更新索引目录指令失败:",e);
		}
		return false;
	}
	
	/***
	 * 通知其他应用需要更改索引目录
	 */
	public  void notifyPartnerIndexPath(String buildingFileIndexPath){
		
		if(isCreateIndexByMyself() && StringUtils.isNotEmpty(teamMates)){
			String[] address = teamMates.split("[,;]");
			try {
				String md5code = MD5Coder.GetMD5Code((buildingFileIndexPath + MD5_STRING).getBytes("utf-8"));
				for(String partner : address){
					String targetXmlData = partner + "/ChangeIndexPath?indexLocalPath="+ buildingFileIndexPath +"&code="+md5code;
					logger.info("通知更新索引:" + targetXmlData);
					HttpUrlConnectionUtil.downloadUrl(targetXmlData);
				}
			} catch (UnsupportedEncodingException e1) {
				logger.error("通知更新索引出错!",e1);
			}
		}

	}
	
	public String getTeamMates() {
		return teamMates;
	}

	public void setTeamMates(String teamMates) {
		this.teamMates = teamMates;
	}

	/***
	 * 获取索引要存放的目录
	 * @return
	 */
	public File getIndexStoreDirectoryFile() {
		
		final String indexParentPath = getIndexStoreDirectory();
		System.out.println("indexParentPath:"+indexParentPath);
		final File indexPath = new File(indexParentPath);
		if(!indexPath.exists()){
			throw new IllegalArgumentException("不存在的索引目录：" + indexParentPath + "，请创建该目录!取值于classes/config/partners.properties的synchronize.indexStoreDirectory");
		}
		
		logger.info("### 索引存放目录 ###" + indexParentPath + "###createIndexByMyself=" + this.isCreateIndexByMyself());
		return indexPath;
	}

	public String getIndexStoreDirectory() {
		
		return indexStoreDirectory;
	}
	/**
	 * 获取一个新的索引目录名称以".building"结尾，用于存放准备创建的索引
	 * @return
	 */
	public File newIndexBuildingFile(){
		
		return new File(indexStoreDirectory,System.currentTimeMillis() + MyIndexReader.BUILDING_TAG);
	}
	
	/***
	 * 重新改名"1438236383152.building" 为 "1438236383152"
	 * @param buildingFile
	 * @return 返回改名后的文件的名称
	 */
	public File reNameIndexBuildingFile(File buildingFile){
		
		File newFile = new File(buildingFile.getParentFile(),FilenameUtils.getBaseName(buildingFile.getName()));
		buildingFile.renameTo(newFile);
		return newFile;
	}
	
	
	public void setIndexStoreDirectory(String indexStoreDirectory) {
		this.indexStoreDirectory = indexStoreDirectory;
	}

	/**
	 * 	是否由自己创建索引
	 * true:自己创建索引，false：自己不创建索引，让其他程序创建
	 * @return
	 */
	public boolean isCreateIndexByMyself() {
		return createIndexByMyself;
	}

	public void setCreateIndexByMyself(boolean createIndexByMyself) {
		this.createIndexByMyself = createIndexByMyself;
	}
	
	
}
