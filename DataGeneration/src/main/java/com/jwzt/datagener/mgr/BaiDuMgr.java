package com.jwzt.datagener.mgr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jwzt.datagener.helper.HttpUrlConnectionUtil;
import com.jwzt.datagener.helper.MD5Coder;
import com.jwzt.datagener.model.AppConextConfig;

public class BaiDuMgr {
	private static final Logger logger = Logger.getLogger(BaiDuMgr.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
	
	public static boolean sendBaiDuIAsset(JSONObject object,String nodeid){
		/*String url = AppConextConfig.getSearchConfig().getBaiDuUrl();
		String key = AppConextConfig.getSearchConfig().getKey();
		String value = object.toString();
		long systemTimeMillis = System.currentTimeMillis();
    	String code = MD5Coder.GetMD5Code(key+'#'+systemTimeMillis);

    	String param = "code="+code+"&t="+systemTimeMillis;
    	url = url + "?"+ param;
    	
    	logger.info("id【"+object.getString("id")+"】剧头id【"+object.getString("pid")+"】栏目id【"+nodeid+"】剧头开始调用接口,value:"+value);
    	boolean baiduStatu = HttpUrlConnectionUtil.httpCon(url, value,object.getString("id"),object.getString("pid"),nodeid);
		logger.info("id【"+object.getString("id")+"】剧头id【"+object.getString("pid")+"】栏目id【"+nodeid+"】剧头结束调用接口");
    	return baiduStatu;*/
		

		
		//System.out.println());
		
		String filePath=AppConextConfig.getSearchConfig().getSendinfo();
	    String fileName = "all";
	    
			fileName = AppConextConfig.getSearchConfig().getSend_increment();
	        // 标记文件生成是否成功
	        boolean flag = true;

	        // 拼接文件完整路径
	        String fullPath = filePath + File.separator +sdf.format(new Date())+ fileName + ".json"; 
	            
	        // 生成json格式文件
	        try {
	        	
	        	
	        	
	        	  FileReader fr = new FileReader(fullPath);
	      	    LineNumberReader lnr = new LineNumberReader(fr);
	      	    int linenumber = 0;
	                  while (lnr.readLine() != null){
	              	linenumber++;
	                  }
	                  System.out.println("Total number of lines : " + linenumber);
	                  lnr.close();
	                  
	                  if(linenumber >= 80000 ){
	                	  fullPath = filePath + File.separator + fileName + "_"+(linenumber/80000)+".json";
	                  }
	        	
	        	
	            // 保证创建一个新文件
	            File file = new File(fullPath);
	            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
	                file.getParentFile().mkdirs();
	            }
	            	file.createNewFile();	            

		            Writer write = new OutputStreamWriter(new FileOutputStream(file,true), "UTF-8");

	            	 write.write(object.toString()+"\n");
	     
	        
	            write.flush();
	            write.close();
	           
	          
	        } catch (Exception e) {
	            flag = false;
	            e.printStackTrace();
	        }
		
		
		
		return flag;
		
    	
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
		
		System.out.println(sdf.format(new Date()));
	}
	

}
