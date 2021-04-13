package com.jwzt.datagener.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.jwzt.datagener.mgr.PlayUrlMgr;
import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.BaiDuType;
import com.jwzt.datagener.model.ChnCodeType;
import com.jwzt.datagener.model.CpPrvType;
import com.jwzt.datagener.model.Node;
import com.jwzt.datagener.model.NodeTree;
import com.jwzt.datagener.model.Pg;
import com.jwzt.datagener.model.Program;


public class JsonUtil {
	private static final Logger logger = Logger.getLogger(JsonUtil.class);
	
	public static List<JSONObject> assetList = new ArrayList<JSONObject>();
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static JSONObject creatBaiDuJson(Pg pg){
		JSONObject object = new JSONObject();
		object.put("contentId", pg.getId()); //资产ID
	    object.put("Program_name", pg.getTitle());//标题-节目名称
	   
	    object.put("actors", pg.getActors().replace("/", ","));//演员
	    object.put("directors", pg.getDirectors().replace("/", ","));//导演
	    object.put("contentYear", pg.getIssue_date());//年份
    	object.put("program_status", pg.getIs_online());//数据有效性0为无效(产品下线)1为有效(产品上线)
    	object.put("isPaid", pg.getCost());//是否免费0免费；1付费  默认为免费
    	object.put("program_type", pg.getVideo_type()); 
    	logger.info("资产上线处理是的上线时间"+pg.getOnlinetime());
    	
    	if(pg.getIs_online() == 0){ //下线
    		object.put("program_online_time", "");
        	object.put("program_offline_time", pg.getOfflinetime());
    	}else{ //上线
    		
    		Date date = new Date(pg.getOnlinetime());//用Date构造方法，将long转Date
    		String stringDate = format.format(date);//用SimpleDateFormat将Date转xxxx-xx-xx格式的字符
    		object.put("program_online_time", stringDate);
        	object.put("program_offline_time", "");
    	} 
    
    	if("999992".equals(pg.getPpvid())){
    		object.put("Product_code", "1018000035");
    	}else{
    		object.put("Product_code", "");
    	}
    	
    	object.put("Product_name", "华数精选");

    	assetList.add(object);
    	
    	return object;
	}
	
	
	public static JSONObject test(String newsID,String name,String pic1,String pic2,String token,String actor,
			String director,String region,String language){
		JSONObject object = new JSONObject();
    	object.put("id", newsID);
    	object.put("pid", newsID);
    	object.put("name", name);
    	object.put("poster_url", pic1);//海报url
    	object.put("thumb_url", pic2);//海报缩略图url
    	object.put("token", token);//播放url或者是播放id
    	object.put("duration", 7200);//时长(秒)
    	object.put("tag", "");//为空
    	object.put("director", director);//导演，用/分割
    	object.put("actor", actor);//演员， 用/分割
    	object.put("region", region);//地域，用/分割
    	object.put("language", language);//语言，(中文/英文等)
    	
    	object.put("partner", "sony_huashu");
    	object.put("resource_status", 1);//上下线标识，1：上线；-1下线
    	object.put("type", "电影");//类型（电影、电视剧、综艺、动漫、纪录片）
    	object.put("provider", "huashu");//huashu
    	object.put("serial_name", "");//系列名称
    	object.put("alias_name", "");//别名，用/分割
    	object.put("category", "");//为空
    	object.put("source_type", "");//为空
    	object.put("season", 1);//季、部
    	object.put("total_episodes", 1);//总集数
    	object.put("episode", 1);//当前集
    	object.put("release_date", "");//发布年代，精确到年,数据格式为yyyy-mm-dd或者yyyy,输出yyyy
    	object.put("update_time", 0);//更新时间， yyyyMMDD（示例：20171108）
    	object.put("cost", "");//资费信息（付费/免费/vip）
    	object.put("hot", 0);   //项目经理建议直接空，不填值
    	object.put("weight", 0);//项目经理建议直接空，不填值
    	object.put("definition", "");//清晰度，（超清、高清、流畅等）
    	object.put("introduction", "");//视频简介
    	object.put("extend", "");//资源方自定义数据，不大于5000
    	object.put("score", "");//评分
    	return object;
	}
	
