package com.jwzt.datagener.mgr;

import org.apache.log4j.Logger;

import wasu.WasuDecryptUtil;
import wasu.WasuEncryptUtil;

import com.alibaba.fastjson.JSONObject;
import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.Node;
import com.jwzt.datagener.model.NodeTree;
import com.jwzt.datagener.model.ProgramType;


public class PlayUrlMgr{
	private static Logger logger = Logger.getLogger(PlayUrlMgr.class);

	public static String getPlayUrl(int nodeId, String newsId, String content_channel,
			String video_type, String layoutcode, String param) {
		// TODO Auto-generated method stub
		int showType = ProgramType.NEWS.getInt();;
		if(ProgramType.MOVIE.toString().toLowerCase().equals(video_type)){
			showType = ProgramType.MOVIE.getInt();
		}else if(ProgramType.SERIES.toString().toLowerCase().equals(video_type)){
			showType = ProgramType.SERIES.getInt();
		}else if(ProgramType.COLUMN.toString().toLowerCase().equals(video_type)){
			showType = ProgramType.COLUMN.getInt();
		}else if(ProgramType.NEWS.toString().toLowerCase().equals(video_type)){
			showType = ProgramType.NEWS.getInt();
		}
		
		
		JSONObject object = new JSONObject();
		object.put("newId", newsId);
		object.put("nodeId", nodeId);
		object.put("content_channel", content_channel);
		object.put("showType", showType);
		object.put("layoutCode", layoutcode);
		object.put("param", param);
		
		int module = AppConextConfig.getSearchConfig().getModule();
		logger.info("module:"+module);
		
		return WasuEncryptUtil.encrypt(module+"", object.toString());
	}
	
	public static String decodePlayUrl(String str){
		return WasuDecryptUtil.decrypt(2+"", str);
	}

	public static void main(String[] args) {
		String playUrl = "7A04914CF52312F2D1606604236F6639E98A02684A19987BBBF52F4D530CD1BBD8D11EE863ABAF4F710D2AFB0CD4662295CDE5B22CBA34E1BA82645FAC974083083B9E6BFF84F3108CF1E2C9E924EC9DBE6CF6C4C895852E642EBE9BD6C3E6CF07F67F4EAA0A7BE9DD3CCE2DE3D07C09F6A0205C653CF61C40AC38859144217F";
		System.out.println(decodePlayUrl(playUrl));
	}
	
	
	
	
	
	public static String getpicsUrl1(int nodeId, String newsId,	String video_type) {
		
			String hrefs = "";
		Node n = NodeTree.getNodeById(String.valueOf(nodeId));
		hrefs =AppConextConfig.getSearchConfig().getPicHttp()+n.getPic1().replace("/xml"+AppConextConfig.getSearchConfig().getSiteId(), "");
		return  hrefs;
	}
	
	public static String getPlaysUrl(int nodeId, String newsId,	String video_type) {
		
		StringBuffer hrefs = new StringBuffer(AppConextConfig.getSearchConfig().getPicHttp());
		if(ProgramType.MOVIE.toString().toLowerCase().equals(video_type)){
			
			hrefs.append("/XmlData_ZW4/XmlData.do?type=DetailData&layoutCode=Detail_Movie").append("&assetId=").append(newsId).append("&nodeId=").append(nodeId);
		}else if(ProgramType.SERIES.toString().toLowerCase().equals(video_type)){
			hrefs.append("/XmlData_ZW4/XmlData.do?type=DetailData&layoutCode=Detail_Series").append("&assetId=").append(newsId).append("&nodeId=").append(nodeId);
		}else if(ProgramType.COLUMN.toString().toLowerCase().equals(video_type)){
			
			hrefs.append(NodeTree.getNodeById(String.valueOf(nodeId)).getLink_url());
			//hrefs.append("/XmlData_ZW4/XmlData.do?type=Player&layoutCode=Detail_News").append("&assetId=").append(newsId).append("&nodeId=").append(nodeId);
		}else if(ProgramType.NEWS.toString().toLowerCase().equals(video_type)){
			
			
			hrefs.append(NodeTree.getNodeById(String.valueOf(nodeId)).getLink_url());		
			
			//hrefs.append("?type=Player&layoutCode=Detail_News").append("&assetId=").append(newsId).append("&nodeId=").append(nodeId);
		}
		
		return  hrefs.toString();
	}
	
	
}
