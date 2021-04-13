package com.jwzt.datagener.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FileUtil {
	private static final Logger logger  = Logger.getLogger(FileUtil.class);
	
	/**
	 * 读取文件内容
	 * @param fileName
	 * @return
	 */
	public static String readFileByLines(String fileName) {
		// TODO Auto-generated method stub
		File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer(1024);
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                sb.append(tempString);
            }
        } catch (IOException e) {
            logger.error("解析文件【"+fileName+"】出错!错误信息："+ e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                	logger.error("关闭reader出现异常");
                }
            }
        }
        return sb.toString();
	}
	
	/**
	 * 本地生成文件
	 * @param fileDirectoryAndName
	 * @param fileContent
	 */
	 public static boolean createNewFile(String fileDirectoryAndName,String fileContent){
		 FileWriter resultFile = null;
		 PrintWriter myNewFile = null;
		 boolean isGood = false;
		  try{
			  String fileName = fileDirectoryAndName;
			  
			  File myFile = new File(fileName);//创建File对象，参数为String类型，表示目录名
			  //判断文件是否存在，如果不存在则调用createNewFile()方法创建新目录，否则跳至异常处理代码
			  isExists(myFile);
			  
			  resultFile = new FileWriter(myFile);//下面把数据写入创建的文件，首先新建文件名为参数创建FileWriter对象
			  
			  myNewFile = new PrintWriter(resultFile);//把该对象包装进PrinterWriter对象
			  
			  myNewFile.println(fileContent);//再通过PrinterWriter对象的println()方法把字符串数据写入新建文件
			  isGood = true;
		  }catch(Exception ex){
			  logger.error("无法创建新文件！"+ex);
			  ex.printStackTrace();
		  }finally{
			  try {
				  if(null != resultFile){
					resultFile.close(); //关闭文件写入流
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			  if(null != myNewFile){
				  myNewFile.close();
			  }
		}
		  return isGood;
	}
	 
	 /**
	  * 判断文件和目录是否存在，不存在新建
	  * @param file
	  */
	 public static void isExists(File file){
		 if(!file.getParentFile().exists()){
			 file.getParentFile().mkdirs();
	     }
	 }
	 
		/**
		 * 获得mpd文件本地存放地址
		 * @param remotePath  http://qszb.wasu.tv.8686c.com/temp/pr/sdr-20/stream.mpd
		 * @return F:\HDR\mpd/temp/pr/sdr-20/stream.mpd
		 */
	 public static String getLocalPath(String remotePath,String localPath){
		String path = localPath;
		if(remotePath.indexOf(".com/") > -1){
			return path + remotePath.substring((remotePath.indexOf(".com/")+4), remotePath.length());
		}else if(remotePath.indexOf(".cn/") > -1){
			return path + remotePath.substring((remotePath.indexOf(".cn/")+3), remotePath.length());
		}else{
			return path + DateHelper.getData();
		}
	}
	
	 /**
	  * ftp上传mpd文件地址
	  * @param sourcePath  源文件的目录
	  * @param localPath   源文件的绝对地址
	  * @param ftpPath     ftp目录
	  * @return
	  */
	public static String ftpUploadMpdPath(String sourcePath,String localPath,String ftpPath){
		//localMpdPath=F:/HDR/localMpd
		//     ftpPath=/
		//  F:\HDR\localMpd\8m-hdr-0616\stream.mpd
		//ftp:\8m-hdr-0616\stream.mpd
		//拿到本地存放的目录
		int endIndex = sourcePath.lastIndexOf("/");
		String file = localPath.substring((endIndex+1), localPath.length());
		return ftpPath+file;
	}	
	
	public static void appendContext(String file, String conent) {     
        BufferedWriter out = null;     
        try {     
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));     
            out.write(conent+"\n");     
        } catch (Exception e) {     
            e.printStackTrace();  
            logger.error("向文件【"+file+"】追加内容出现异常"+e.toString());
        } finally {     
            try {     
                if(out != null){  
                    out.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();  
                logger.error("appendContext 关闭out出现异常"+ e.toString());
            }     
        }     
    } 
	
	/**
	 * 从一个文件夹里过滤出日期大于给出的日期的文件夹
	 * @param incrementFile  给定的文件夹
	 * @param currentFolderName  当前的文件夹名称(格式：yyyyMMdd)
	 * @return
	 */
	public static List<File> filterFolder(File incrementFile,long currentFolderName){
		List<File> folderList  = new ArrayList<File>();
		File[] folders = incrementFile.listFiles();
		for(File f : folders){
			if(Long.parseLong(f.getName()) == currentFolderName){
				folderList.add(f);
			}
		}	
		return folderList;
	}
	
	/**
	 * 过滤出xml文件
	 * @param folderList
	 * @param currentFilePrefix
	 * @return
	 */
	public static List<File> filterFile(List<File> folderList,long currentFilePrefix) {
		
		List<File>  fileList = new ArrayList<File>();
			
		for(File ff : folderList){
			File[] subFile = ff.listFiles();
			for(File f : subFile){
				if(Long.parseLong(f.getName().substring(0,f.getName().indexOf(".xml"))) > currentFilePrefix){
					fileList.add(f);
				}
			}
		}
		return fileList;
	}
	
	public static void main(String[] args) {
//		ftpUploadMpdPath("F:/hls/ftp/localMpd","F:/hls/ftp/localMpd/angrybirds/SDR-A-12/stream.mpd","/");
		
//		createNewFile("F:/HDR/localMpd/deleterManiFest.txt","2222222222222222");
		
		appendContext("F:/HDR/localMpd/deleterManiFest.txt","555555555555555555");
	}
}