	public static List<JSONObject> getAll(){
		String newsid1 = "3276278";
		String newsid2 = "17514929";
		String newsid3 = "3240967";
		String newsid4 = "3249425";
		String newsid5 = "857916";
		String newsid6 = "2412056";
		String newsid7 = "900171";
		String newsid8 = "925466";
		String newsid9 = "3309958";
		String newsid10 = "3307020";
		
		String new1pic = "https://qin-api-cs.wasu.tv/xml242/_CMS_NEWS_IMG_/www224/2017-10/30/1509343279283_452081.jpg";
		String new2pic = "http://cn-vmc-images.alicdn.com/vmac/10000000050E00005A3C8D22ADBAC30558028ED8";
		String new3pic = "http://qin-epg-cs.wasu.tv/xml242/_CMS_NEWS_IMG_/www224/201709/20/cms_80242945033339088199566.jpg";
		String new4pic = "http://qin-epg-cs.wasu.tv/xml242/_CMS_NEWS_IMG_/www224/2015-08/25/1440467429500_218941.jpg";
		String new5pic = "http://qin-epg-cs.wasu.tv/xml242/_CMS_NEWS_IMG_/www224/2014-01/10/1389357731409_911514.jpg";
		String new6pic = "http://qin-epg-cs.wasu.tv/xml242/_CMS_NEWS_IMG_/www224/201605/31/cms_53547461503345502985911.jpg";
		String new7pic = "http://qin-epg-cs.wasu.tv/xml242/_CMS_NEWS_IMG_/www224/2014-02/24/1393229431628_549117.jpg";
		String new8pic = "http://qin-epg-cs.wasu.tv/xml242/_CMS_NEWS_IMG_/www224/201403/24/cms_72009350647631957669861.jpg";
		String new9pic = "http://cn-vmc-images.alicdn.com/vmac/10000000050E00005983EC2DADBC09032C0045A7";
		String new10pic = "http://cn-vmc-images.alicdn.com/vmac/10000000050E000059DB1C78ADBC090AC903F35C";
		
		String token1 = PlayUrlMgr.getPlayUrl(77269, newsid1, "10010101", "movie", "Detail_Movie", "");
		String token2 = PlayUrlMgr.getPlayUrl(194845, newsid2, "10010301", "movie", "Detail_Movie", "");
		String token3 = PlayUrlMgr.getPlayUrl(77260, newsid3, "10010101", "movie", "Detail_Movie", "");
		String token4 = PlayUrlMgr.getPlayUrl(174403, newsid4, "10010101", "movie", "Detail_Movie", "");
		String token5 = PlayUrlMgr.getPlayUrl(89914, newsid5, "10010101", "movie", "Detail_Movie", "");
		String token6 = PlayUrlMgr.getPlayUrl(142814, newsid6, "10010101", "movie", "Detail_Movie", "");
		String token7 = PlayUrlMgr.getPlayUrl(129533, newsid7, "10010101", "movie", "Detail_Movie", "");
		String token8 = PlayUrlMgr.getPlayUrl(117646, newsid8, "10010101", "movie", "Detail_Movie", "");
		String token9 = PlayUrlMgr.getPlayUrl(132680, newsid9, "10010301", "movie", "Detail_Movie", "");
		String token10 = PlayUrlMgr.getPlayUrl(177784, newsid10, "10010301", "movie", "Detail_Movie", "");
		
		String actor1 = "成龙/詹妮弗·洛芙·海维特";
		String actor2 ="成龙/罗志祥/欧阳娜娜/夏侯云姗/卡兰·马尔韦";
		String actor3 ="成龙/皮尔斯·布鲁斯南/刘涛/西蒙·坤茨";
		String actor4 ="成龙/克里斯·塔克/尊龙/章子怡/罗塞莉·桑切斯";
		String actor5 ="成龙/张曼玉/刘嘉玲/关之琳/吕良伟/林威";
		String actor6 ="刘德华/刘烨";
		String actor7 ="刘德华/朱茵/张家辉/李子雄/高捷/张慧仪/张锦程";
		String actor8 ="刘德华/姚晨";
		String actor9 ="冯德伦/罗耀辉/哈智超/张志光/黄小庄";
		String actor10 ="刘德华/黄晓明/王祖蓝/胡然/欧阳娜娜/沈腾";
		
		String director1 = "凯文·多诺万Kevin Donovan";
		String director2 = "张立嘉";
		String director3 = "马丁·坎贝尔";
		String director4 = "布莱特·拉特纳";
		String director5 = "成龙";
		String director6 = "丁晟";
		String director7 = "王晶";
		String director8 = "袁锦麟";
		String director9 = "冯德伦";
		String director10 = "王晶";

		String region1 = "美国";
		String region2 = "中国大陆";
		String region3 = "中国大陆、美国";
		String region4 = "美国";
		String region5 = "中国香港";
		String region6 = "中国";
		String region7 = "中国香港";
		String region8 = "中国香港";
		String region9 = "中国";
		String region10 = "中国";
    	
    	String language1 = "英语";
    	String language2 = "国语";
    	String language3 = "英语";
    	String language4 = "英语";
    	String language5 = "汉语普通话";
    	String language6 = "国语";
    	String language7 = "国语";
    	String language8 = "国语/粤语";
    	String language9 = "国语";
    	String language10 = "普通话/粤语";

		
		List<JSONObject> alljson = new ArrayList<JSONObject>();
		
		alljson.add(test(newsid1, "神奇燕尾服", new1pic, new1pic, token1, actor1,director1, region1, language1));
		alljson.add(test(newsid2, "机器之血", new2pic, new2pic, token2, actor2,director2,region2,language2));
		alljson.add(test(newsid3, "英伦对决 最新版预告", new3pic, new3pic, token3, actor3,director3,region3,language3));
		alljson.add(test(newsid4, "尖峰时刻2", new4pic, new4pic, token4, actor4,director4,region4,language4));
		alljson.add(test(newsid5, "A计划续集", new5pic, new5pic, token5, actor5,director5,region5,language5));
		
		
		alljson.add(test(newsid6, "解救吾先生(sofa)", new6pic, new6pic, token6, actor6,director6,region6,language6));
		alljson.add(test(newsid7, "赌侠1999", new7pic, new7pic, token7, actor7,director7,region7,language7));
		alljson.add(test(newsid8, "风暴(杜比)", new8pic, new8pic, token8, actor8,director8,region8,language8));
		alljson.add(test(newsid9, "侠盗联盟", new9pic, new9pic, token9, actor9,director9,region9,language9));
		alljson.add(test(newsid10, "王牌逗王牌", new10pic, new10pic, token10, actor10,director10,region10,language10));
		
		return alljson;
	}
	
	
	public static void showMessage(){
		logger.info("推送给百度的频道有：");
		List<JSONObject> assetlist = assetList;
		if(null != assetlist && assetlist.size() > 0){
			Map<String,String> typeMap = new HashMap<String,String>();
			
			for (JSONObject jsonObject : assetlist) {
				typeMap.put(jsonObject.getString("type"), jsonObject.getString("id"));
			}
			
			
			for(String key : typeMap.keySet()){
				logger.info(key);
			}
			
			logger.info("推送给百度的频道展现结束");
		}
	}
	

}
