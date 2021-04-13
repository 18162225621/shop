package com.jwzt.datagener.lucence;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldCollector;

import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.MyHelper;
import com.jwzt.datagener.model.PageInfo;
import com.jwzt.datagener.model.Program;


public abstract class BaseSearch {
	private static Logger logger = Logger.getLogger(BaseSearch.class);

	/**在搜索结果中*/
	public final static Query QUERY_INRESULT_PAGE = new TermQuery(new Term("inresultpage","1"));
	
	/**电视剧3*/
	public final static Query QUERY_TYPE_SERIES = new TermQuery(new Term("type","3"));
	
	/**电影1*/
	public final static Query QUERY_TYPE_MOVIE = new TermQuery(new Term("type","1"));

	/**非影视剧0*/
	public final static Query QUERY_TYPE_OTHER = new TermQuery(new Term("type","0"));
	
	/**默认排序*/
	public final static int OREDER_BY_DEFAULT = 0;
	
	/**按时间排序*/
	public final static int OREDER_BY_TIME = 1;
	
	/**按点击量排序*/
	public final static int ORDER_BY_CLICKNUM = 2;
	
	/**按栏目名称排序*/
	public final static int ORDER_BY_NODENAME = 4;
	
	/**按稿件顺序排序*/
	public final static int ORDER_BY_NEWSORDER = 5;
	
	
	protected int ordertype = 0;    //排序方式
	
	protected boolean desc = true; //true:降序排序，false：升序排序
	
	protected PageInfo pageinfo = null; //分页信息
	
	protected List<Program> programList; //节目列表
	
	protected final static int maxSize = 200000; //显示的最大搜索条数
	
