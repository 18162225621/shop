/**
 * 
 */
package com.jwzt.datagener.model;

public class Program {
	
	
	/**站点id*/
	private int siteId;
	
	/**稿件id*/
	private int id;
	
	/**如果是复制的节目，稿件的真实id*/
	private int true_id;
	
	/**稿件名称*/
	private String title="";
	
	/**栏目名称*/
	private String nodename="";
	
	/**栏目id*/
	private int node_id;
	
	/**描述信息*/
	private String description="";
	
	/**发布时间*/
	private long pubtime=0;
	
	/**导演*/
	private String director="";
	
	/**主演*/
	private String actor="";
	
	/**图片地址*/
	private String picurl="";
	
	private String picurl2 = "";
	
	private String picurl3 = "";
	
	/**链接地址*/
	private String newsurl="";
	
	/**地区*/
	private String area="";
	
	/**语言*/
	private String language="";
	
	/**年份*/
	private String year="";
	
	/**标签或关键字*/
	private String tags="";
	
	
	/**（news：非影视类，movie：影视类电影，series：影视类电视剧  column:综艺类型）*/
	private String video_type;
	
	/**评分*/
	private String view_point = "0";
	
	/**有几个视频文件*/
	private int file_count = 1;
	
	/**实际的总集数*/
	private String total_several = "";
	
	/**对应视频xml文件的路径*/
	private String proXmlPath;

	/**在搜索结果中是否展现,在搜索结果页里面不展现的，在其他页里面可以展现*/
	private boolean in_search_result = false;
	
	/**稿件本来的排序*/
	private int news_order =0;
	
	/** PPV_ID可以判断资产是否收费 */
	private String ppv_id ="";
	
	private String content_channel="";
	
	private String  columnName="";
	private String  score="";

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTrue_id() {
		return true_id;
	}

	public void setTrue_id(int true_id) {
		this.true_id = true_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public int getNode_id() {
		return node_id;
	}

	public void setNode_id(int node_id) {
		this.node_id = node_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPubtime() {
		return pubtime;
	}

	public void setPubtime(long pubtime) {
		this.pubtime = pubtime;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String pics_url) {
		this.picurl = pics_url;
	}

	public String getPicurl2() {
		return picurl2;
	}

	public void setPicurl2(String picurl2) {
		this.picurl2 = picurl2;
	}

	public String getPicurl3() {
		return picurl3;
	}

	public void setPicurl3(String picurl3) {
		this.picurl3 = picurl3;
	}

	public String getNewsurl() {
		return newsurl;
	}

	public void setNewsurl(String newsurl) {
		this.newsurl = newsurl;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getVideo_type() {
		return video_type;
	}

	public void setVideo_type(String video_type) {
		this.video_type = video_type;
	}

	public String getView_point() {
		return view_point;
	}

	public void setView_point(String view_point) {
		this.view_point = view_point;
	}

	public int getFile_count() {
		return file_count;
	}

	public void setFile_count(int file_count) {
		this.file_count = file_count;
	}

	public String getTotal_several() {
		return total_several;
	}

	public void setTotal_several(String total_several) {
		this.total_several = total_several;
	}

	public String getProXmlPath() {
		return proXmlPath;
	}

	public void setProXmlPath(String proXmlPath) {
		this.proXmlPath = proXmlPath;
	}

	public boolean isIn_search_result() {
		return in_search_result;
	}

	public void setIn_search_result(boolean in_search_result) {
		this.in_search_result = in_search_result;
	}
	
	@Override
	public boolean equals(Object obj) {

		if(obj == this) return true;
		if(obj == null) return false;
		if (!(obj instanceof Program)) return false;
		
		return this.getId() == ((Program)obj).getId();
	}
	@Override
	public int hashCode() {
		return this.getId();
	}

	public int getNews_order() {
		return news_order;
	}

	public void setNews_order(int news_order) {
		this.news_order = news_order;
	}

	public String getPpv_id() {
		return ppv_id;
	}

	public void setPpv_id(String ppv_id) {
		this.ppv_id = ppv_id;
	}

	public String getContent_channel() {
		return content_channel;
	}

	public void setContent_channel(String content_channel) {
		this.content_channel = content_channel;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public static boolean isYouKu(String contentChannel){
		String IS_REGEX ="^(.*"+CPType.YouKu.getString()+")[0-9]{2}$";
		return contentChannel.matches(IS_REGEX);
	}
	
	public static boolean isSoHu(String contentChannel){
		String IS_REGEX ="^(.*"+CPType.SoHu.getString()+")[0-9]{2}$";
		return contentChannel.matches(IS_REGEX);
	}
	
	public static boolean isHDR(String contentChannel){
		String IS_REGEX ="^(.*"+CPType.SonyHDR.getString()+")[0-9]{2}$";
		return contentChannel.matches(IS_REGEX);
	}
	
}
	
