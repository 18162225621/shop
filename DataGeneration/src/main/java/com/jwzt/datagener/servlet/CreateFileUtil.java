package com.jwzt.datagener.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.alibaba.fastjson.JSONObject;

public class CreateFileUtil {

    /**
     * 生成.json格式文件
     */
    public static boolean createJsonFile() {
    	
    String filePath="c:\\fileStorage\\download1\\json";
    String fileName = "agency";
        // 标记文件生成是否成功
        boolean flag = true;

        // 拼接文件完整路径
        String fullPath = filePath + File.separator + fileName + ".json";
        
        
      
            
            
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
                  
                  if(linenumber > 10 ){
                	  fullPath = filePath + File.separator + fileName + "_1.json";
                  }else{
                	  
                  }
        	
        	
            // 保证创建一个新文件
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
            	System.out.println(file.exists());
                //file.delete();
            }else{
            	file.createNewFile();
            }
            

	            Writer write = new OutputStreamWriter(new FileOutputStream(file,true), "UTF-8");
           
           
           String jsonString = null;
        	for (int i = 0; i < 3; i++) {
        		 JSONObject s1 = new JSONObject();
        		JSONObject s = new JSONObject();
        		s.put("1", "a");
            	s.put("2", "aa");
            	s.put("3", "aaa");
            	s.put("4", "aaa");
            	s1.put("s"+i, s);
            	
            	jsonString = s1.toString()+"\n";
            	 write.write(jsonString);
    		}
        	

     
        
            write.flush();
            write.close();
           
          
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        // 返回是否成功的标记
        return flag;
    }
    
    public static void main(String[] args) {
    	
    	/*try{
    		File file =new File("c:\\fileStorage\\download1\\json\\agency.json");
    		if(file.exists()){
    		    FileReader fr = new FileReader(file);
    		    LineNumberReader lnr = new LineNumberReader(fr);
    		    int linenumber = 0;
    	            while (lnr.readLine() != null){
    	        	linenumber++;
    	            }
    	            System.out.println("Total number of lines : " + linenumber);
    	            lnr.close();
    		}else{
    			 System.out.println("File does not exists!");
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	}*/

        CreateFileUtil.createJsonFile();
	}
       
}