package com.jwzt.datagener.lucence;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.MMapDirectory;


/**
 * 保证一个搜索目录只有一个IndexReader与这对应
 * 在整个搜索中，保证对每个用户的搜索调用的都是同一个IndexReader实例，因为重新打开IndexReader需要比较大的系统开销
 * @version 
 */
public class MyIndexReader {
	
	
	public static Logger logger = Logger.getLogger(MyIndexReader.class);

	/**系统唯一的reader*/
	private static IndexReader  unique_Index_Reader = null;
	
	/**系统当前的索引路径*/
	public volatile static String currentPath = null;

	/**在创建的标记*/
	public static final String BUILDING_TAG = ".building";
	
	/**是否正在写*/
	private  volatile static boolean writing = false;
	
	private static final Lock lock = new ReentrantLock();
	
	private static final Condition condition = lock.newCondition();
	
	public  static final IndexReader getReaderByPath(){

		return unique_Index_Reader;
	}
	
	/***
	 * 替换旧索引
	 * @param wantedPath
	 */
	public  static void replaceReader(File newIndexPath){
		if(newIndexPath == null){
			logger.info("索引对象为空!");
			return ;
		}	
		lock.lock();
		try{
			if(!StringUtils.equals(currentPath, newIndexPath.getAbsolutePath())){
			     if(!writing) {
			    	  writing = true;
			    	  try {
			    		    boolean firstCreate = StringUtils.isEmpty(currentPath);
			    		    if(firstCreate){
			    		    	logger.info("服务器刚重启，打开旧索引[" + newIndexPath.getAbsolutePath() + "]开始!");
			    		    }else{
			    		    	logger.info("打开新索引[" + newIndexPath.getAbsolutePath() + "]开始!");
			    		    }
							IndexReader newReader = DirectoryReader.open(new MMapDirectory(newIndexPath));
							currentPath = newIndexPath.getAbsolutePath();
							IndexReader oldReader = unique_Index_Reader;
							unique_Index_Reader = newReader;
							if(oldReader != null){
								oldReader.close();
							}
			    		    if(firstCreate){
			    		    	logger.info("服务器刚重启，打开旧索引[" + newIndexPath.getAbsolutePath() + "]完毕!");
			    		    }else{
			    		    	logger.info("打开新索引[" + newIndexPath.getAbsolutePath() + "]完毕!");
			    		    }
						} catch (IOException e) {
							logger.error("新旧索引切换时出错!",e);
						}
				      writing = false;
				      condition.signal();
				 }
			
			}else{
				try {
					if(writing){
						condition.await();
					}
				} catch (InterruptedException e) {
					logger.error("索引打开时遇到错误!", e);
				}
			}
		}finally{
			lock.unlock();
		}
			
	}

	/***
	 * 初始化：获取旧索引目录,找到最近修改的那个文件
	 * 并设置刚动时的索引
	 * @param indexParentPath 索引目录的父目录
	 * @return
	 */
	public static File initWithOldIndexPath(File indexParentPath ) {
	
		File[]  indexs = indexParentPath .listFiles();
		long tmp = 0;
		File filePath = null;
		for(File last : indexs){
			long lastTime =  NumberUtils.toLong(last.getName(), -1);
			if(lastTime > tmp && !last.getName().endsWith(BUILDING_TAG)
					&& last.listFiles() != null && lastTime > 0){//查找最后修改的，且不为空的目录,且目录名称为时间撮的
				filePath = last;
				tmp = lastTime;
			}
		}
		//
		replaceReader(filePath);
		
		return filePath;
	}
	

	/***
	 * 清除旧的索引目录，保留时间最晚的两个索引目录和正在建设的目录
	 * @param newPath 刚建的索引目录
	 * @param oldPath 开始打开的索引目录
	 * @author 
	 */
	public static void clearOldIndexFiles(File newPath,File oldPath){
		
		if(newPath == null)return;
		
		try{
			final int filLen = 2;//大于2才有可能要清理
			File[]  indexs = newPath.getParentFile().listFiles();
			if(indexs != null && indexs.length > filLen){
				for(File cfile : indexs){
					if(cfile.getAbsolutePath().equals(newPath.getAbsolutePath()) || (oldPath != null &&
							cfile.getAbsolutePath().equals(oldPath.getAbsolutePath()))){
						
					}else{
						logger.info("清除废弃索引" + cfile.getAbsolutePath());
						FileUtils.deleteDirectory(cfile);
					}
				}
			}

		}catch(Exception e){
			logger.error("清除旧索引时出错!", e);
		}
		
	}

	public static String getCurrentPath() {
		return currentPath;
	}
	
}