	/***
	 * 通用搜索入口,获取总条数,还获取列表页
	 * @param this
	 * @return
	 */
	public  void beginSearch(){
		
		List<Program> programs = new ArrayList<Program>();
		IndexReader reader = null;
		IndexSearcher searcher = null;
		int totalSize = 0;
		try {
			/**解析得到关键字数组**/
			Query query = this.getQuery();
			if(query != null){
				reader = MyIndexReader.getReaderByPath();
				if(reader == null){
					return;
				}
				searcher = new IndexSearcher(reader);
				TopFieldCollector topCollector = TopFieldCollector.create(this.getSort(),maxSize,true,false,false,false);
				if(this.getFilter() == null){
					searcher.search(query,topCollector);
				}else{
					searcher.search(query,this.getFilter(),topCollector);
				}

				totalSize = topCollector.getTotalHits();
				if(pageinfo == null) pageinfo = new PageInfo();
				pageinfo.setTotal(totalSize);
				TopDocs tds = topCollector.topDocs((pageinfo.getCurrentPage() - 1) * pageinfo.getPageSize(), pageinfo.getPageSize());
				//TopDocs tds = topCollector.topDocs(start, end);
				//TopDocs tds = topCollector.topDocs(0,topCollector.getTotalHits());
				List<Integer> tempList = new ArrayList<Integer>();
				ScoreDoc[] sd = tds.scoreDocs;
				Document doc;
				Program pg;

				for (int i = 0; i<sd.length; i++) {

					doc = reader.document(sd[i].doc);
					
					if (tempList.contains(Integer.parseInt(doc.get("id")))) {   //资产去重操作
						continue;
					} else {
						tempList.add(Integer.parseInt(doc.get("id")));
					}
					pg = new Program();
					pg.setId(Integer.parseInt(doc.get("id")));
					pg.setTrue_id(Integer.parseInt(doc.get("trueid")));
					pg.setNode_id(Integer.parseInt(doc.get("nodeid")));
					pg.setNodename(doc.get("nodeName"));
					pg.setActor(doc.get("actor")==null ? "" : doc.get("actor").replace("'", "\\'"));
					pg.setDirector(doc.get("director")== null ? "" : doc.get("director").replace("'", "\\'"));
					pg.setDescription(doc.get("description")==null ? "" : doc.get("description").replace("'", "\\'"));
				
					String pic = doc.get("pic_url");
					if(pic != null && pic.trim().length() > 0){
						pg.setPicurl(pic);
						
					}
					String pic2 = doc.get("pic_url2");
					if(pic2 != null && pic2.trim().length() > 0){
						pg.setPicurl2(pic2);
					}
					
					String pic3 = doc.get("pic_url3");
					if(pic3 != null && pic3.trim().length() > 0){
						pg.setPicurl3(pic3);
					}
					pg.setPubtime(Long.parseLong(doc.get("pubtime")));
					pg.setTitle(doc.get("name")==null ? "" : doc.get("name").replace("'", "\\'"));
					pg.setNewsurl(doc.get("newsurl"));
					pg.setTags(doc.get("tags"));
					pg.setView_point(doc.get("view_point"));
					pg.setSiteId(AppConextConfig.getSearchConfig().getSiteId());
					pg.setProXmlPath(doc.get("proXmlPath"));
					pg.setTotal_several(doc.get("total_several"));
					pg.setFile_count(NumberUtils.toInt(doc.get("file_count")));
					pg.setVideo_type(doc.get("video_type"));
					pg.setArea(doc.get("area"));
					pg.setLanguage(doc.get("language"));

					String year = doc.get("year");
					if(year != null && year.length() >= 7){//年月
						year = year.substring(0,7);
					}
					pg.setYear(year);
					pg.setNews_order(NumberUtils.toInt(doc.get("news_order")));
					pg.setPpv_id(doc.get("ppvid"));
					pg.setColumnName(doc.get("columnName"));
					pg.setScore(doc.get("score"));
					
					String inresultpage = doc.get("inresultpage");
					if("1".equals(inresultpage)){
						pg.setIn_search_result(true);//能搜索到
					}else{
						pg.setIn_search_result(false);
					}
					
					programs.add(pg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setProgramList(programs);
	}
	
	/***
	 * 通用搜索入口.只获取总条数
	 * @param this
	 * @return
	 */
	public final int getTotalHits(){
		
		int countall = 0;
		IndexReader reader = null;
		IndexSearcher searcher = null;
		try {
			/**解析得到关键字数组**/
			Query query = this.getQuery();
			if(query != null){
				reader = MyIndexReader.getReaderByPath();
				searcher = new IndexSearcher(reader);
				TopFieldCollector topCollector = TopFieldCollector.create(this.getSort(),maxSize,true,false,false,false);
				if(this.getFilter() == null){
					searcher.search(query,topCollector);
				}else{
					searcher.search(query,this.getFilter(),topCollector);
				}
				countall = topCollector.getTotalHits();
			}
		} catch (Exception e) {
//			System.out.println("获取总记录数时出错..." + e);
		}
		return countall;
	}
	
	/**查询语句*/
	protected abstract Query getQuery();
	
	/***
	 * 排序方式
	 * @return
	 */
	protected  Sort getSort(){
		
		Sort sort = new Sort();
		
		if(this.getOrdertype() == OREDER_BY_DEFAULT || this.getOrdertype() == OREDER_BY_TIME){
			
			SortField sortField = new SortField("pubtime", SortField.Type.LONG,this.isDesc());
			sort.setSort(sortField);
		}else if(this.getOrdertype() == ORDER_BY_CLICKNUM){
		
			sort.setSort(new SortField[]{new SortField("click_num", SortField.Type.INT,this.isDesc()),new SortField("pubtime", SortField.Type.LONG,this.isDesc())});
		}else if(this.getOrdertype() == ORDER_BY_NODENAME){
		
			sort.setSort(new SortField("nodeName", SortField.Type.STRING,this.isDesc()));
		}else if(this.getOrdertype() == ORDER_BY_NEWSORDER){
		
			sort.setSort(new SortField("news_order", SortField.Type.INT,this.isDesc()));
		}
		return  sort;
	}
	
	/**过滤方法*/
	protected  Filter getFilter(){
		
		return null;
	}

	public List<Program> getProgramList() {
		if(programList == null){
			this.beginSearch();
		}
		return programList;
	}

	public void setProgramList(List<Program> programList) {
		this.programList = programList;
	}

	public boolean isDesc() {
		return desc;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	public int getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(int ordertype) {
		this.ordertype = ordertype;
	}

	public PageInfo getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(PageInfo pageinfo) {
		this.pageinfo = pageinfo;
	}
}
