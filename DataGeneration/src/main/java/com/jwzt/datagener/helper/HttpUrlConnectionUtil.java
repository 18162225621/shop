package com.jwzt.datagener.helper;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.io.IOUtils;



public class HttpUrlConnectionUtil {
	
	private static final Logger logger = Logger.getLogger(HttpUrlConnectionUtil.class);
	
	public static JSONObject apiHttpsCon(String url) {
		// TODO Auto-generated method stub
		StringBuffer strBuffer=new StringBuffer(1024);
		JSONObject object = null;
		URL reqURL;
		HttpsURLConnection httpsConn = null;
		InputStreamReader insr = null;
		boolean flag = true;
		if(url!=null){
			try {
				logger.info("【httpUrlConnection】apiHttpsCon url:" + url);
				reqURL = new URL(url);
				httpsConn = (HttpsURLConnection)reqURL.openConnection();
				httpsConn.setConnectTimeout(1*60*1000);//设置连接主机超时（单位：毫秒）
				httpsConn.setReadTimeout(5000);//设置从主机读取数据超时（单位：毫秒）
				httpsConn.setDoInput(true);
				httpsConn.setRequestProperty("Content-type", "application/json");

				//取得该连接的输入流，以读取响应内容 
				insr = new InputStreamReader(httpsConn.getInputStream(),"UTF-8");

				//读取服务器的响应内容并显示
				int respInt = insr.read();
				
				while(respInt != -1){
					strBuffer.append((char)respInt);
					respInt = insr.read();
				}
				
				if(strBuffer.length() > 0){
					logger.info("【httpUrlConnection】apiHttpsCon返回的数据："+strBuffer.toString());

					object = new JSONObject();
					object = (JSONObject) JSONObject.parse(strBuffer.toString());
				}
			}catch(Exception ex){
				logger.error("【httpUrlConnection】apiHttpsCon出错啦 ！连接到"+url+"出错!"+ex);
				flag = false;
			}finally{
				try {
					if(insr != null){
						insr.close();
					}
				} catch (IOException e) {
					flag = false;
					logger.error("【httpUrlConnection】apiHttpsCon出错啦 ！关闭insr出错!"+e);
				}
				
				if(httpsConn != null){
					httpsConn.disconnect();
				}
				
				if(!flag){
					object = new JSONObject();
					try {
						object.put("status", "fail");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						logger.error("【httpUrlConnection】apiHttpsCon出错啦 ！返回json出错"+ e);
					}
				}
			}
		}
		return object;
	}

	public static JSONObject httpsCon(String url) {
		// TODO Auto-generated method stub
		StringBuffer strBuffer=new StringBuffer(1024);
		JSONObject object = null;
		URL reqURL;
		HttpsURLConnection httpsConn = null;
		InputStreamReader insr = null;
		if(url!=null){
			try {
				logger.info("httpsCon url:"+ url);
				reqURL = new URL(url);
				httpsConn = (HttpsURLConnection)reqURL.openConnection();
				httpsConn.setConnectTimeout(1*60*1000);//设置连接主机超时（单位：毫秒）
				httpsConn.setReadTimeout(5000);//设置从主机读取数据超时（单位：毫秒）
				httpsConn.setDoInput(true);

				//取得该连接的输入流，以读取响应内容 
				insr = new InputStreamReader(httpsConn.getInputStream(),"UTF-8");

				//读取服务器的响应内容并显示
				int respInt = insr.read();
				
				while(respInt != -1){
					strBuffer.append((char)respInt);
					respInt = insr.read();
				}
				
				if(strBuffer.length() > 0){
					object = new JSONObject();
					object = (JSONObject) JSONObject.parse(strBuffer.toString());
				}
			}catch(Exception ex){
				logger.error("httpsCon出错啦 ！连接到"+url+"出错!"+ex);
			}finally{
				try {
					if(insr != null){
						insr.close();
					}
				} catch (IOException e) {
					logger.error("httpsCon出错啦 ！关闭insr出错!"+e);
				}
				
				if(httpsConn != null){
					httpsConn.disconnect();
				}
			}
		}
		return object;
	}

