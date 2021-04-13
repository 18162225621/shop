package com.jwzt.datagener.model;

public class BaiduChannelProperty {
	
	/**电影*/
	public final static String MOVIE = "movie";
	
	/**电视剧*/
	public final static String SERIES = "series";
	
	/**少儿、点点乐园*/
	public final static String CHILDREN = "children";
	
	/**纪录（即栏目）*/
	public final static String RECORD = "record";
	
	/**栏目（即纪录）*/
	public final static String COLUMN = "column";
	
	/**综艺*/
	public final static String VARIETY = "variety";
	
	/**新闻*/
	public final static String NEWS = "news";
	
	/**体育*/
	public final static String SPORTS = "sports";
	
	/**军事*/
	public final static String MILITARY = "military";
	
	/**时尚*/
	public final static String FASHION = "fashion";
	
	/**生活*/
	public final static String LIFE = "life";
	
	/**娱乐*/
	public final static String ENTERTAINMENT = "entertainment";
	
	/**音乐*/
	public final static String MUSIC = "music";
	
	/**频道的名称*/
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**该频道最近更新（或者推荐的）节目所在栏目id*/
	private int recentlyUpdateNodeId;


	
	/**改频道根栏目编号*/
	private int rootNodeId;
	


	public int getRecentlyUpdateNodeId() {
		return recentlyUpdateNodeId;
	}

	public void setRecentlyUpdateNodeId(int recentlyUpdateNodeId) {
		this.recentlyUpdateNodeId = recentlyUpdateNodeId;
	}

	public int getRootNodeId() {
		return rootNodeId;
	}

	public void setRootNodeId(int rootNodeId) {
		this.rootNodeId = rootNodeId;
	}
	

}
