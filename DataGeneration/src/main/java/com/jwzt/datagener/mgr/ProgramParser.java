package com.jwzt.datagener.mgr;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;
import com.jwzt.datagener.helper.DateHelper;
import com.jwzt.datagener.helper.JsonUtil;
import com.jwzt.datagener.helper.XmlUtil;
import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.MyHelper;
import com.jwzt.datagener.model.Node;
import com.jwzt.datagener.model.NodeTree;
import com.jwzt.datagener.model.Pg;
import com.jwzt.datagener.model.Program;
import com.jwzt.datagener.model.ProgramType;
import com.jwzt.datagener.model.SearchConfigure;




public class ProgramParser {
	
	private static Logger logger = Logger.getLogger(ProgramParser.class);
	
	private static Pattern pattern = Pattern.compile("(.*/)(\\d+)(\\.xml)");

	
	private static List<Integer>  tempList = new ArrayList<Integer>();
	
	public static int notRepeatNes = 0;
	
	
	public static List<Program> getProgram(File file) {
		//logger.info("开始解析"+file.getAbsolutePath()+"文件");
		return getProgram(file,1000);
	}
	/***
	 * 获取一个xml文件中的所有节目信息
	 * @param file
	 * @return
	 */
	public static List<Program> getProgram(File file,int maxSize) {
		if(file == null || !file.getPath().endsWith(".xml") || !file.exists()){
			//logger.info("文件没有找到!");
			return null;
		}
		List<Program> list = null;
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = null;
		try {
			doc = reader.read(file);
			Element root = doc.getRootElement();
			Element head = root.element("head");
			String node_name = head.elementTextTrim("node_name");
			String nodeid = head.elementTextTrim("node_id");
			Element programs = root.element("programs");
			int pageSize = NumberUtils.toInt(head.elementTextTrim("record_num"),40);
			int currentPage = NumberUtils.toInt(head.elementTextTrim("curr_page"));
			if(programs != null){
				List<?> elpgs = programs.elements("program"); 
				if(elpgs.size() > 0) list = new ArrayList<Program>(Math.min(maxSize,elpgs.size()));
				Program pg = null;
				//logger.info("获得对象的长度elpgs.size()="+elpgs.size());
				for(int i = 0; i < elpgs.size(); i++){
					Element program = (Element) elpgs.get(i);
					String video_type = program.elementTextTrim("video_type");

					int id = 0;
					String title = "";
					String pic1 = "";
					String pic2 = "";
					String pic3 = "";
					String actor = "";
					String des = "";
					String type = program.elementTextTrim("type");
					
					id = Integer.parseInt(program.elementTextTrim("id"));
					if(tempList.contains(id)){
					}else{
						tempList.add(Integer.parseInt(program.elementTextTrim("id")));
						notRepeatNes ++;
					}
					
					title = program.elementTextTrim("name");
					int m = 0;
					String pic = "";
					for (Iterator<?> it2 = program.element("pics").elementIterator("url"); it2.hasNext();) {
						m++;
						pic = ((Element)it2.next()).getTextTrim();
						pic =  MyHelper.getFilteredPic(pic);
						
						if(m == 1){
							pic1 = pic;
						}else if(m == 2){
							pic2 = pic;
						}else if(m == 3){
							pic3 = pic;
						}
					}
					
					actor = program.elementTextTrim("actor");
					des = program.elementTextTrim("description");
					pg = new Program();
					pg.setId(id);
					pg.setTitle(title);
					pg.setPicurl(pic1);
					pg.setPicurl2(pic2);
					pg.setPicurl3(pic3);
					pg.setActor(actor);
					pg.setDescription(des);
					pg.setNode_id(Integer.parseInt(nodeid));
					pg.setDirector(program.elementTextTrim("director"));
					pg.setNodename(node_name);
					pg.setFile_count(NumberUtils.toInt(program.elementTextTrim("file_count"),1));
					pg.setTotal_several(String.valueOf(NumberUtils.toInt(program.elementTextTrim("total_several"), 0)));
					pg.setPubtime(DateHelper.getTime(program.elementTextTrim("pubtime")));
					pg.setArea(program.elementTextTrim("region"));
					pg.setLanguage(program.elementTextTrim("language"));
					String news_url = StringUtils.trimToEmpty(program.elementTextTrim("newsurl"));
					pg.setYear(program.elementTextTrim("year"));
					pg.setTags(program.elementTextTrim("tags"));
					
					if(String.valueOf(ProgramType.SUBJECT.getInt()).equals(type)){//专题的访问地址
						pg.setNewsurl(program.elementTextTrim("play"));
					}else{
						pg.setNewsurl(news_url);
					}
					pg.setView_point(program.elementTextTrim("view_point"));
					pg.setVideo_type(video_type);
					if(news_url.indexOf("/proXml/") != -1 && (news_url.endsWith(".xml"))){ //推荐节目，引用的是其他视频节目,newsurl里面存放的是play里面的值
						pg.setProXmlPath(AppConextConfig.getSearchConfig().getProXml() + "/xml"+ AppConextConfig.getSearchConfig().getSiteId() + news_url);	
					}else{
						pg.setProXmlPath(AppConextConfig.getSearchConfig().getProXml()+ program.elementTextTrim("play"));
					}
					String true_xml =  program.elementTextTrim("play");///xml225/proXml/88/448388.xml
					if(true_xml != null){
						Matcher matcher =  pattern.matcher(true_xml);
						if(matcher.find()){
							pg.setTrue_id(Integer.parseInt(matcher.group(2)));
						}else{
							pg.setTrue_id(-1);
						}
					}
					pg.setNews_order((currentPage -1) * pageSize + i);
					pg.setPpv_id(program.elementTextTrim("ppv_id"));
					//logger.info(program.elementTextTrim("ppv_id")+"----------------------");
					pg.setColumnName(program.elementTextTrim("column_name"));
					pg.setScore(program.elementTextTrim("score"));
					
					list.add(pg);
					if(list.size() > maxSize) {
						logger.info("当前xml【"+file.getAbsolutePath()+"】中的资产的长度大于"+maxSize+"，超过的不会继续解析");
						break;
					}
				}
				//logger.info("解析newsXml【"+file.getAbsolutePath()+"】结束，获得的集合长度："+list.size());
			}else{
				logger.info("newXML["+file.getAbsolutePath()+"]中的program对象为空");
			}
			
		} catch (Exception e) {
			logger.info("解析文件"+file.getAbsolutePath()+"出错!"+e);
		}
		return list;
	}
	