	public static boolean httpCon(String conUrl,String param,String id,String pid, String nodeid) {
		// TODO Auto-generated method stub
		URL url = null;
		InputStream in= null;
		BufferedReader breader = null;
		HttpURLConnection httpurlconnection = null;
		String str= "";
		JSONObject object = null;
		boolean baisuStatus = false;
		try{
			logger.info("id【"+id+"】pid【"+pid+"】nodeid【"+nodeid+"】httpCon请求的接口地址："+conUrl);
			url = new URL(conUrl);
		    //以post方式请求
		    httpurlconnection = (HttpURLConnection) url.openConnection();
		    httpurlconnection.setRequestProperty("Content-type", "application/json");
		    httpurlconnection.setDoOutput(true);
		    httpurlconnection.setRequestMethod("POST");
		    httpurlconnection.setConnectTimeout(10000);//连接超时 单位毫秒
		    httpurlconnection.setReadTimeout(2000);//读取超时 单位毫秒
		    httpurlconnection.setRequestProperty("Charset", "UTF-8");// 设置文件字符集
		    
		    if(!"".equals(param)){
		    	OutputStream outStream = httpurlconnection.getOutputStream();
				outStream.write(param.getBytes("UTF-8"));
				outStream.flush();
				outStream.close();
		    }
		    //获取页面内容
		    in= httpurlconnection.getInputStream();
		    breader =new BufferedReader(new InputStreamReader(in));
		    String line ;
		    while((line = breader.readLine()) != null){
		    	str=line;
		    }
		    logger.info("id【"+id+"】pid【"+pid+"】nodeid【"+nodeid+"】httpCon请求接口结束");
		    if(!"".equals(str)){
		    	logger.info("id【"+id+"】pid【"+pid+"】nodeid【"+nodeid+"】httpCon请求接口结束,接口有返回数据："+ str);
			    object = JSONObject.parseObject(str);
			    if(null != object){
			    	String status = object.getString("status");
			    	String msg = object.getString("msg");
			    	if("0".equals(status)){
			    		baisuStatus = true;
			    		logger.info("id【"+id+"】pid【"+pid+"】nodeid【"+nodeid+"】httpCon请求接口结束,接口有返回成功，status:"+ status);
			    	}else{
			    		logger.info("id【"+id+"】pid【"+pid+"】nodeid【"+nodeid+"】httpCon请求接口结束,接口有返回失败，status:"+ status+";msg:"+msg);
			    	}
			    }else{
			    	logger.info("id【"+id+"】pid【"+pid+"】nodeid【"+nodeid+"】httpCon请求接口结束,接口返回数据，转换数据出错");
			    }
		    }else{
		    	logger.info("id【"+id+"】pid【"+pid+"】nodeid【"+nodeid+"】httpCon请求接口结束,接口没有返回数据");
		    }
			
			
		    
		 }catch(Exception e){
			 logger.error("id【"+id+"】pid【"+pid+"】nodeid【"+nodeid+"】httpCon调用接口["+url+"]出现异常"+e);
		 }finally{
			 try {
				 if(breader != null){
					 breader.close();
			     }
			} catch (Exception e2) {
				logger.error("id【"+id+"】pid【"+pid+"】nodeid【"+nodeid+"】httpCon关闭BufferedReader出现异常"+ e2);
			}
			    try {
					if(in != null){
						in.close();
					}	
				} catch (Exception e2) {
					logger.error("id【"+id+"】pid【"+pid+"】nodeid【"+nodeid+"】httpCon关闭InputStream出现异常"+ e2);
				}
				  
				try {
					if(httpurlconnection != null){
						httpurlconnection.disconnect();
					}
				} catch (Exception e2) {
					logger.error("id【"+id+"】pid【"+pid+"】nodeid【"+nodeid+"】httpCon关闭httpurlconnection出现异常"+ e2);
				}
		 }
		return baisuStatus;
	}

