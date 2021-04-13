package com.jwzt.datagener.model;

public class Pg {

	private String id; //资产编号
	
	private String pid;//壳id
	
	private String title  =""; //标题
	
	private String english_name =""; //英文名
	 
	private String alias_name=""; //别名
	
	private String category ="新闻";//电影，电视剧
	
	private String issue_date  =""; //上映时间 格式yyyy-mm-dd
	
	private int play_length =0; //时长
	
	private int episode_count; //总集数
	
	private int episode_updated ;//当前集数
	 
	private String focus  =""; //看点
	
	private String description  =""; //简介
	
	private String poster  =""; //海报
	
	private String thumb_url  ="";//缩略图
	
	private String qualities  =""; 	//标清;高清;超清;蓝光;4K
	
	private String tags  =""; //编目结构
	
	private String regions  =""; //地区
	
	private String publishers =""; //发行地
	
	private String languages =""; //语言
	
	private String directors =""; //导演
	
	private String actors =""; //演员
	 
	private String cost =""; //收费类型，免费  收费
	
	private String valid_time =""; //点播节目有效日期
	
	private int is_online; //上线，下线
	
	private String layoutCode; //详情页布局
	
	private String tag; //movie,
	
	private int node_id; //栏目编号
	
	private String content_channel;//渠道标识
	
	private String score;//评分
	
	private String video_type;//资产类型
	
	private String playUrl;//播放地址
	
	private  String columnName;//综艺壳名称
	
	
	private long onlinetime; //上线时间
	private String ppvid; //产品包id
	private String offlinetime;


	public String getOfflinetime() {
		return offlinetime;
	}

	public void setOfflinetime(String offlinetime) {
		this.offlinetime = offlinetime;
	}

	public long getOnlinetime() {
		return onlinetime;
	}

	public void setOnlinetime(long onlinetime) {
		this.onlinetime = onlinetime;
	}

	public String getPpvid() {
		return ppvid;
	}

	public void setPpvid(String ppvid) {
		this.ppvid = ppvid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEnglish_name() {
		return english_name;
	}

	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}

	public String getAlias_name() {
		return alias_name;
	}

	public void setAlias_name(String alias_name) {
		this.alias_name = alias_name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}

	public int getPlay_length() {
		return play_length;
	}

	public void setPlay_length(int play_length) {
		this.play_length = play_length;
	}

	public int getEpisode_count() {
		return episode_count;
	}

	public void setEpisode_count(int episode_count) {
		this.episode_count = episode_count;
	}

	public int getEpisode_updated() {
		return episode_updated;
	}

	public void setEpisode_updated(int episode_updated) {
		this.episode_updated = episode_updated;
	}

	public String getFocus() {
		return focus;
	}

	public void setFocus(String focus) {
		this.focus = focus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getQualities() {
		return qualities;
	}

	public void setQualities(String qualities) {
		this.qualities = qualities;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getRegions() {
		return regions;
	}

	public void setRegions(String regions) {
		this.regions = regions;
	}

	public String getPublishers() {
		return publishers;
	}

	public void setPublishers(String publishers) {
		this.publishers = publishers;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public String getDirectors() {
		return directors;
	}

	public void setDirectors(String directors) {
		this.directors = directors;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getValid_time() {
		return valid_time;
	}

	public void setValid_time(String valid_time) {
		this.valid_time = valid_time;
	}

	public int getIs_online() {
		return is_online;
	}

	public void setIs_online(int is_online) {
		this.is_online = is_online;
	}

	public String getLayoutCode() {
		return layoutCode;
	}

	public void setLayoutCode(String layoutCode) {
		this.layoutCode = layoutCode;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getNode_id() {
		return node_id;
	}

	public void setNode_id(int node_id) {
		this.node_id = node_id;
	}

	public String getContent_channel() {
		return content_channel;
	}

	public void setContent_channel(String content_channel) {
		this.content_channel = content_channel;
	}

	public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getVideo_type() {
		return video_type;
	}

	public void setVideo_type(String video_type) {
		this.video_type = video_type;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	
}