	/***
	 * 把一个节目从xml转换为json相应的对象
	 * @param program
	 * @return
	 */
	public static List<Pg> ProgramParPg(List<Program> programs) {
		List<Pg> pglist = new ArrayList<Pg>(programs.size());
		for (Program program : programs) {
			Pg pg = new Pg();
			pg.setId(String.valueOf(program.getId()));//编号
			
			pg.setTitle(program.getTitle());//标题
			
			int rootid= NodeTree.getRootid(String.valueOf(program.getNode_id()));
			Node rootNode = NodeTree.getNodeById(String.valueOf(rootid));
			pg.setCategory(rootNode.getName());//电影，电视剧,综艺，娱乐，体育，少儿，家庭影院等
			
			pg.setIssue_date(DateHelper.MillSecondsToyMdHmsss(program.getPubtime(),"yyyy-MM-dd"));//上映时间 格式yyyy-mm-dd
			
			pg.setEpisode_count(NumberUtils.toInt(program.getTotal_several()));//总集数
			
			pg.setEpisode_updated(program.getFile_count());//当前集数
			
			pg.setFocus(program.getView_point());//看点
			
			pg.setDescription(program.getDescription());//简介
			
			//获得海报图
			
			pg.setPoster(program.getPicurl());//海报
			
			pg.setTags(MyHelper.replaceStr(NodeTree.getNodePath(program.getNode_id())));//编目结构   replaceStr
			 
			pg.setRegions(MyHelper.replaceStr(program.getArea()));//地区
			
			pg.setPublishers("");//发行地
			
			pg.setLanguages(program.getLanguage());//语言
			
			pg.setDirectors(MyHelper.replaceStr(program.getDirector()));//导演  replaceStr
			
			pg.setActors(MyHelper.replaceStr(program.getActor()));//演员 replaceStr
			pg.setCost(DataManager.ppvisFree(program.getPpv_id()));//收费类型，免费 收费
			
			pg.setIs_online(1);//上线，下线
			
			pg.setTag(ProgramType.getTag(String.valueOf(program.getNode_id()), program.getVideo_type()));//movie,//根据栏目编号获得根栏目编号
			
			pg.setNode_id(program.getNode_id());//栏目编号
			
			pg.setContent_channel(program.getContent_channel());
			
			pg.setLayoutCode(ProgramType.getLayoutCode(program.getVideo_type(),pg.getContent_channel()));//详情页布局
			pglist.add(pg);
		}
		
		return pglist;
	}
}