	public static String httpsUrl(String url, String pageSize, int currentPage) {
		// TODO Auto-generated method stub
		if("？".indexOf(url) > -1){
			return url + "&pageSize="+pageSize+"&currentPage="+currentPage;
		}else{
			return url + "?pageSize="+pageSize+"&currentPage="+currentPage;
		}
	}
	
	
	/***
	 * @param url
	 * @return
	 */
	public static String downloadUrl(String url){

		String strRet = null;
		if(url!=null){
			BufferedReader reader=null;
			HttpURLConnection httpTarget=null;
			InputStreamReader input = null;
			try{
				URL target=new URL(url);
				httpTarget=(HttpURLConnection)target.openConnection();
				httpTarget.setConnectTimeout(10000);
				httpTarget.setReadTimeout(100000);
				httpTarget.setRequestMethod("GET");
				httpTarget.setDoInput(true);
				input = new InputStreamReader(httpTarget
						.getInputStream(),"utf-8");
				reader=new BufferedReader(input);
				StringBuffer strBuffer=new StringBuffer(1024);
				String line=reader.readLine();
				while(line!=null){
					strBuffer.append(line);
					line=reader.readLine();
				}
				strRet=strBuffer.toString();
			}catch(Exception ex){
				logger.error("请求地址" + url + "失败!");
			}finally{
				if(httpTarget != null){
					httpTarget.disconnect();
				}
				IOUtils.closeQuietly(input);
				IOUtils.closeQuietly(reader);
			}
		}
		return strRet;
	}   

    
    public static void main(String[] args) {
    	
    	
    	JSONObject object = new JSONObject();
    	object.put("id", 1249);
    	object.put("vod_id", "3922");
    	object.put("parent_id", "3615");
    	object.put("provider", "");
    	object.put("partner", "sony_huashu");
    	object.put("resource_status", -1);
    	object.put("name", "英才发掘团 20160504");
    	object.put("serial_name", "英才发掘团 2016");
    	object.put("alias_name", "");
    	object.put("type", "综艺");
    	object.put("category", "访谈/真人秀");
    	object.put("source_type", "");
    	object.put("tag", "");
    	object.put("duration", 3203);
    	object.put("season", 0);
    	object.put("total_episodes", 0);
    	object.put("episode", 20160504);
    	object.put("director", "");
    	object.put("actor", "");
    	object.put("region", "韩国/国外");
    	object.put("release_date", "2016");
    	object.put("update_time", "20170314");
    	object.put("cost", "");
    	object.put("hot", 0);
    	object.put("weight", 50);
    	object.put("language", "");
    	object.put("definition", "");
    	object.put("introduction", "《英才发掘团》是一档发掘人才的综艺娱乐节目。");
    	object.put("poster_url", "http://192.168.80.45/poster/930.jpg");
    	object.put("thumb_url", "");
    	object.put("token", "");
    	
//    	String value = object.toJSONString();
    	
    	
    	List<JSONObject> news = JsonUtil.getAll();
    	int num = 0;
    	for (JSONObject jsonObject : news) {
    		num ++ ;
    		String value = jsonObject.toJSONString();
    		
    		long systemTimeMillis = System.currentTimeMillis();
        	String code = MD5Coder.GetMD5Code("3Ep1K|tnwjxlgozh"+'#'+systemTimeMillis);
        	String url = "http://duertv.baidu.com/duertv/data/push";
        	
        	String param = "code="+code+"&t="+systemTimeMillis;
        	url = url + "?"+ param;
        	
        	System.err.println(num + "开始调用接口,value:"+value);
        	httpCon(url, value,jsonObject.getString("id"),jsonObject.getString("pid"),"");
        	System.err.println(num + "结束调用接口");
		}
  
    	
    	
    	
    }
}
