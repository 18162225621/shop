package com.jwzt.datagener.lucence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import com.jwzt.datagener.mgr.ProgramParser;
import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.Program;



public class FileIndexCreater {
	private static Logger logger = Logger.getLogger(FileIndexCreater.class);
	
	public int totalNum;
	
	private IndexWriter writer = null;

	/***
	 * 创建新的indexPath;
	 * @param indexDir
	 * @return
	 */
	public boolean  createIndexWriter(File indexDir){
		try {
			if(!indexDir.exists())
				indexDir.mkdir();
			Analyzer luceneAnalyzer = AppConextConfig.getAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_44, luceneAnalyzer);
			config.setOpenMode(OpenMode.CREATE);
			config.setMaxBufferedDocs(1000);
			Directory  directory = FSDirectory.open(indexDir);
			writer = new IndexWriter(directory,config);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("创建 indexWrite 出现异常",e);
			return false;
		} finally {
			
		}
		return true;
	}
	public void closeIndexWriter(){
		try {
			if(writer != null)
				writer.close();
		} catch (Exception e1) {
			logger.info("关闭 indexWrite 出现异常",e1);
		}
	}

	/***
	 * 
	 * @param xmlurl 当前栏目下所有的xml文件列表
	 * @param nodeid 当前栏目id
	 * 
	 */
	public  List<Program> createIndex(List<String> xmlurl, String nodeid) {
		List<Program> list = new ArrayList<Program>();
		for (int i = 0; i < xmlurl.size(); i++) {
			List<Program> lt = getPrograms(xmlurl.get(i));
			if(lt != null && lt.size()>0){
				list.addAll(lt);	
			}
			
		}
		return list;
	}

	
	/**
	 * 创建索引
	 * @param insearchResult 在其他结果页运行搜到，判断是否需要在搜索结果页里面搜到，true需要，false不需要
	 * @param programlist
	 */
	public void addTem(List<Program> programlist){
		try {
			if(programlist != null){

				for (Program pg : programlist) {
					pg.setIn_search_result(true);
					addItem(pg);
				}

			}else{
				logger.info("得到的资产集合总数为空");
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.info("开始创建索引出现异常 ");
		}
	}
	
	private  List<Program> getPrograms(String path) {
		List<Program> list = new ArrayList<Program>();
		try {
			list = ProgramParser.getProgram(new File(AppConextConfig.getSearchConfig().getNewsXml() + path));
		} catch (Exception e) {
			//e.printStackTrace();
			logger.info("解析newXml path："+path+"出现异常");
		}
		return list;
	}

	/***
	 * 一条一条添加稿件信息
	 * @param writer
	 * @param pg
	 */
	private int addItem(Program pg) {
		int delete = 0;
		try {
			String inresultpage =pg.isIn_search_result()? (pg.getTrue_id() > 0 ? "1" : "0"):"0";
			if("1".equals(inresultpage)){
				Document doc = new Document();
				String des = pg.getDescription();
				String actor = pg.getActor();
				String name = pg.getTitle();
				String director = pg.getDirector();
				doc.add(new StringField("id", String.valueOf(pg.getId()), Field.Store.YES));
				doc.add(new StringField("trueid", String.valueOf(pg.getTrue_id()), Field.Store.YES));
				doc.add(new StringField("nodeid", String.valueOf(pg.getNode_id()), Field.Store.YES));
				doc.add(new StringField("description", des, Field.Store.YES));
				doc.add(getIndexedTokenizedField("director",director));
				doc.add(getIndexedTokenizedField("actor",actor));
				doc.add(getIndexedTokenizedField("name",name));
				doc.add(new StringField("inresultpage",inresultpage, Field.Store.YES));
				doc.add(new LongField("pubtime", pg.getPubtime(), Field.Store.YES));
				doc.add(getNotIndexedNotTokenizedField("pic_url", pg.getPicurl()));
				doc.add(getIndexedTokenizedField("view_point", pg.getView_point()));
				doc.add(new StringField("nodeName", pg.getNodename(), Field.Store.YES));
				doc.add(new StringField("area", pg.getArea(), Field.Store.YES));
				doc.add(new StringField("language", pg.getLanguage(), Field.Store.YES));
				doc.add(new StringField("ppvid", pg.getPpv_id(), Field.Store.YES));
				doc.add(new StringField("year", pg.getYear(), Field.Store.YES));

				doc.add(getIndexedTokenizedField("tags", pg.getTags()));
				doc.add(new StringField("total_several", String.valueOf(pg.getTotal_several()), Field.Store.YES));
				doc.add(new StringField("file_count", String.valueOf(pg.getFile_count()), Field.Store.YES));
				doc.add(new StringField("video_type", String.valueOf(pg.getVideo_type()), Field.Store.YES));
				doc.add(new StringField("proXmlPath", pg.getProXmlPath(), Field.Store.YES));
				doc.add(new StringField("score", String.valueOf(pg.getScore()), Field.Store.YES));
				doc.add(new StringField("columnName", pg.getColumnName(), Field.Store.YES));

				
				writer.addDocument(doc);
				totalNum++;
			}else{
				delete = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("资产【"+pg.getTitle()+"==>"+pg.getId()+"==>nodeid:"+pg.getNode_id()+"】创建索引出现异常"+e);
		}
		return delete;
	}
	
	/**
	 * 需要索引需要分词，需要恢复
	 * @param name
	 * @param value
	 * @return
	 */
	private Field getIndexedTokenizedField(String name,String value){
		
		FieldType type = new FieldType();
		type.setIndexed(true);
		type.setStored(true);
		type.setTokenized(true);
		return new Field(name, value, type);
	}

	/***
	 * 不需要索引，不需要分词，需要恢复
	 * @param name
	 * @param value
	 * @return
	 */
	private Field getNotIndexedNotTokenizedField(String name,String value){
		
		FieldType type = new FieldType();
		type.setIndexed(false);
		type.setStored(true);
		type.setTokenized(false);
		return new Field(name, value, type);
	}
	
	
}
