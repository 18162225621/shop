package com.jwzt.datagener.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MyHelper {

	private final static String PLAY_PREFIX = "/PlayData?assetId=";
	
	private final static String PROGRAM_PREFIX = "/Program?nodeId=";
	
	private static Logger logger = Logger.getLogger(MyHelper.class);

	private static SearchConfigure searchConfig = AppConextConfig.getSearchConfig();


	/***
	 * 获取某个栏目第几页的document
	 * @param nodeId 栏目id
	 * @param currentPage 第几页
	 * @return
	 */
	public static Document	getNewXmlDocument(String nodeId,String currentPage){

		return getDocument(getNewsXmlPath(nodeId,currentPage));
	}
	
	public static String getNewsXmlPath(String nodeId,String currentPage){
		
		StringBuffer xmlPath = new StringBuffer(searchConfig.getNewsXml()).append("/xml").append(searchConfig.getSiteId())
				.append("/newsXml/").append(nodeId).append("/").append(nodeId).append("_").append(currentPage).append(".xml");

		return xmlPath.toString();
	}
	
	/***
	 * 根据xml路径获取它对应的document对象
	 * @param xmlPath
	 * @return
	 */
	public static Document getDocument(String xmlPath){
		
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = null;
		File file = new File(xmlPath);
		if(!file.exists()){
			//logger.error("解析[" + xmlPath + "]文件不存在!");
			return null;
		}
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			logger.error("解析[" + xmlPath + "]得到Document出错!");
		}
			
		return doc;
		
	}
	/***
	 * 获取处理之后的图片地址
	 * 根据配置文件来确定图片地址是从epg还是xml里取
	 * @param pic 图片地址
	 * @return
	 */
	public static String getFilteredPic(String pic){
		
		if(pic != null && pic.trim().length() > 0){
			if(pic.indexOf("http://") > -1 ||pic.indexOf("https://") > -1){
				return pic;
			}else{
				pic = pic.replace("cms/cms_images","_CMS_NEWS_IMG_");
				pic = pic.replace("/xml"+ AppConextConfig.getSearchConfig().getSiteId(),"");
				//pic = pic.replace("/","");
				/*if(!AppConextConfig.getSearchConfig().getPicLocation().equals(SearchConfigure.DEFAULT_PIC_LOCATION_EPG)){
					pic = "/xml"+ AppConextConfig.getSearchConfig().getSiteId() + pic; 
				}	*/
				return AppConextConfig.getSearchConfig().getPicHttp() +pic;
			}
		}else{
			return pic;
		}
	}
	
	/***
	 * 转换节目列表获取路径
	 * @param pgPath
	 * @return
	 */
	public static String ConvertProgramAddr(String pgPath){
		
		int index = pgPath.lastIndexOf("/");
		String textEnd =  pgPath.substring(index+1,pgPath.lastIndexOf(".")).replace("_", "&currentPage=");
		
		return PROGRAM_PREFIX + textEnd;
	}
	
	
	
	/***
	 * 根据参数判断请求类型
	 * @param request
	 * @param param
	 * @return
	 */
	public static boolean UrlEndsWith(HttpServletRequest request,String param){
		
		String url = request.getRequestURI();
		if(url.endsWith("/" + param)){
			return true;
		}
		return false;
	}

	
	/***
	* 输出xml字符串
	* @param resp
	*/
	public static void outPutStringXml(String returnMsg,HttpServletResponse resp) {

		if(returnMsg == null) return;

		PrintWriter out =  null;
		try {
			resp.setContentType("text/xml; charset=UTF-8");
			out = resp.getWriter();
			out.write(returnMsg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(out);
		}
	}
	
	/***
	* 输出字符串
	* @param resp
	*/
	public static void outPutStringJSON(String returnMsg,HttpServletResponse resp) {
	
		if(returnMsg == null) return;
		
		PrintWriter out =  null;
		try {
			resp.setContentType("application/json; charset=UTF-8");
			out = resp.getWriter();
			out.write(returnMsg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(out);
		}
	}	
	/***
	 * 根据播放地址获取到节目id,转化错误时返回-1
	 * @param playDataUrl 示例：/XmlData/PlayData?assetId=956511
	 * @return 
	 */
	public static int getPIdByPlayDataUrl(String playDataUrl){
		
		final int defaultVal = -1;
		try{
			return NumberUtils.toInt(playDataUrl.substring(playDataUrl.lastIndexOf(PLAY_PREFIX) + PLAY_PREFIX.length()),defaultVal);
		}catch(Exception e){
			return defaultVal;
		}
		
	}


	/***
	 * 处理单个节目的图片和视频地址
	 * @param request
	 * @param doc
	 */
	@SuppressWarnings("unchecked")
	public static void filterPicAndPlayUrl(Document doc) {
		try {
			if(doc != null){
				//获取所在栏目id
				List<Element> listpicall = doc.selectNodes("/root/programs/program/pics");
				List<Element> listtype = doc.selectNodes("/root/programs/program/type");
				String nodePic2 = "";
				int i = 0;
				for(Element pic : listpicall){
					
					List<Element> listpic = pic.elements("url");
					for(Element el : listpic){
						String text = el.getTextTrim();
						el.setText(MyHelper.getFilteredPic(text));
						if(ProgramType.NEWS.getInt() == NumberUtils.toInt(listtype.get(i).getTextTrim(), -1)){//非影视剧
							if("1".equals(el.attributeValue("id")) && el.getText().trim().length() == 0){//如果节目图片1不存在则用所在栏目的第二张图片替换节目图片
								el.setText(nodePic2);
							}
						} 
					}
					i++;
				}
			}
		}catch(Exception e){
			logger.info("解析当前文件出现异常");
		}
	}

	
	public static Document getProgramDocument(String xmlPath){
		
		Document doc = MyHelper.getDocument(xmlPath);
		//MyHelper.filterPicAndPlayUrl(doc);
		return doc;
	}	
	/***
	 * 过滤掉非法字符
	 * @param keyword
	 */
	public static String clearIllegalChar(String keyword) {
		
		if(keyword == null) return null;
		String filerdKeyword = keyword.replaceAll("[*|?|\\-|\\+|.]", "");
		if(filerdKeyword.trim().length() == 0){
			return null;
		}
		
		return QueryParser.escape(filerdKeyword);
	}
	
	public static String replaceStr(String keyword){
		if(keyword == null) return null;
		String filerdKeyword = keyword.replaceAll("[ |*|,|#|，|、]", ";");
		if(filerdKeyword.trim().length() == 0){
			return null;
		}
		
		return QueryParser.escape(filerdKeyword);
	}	

	
	/**
	 * 创建文件，文件夹
	 * @param path
	 */
	public static void createNewFile(String path,String program){
		FileWriter resultFile = null;
		PrintWriter myNewFile =null;
		try { 
			File file = new File(path);  
			if (!file.getParentFile().exists()) {  
				if (!file.getParentFile().mkdirs()) {  
				}  
			} 
			if(file.exists()){
				return ;
			} 
			file.createNewFile();
			//下面把数据写入创建的文件，首先新建文件名为参数创建FileWriter对象
			resultFile = new FileWriter(file);
			//把该对象包装进PrinterWriter对象
			myNewFile = new PrintWriter(resultFile);
			//再通过PrinterWriter对象的println()方法把字符串数据写入新建文件
			myNewFile.write(program);
        
		} catch (IOException e) {  
            e.printStackTrace();  
        }finally{
        	try {
				if(myNewFile != null){
					myNewFile.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
        	
        	try {
        		if(resultFile != null){
        			resultFile.close();
        		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();    
			}   //关闭文件写入流
        }
	}
	
	public static void main(String[] args) {
		String pic = "http://1.jpg";
		pic = "/imc/2.jpg";
		System.out.println(getFilteredPic( pic));
	}
}
