package com.jwzt.datagener.model;

import java.util.Map;

import com.jwzt.datagener.mgr.PolymerizedMgr;

/**
 * 节目类型
 * 0：非影视类，1：影视类电影，3：影视类电视剧 4、专题类资产 5.综艺）
 * newXml中的字段是video_type，索引中的字段是type
 * @version 
 */
public enum ProgramType {
	NEWS(0),
	
	MOVIE(1),
	
	SERIES(3),
	
	SUBJECT(4),
	
	COLUMN(5);
	
	private final int pgtype;
	
	private ProgramType(int pgtype) {
	     this.pgtype = pgtype;
	}

	/***
	 * 获取列表的展现类型
	 * @return
	 */
	public int getInt() {
		return this.pgtype;
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	public static String getCategory(int type){
		if(type == ProgramType.NEWS.getInt()){
			return "新闻";
		}else if(type == ProgramType.MOVIE.getInt()){
			return "电影";
		}else if(type == ProgramType.SERIES.getInt()){
			return "电视剧";
		}else if(type == ProgramType.COLUMN.getInt()){
			return "电视栏目";
		}else{
			return "专题类资产";
		}
	}
	
	/**
	 * 获得资产详情页布局
	 * @return
	 */
	public static String getLayoutCode(String vide_type,String content_channel){
		if(ProgramType.NEWS.toString().toLowerCase().equals(vide_type)){
			return "Detail_News";
		}else if(ProgramType.MOVIE.toString().toLowerCase().equals(vide_type)){
			return "Detail_Movie";
		}else if(ProgramType.SERIES.toString().toLowerCase().equals(vide_type)){
			return "Detail_Series";
		}else if(ProgramType.COLUMN.toString().toLowerCase().equals(vide_type)){
			if(PolymerizedMgr.isYouKu(content_channel)){
				return "Detail_Album";
			}else{
				return "Singer_Album_List";
			}
		}else{
			return "Detail_News";
		}
	}
	

	/**
	 * 获得tag的值
	 * @param nodeid
	 * @param type
	 * @return
	 */
	public static String getTag(String nodeid,String video_type){
		String tag = "";
		int rootId = NodeTree.getRootidByNodeid(nodeid);
		Map<String, BaiduChannelProperty> channelMap = AppConextConfig.getBaiduChannels();
		for(BaiduChannelProperty channel : channelMap.values()){
			if(rootId == channel.getRootNodeId()){
				tag = channel.getName();
			}
		}
		if("".equals(tag)){
			if(ProgramType.MOVIE.toString().toLowerCase().equals(video_type)){
				tag = "movie";
			}else if(ProgramType.SERIES.toString().toLowerCase().equals(video_type)){
				tag = "series";
			}else if(ProgramType.COLUMN.toString().toLowerCase().equals(video_type)){
				tag = "variety";
			}else{
				tag = "news"; 
			}
		}
		return tag;
	}
	
	/**
	 * 将xml中的转换为程序中需要的类型
	 * @param video_type 从cms中过来的newsxml和增量xml中都是带video_type
	 * news=====短视频
	 * movie====电影
	 * series===电视剧
	 * column===电视栏目
	 * @return 如果是news需要转换为info，如果不是原值返回
	 */
	public static String newsTransformationInfo(String video_type){
		if("news".equals(video_type)){
			return NEWS.toString();
		}
		return video_type;
	}
	
	public static void main(String[] args) {
		
		System.out.println(ProgramType.MOVIE.toString().toLowerCase());
	}
}
