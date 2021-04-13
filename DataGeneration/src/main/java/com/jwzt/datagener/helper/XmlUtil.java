package com.jwzt.datagener.helper;

import java.util.Iterator;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.jwzt.datagener.mgr.DataManager;
import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.MyHelper;
import com.jwzt.datagener.model.Node;
import com.jwzt.datagener.model.NodeTree;
import com.jwzt.datagener.model.Pg;
import com.jwzt.datagener.model.ProgramType;



public class XmlUtil {
	
	final static Logger logger = Logger.getLogger(XmlUtil.class);
	
	public static Pg parseIncrementXml(Element programElement){
		
		Pg pg = new Pg();

		int m = 0;
		String pic = "";
		String thumb_url = "";

		pg.setId(programElement.elementTextTrim("id"));
		String nodeid = programElement.elementTextTrim("node_id");
		int nodeIdi = 0;
		try {
			nodeIdi =  Integer.parseInt(nodeid);
		} catch (Exception e) {
			// TODO: handle exception
		}
		pg.setNode_id(nodeIdi); //栏目id
		Node currentNode = NodeTree.getNodeById(nodeid);
		if(null != currentNode){
			Node rootNode = NodeTree.getNodeById(String.valueOf(currentNode.getRootid()));
			logger.info("rootid:"+ rootNode.getId());
			if(null != rootNode){
				pg.setCategory(rootNode.getName());//电影，电视剧,综艺，娱乐，体育，少儿，家庭影院等
			}
		}
		
		String state = programElement.elementTextTrim("state");
		if("1".equals(state)){
			pg.setIs_online(1);//上线
			
			for (Iterator<?> it2 = programElement.element("pics").elementIterator("url"); it2.hasNext();) {
				m++;
				if(m == 1){
					pic = ((Element)it2.next()).getTextTrim();
					pic =  MyHelper.getFilteredPic(pic);
				}
				
				if(m == 2){
					thumb_url = ((Element)it2.next()).getTextTrim();
					thumb_url = MyHelper.getFilteredPic(thumb_url);
				}
			}
			pg.setTitle(programElement.elementTextTrim("name"));
			pg.setPoster(pic);//海报
			pg.setThumb_url(thumb_url);//缩略图
			pg.setIssue_date(programElement.elementTextTrim("pubtime").substring(0,10));//上映时间 格式yyyy-mm-dd
			pg.setEpisode_count(NumberUtils.toInt(programElement.elementTextTrim("total_several"),1));//总集数
			pg.setEpisode_updated(NumberUtils.toInt(programElement.elementTextTrim("file_count"),1));//当前集数
			pg.setFocus(programElement.elementTextTrim("view_point"));//看点
			pg.setDescription(programElement.elementTextTrim("description"));//简介
			pg.setRegions(programElement.elementTextTrim("region"));//地区
			pg.setPublishers(programElement.elementTextTrim("region"));//发行地
			pg.setLanguages(programElement.elementTextTrim("language"));//语言
			pg.setDirectors(SpecialCharacterHelper.replaceSpecStr(programElement.elementTextTrim("director")));//导演  replaceStr
			pg.setActors(SpecialCharacterHelper.replaceSpecStr(programElement.elementTextTrim("actor")));//演员 replaceStr
			pg.setOnlinetime(DateHelper.getTime(programElement.elementTextTrim("pubtime")));
			pg.setCost(DataManager.ppvisFree(programElement.elementTextTrim("ppv_id")));//收费类型，免费 收费
			pg.setContent_channel(programElement.elementTextTrim("content_channel"));
			String video_type = programElement.elementTextTrim("video_type");
			pg.setPpvid(programElement.elementTextTrim("ppv_id"));
			pg.setVideo_type(video_type);
			pg.setLayoutCode(ProgramType.getLayoutCode(video_type,pg.getContent_channel()));//详情页布局
			pg.setScore(programElement.elementTextTrim("score"));//评分
			pg.setColumnName(programElement.elementTextTrim("column_name"));//column_name 综艺壳名称
		}else if("2".equals(state)){
			pg.setIs_online(0);//下线
		}
		return pg;
	}
}
