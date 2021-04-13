package com.jwzt.datagener.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jwzt.datagener.helper.Base64;
import com.jwzt.datagener.helper.SpecialCharacterHelper;
import com.jwzt.datagener.helper.StringHelper;
import com.jwzt.datagener.lucence.BaseSearch;
import com.jwzt.datagener.lucence.FullDataSearch;
import com.jwzt.datagener.mgr.BaiDuMgr;
import com.jwzt.datagener.mgr.DataManager;
import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.Node;
import com.jwzt.datagener.model.NodeNews;
import com.jwzt.datagener.model.NodeTree;
import com.jwzt.datagener.model.PageInfo;
import com.jwzt.datagener.model.Pg;
import com.jwzt.datagener.model.Program;
import com.jwzt.datagener.model.ProgramType;
import com.jwzt.datagener.service.NodeNewService;
import com.jwzt.datagener.service.OffLineService;
import com.jwzt.datagener.service.OnLineService;

public class AllAssetMgr {
	private static Logger logger = Logger.getLogger(AllAssetMgr.class);

	@Autowired
	NodeNewService nodeNewService;

	@Autowired
	OnLineService onLineService;

	@Autowired
	OffLineService offLineService;

	public void sendAllAsset() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

		String filePath = AppConextConfig.getSearchConfig().getSendinfo();
	
		// 拼接文件完整路径
		String fullPath = filePath + File.separator + "wasujx_"+ sdf.format(new Date())
				+ "_all.json";

		// 生成json格式文件
		try {

			// 保证创建一个新文件
			File file = new File(fullPath);
			if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
				file.getParentFile().mkdirs();
			}
			file.createNewFile();

			int pagenum = 1;
			PageInfo pageInfo = new PageInfo();
			pageInfo.setPageSize(15);

			FullDataSearch fullDataSearch = null;

			Pg pg = null;
			NodeNews nodeNew = null;
			int newNume = 0;
			int othernume = 0;
			int nums = 0;
			for (int i = 1; i <= pagenum; i++) {
				logger.info("开始处理第" + i + "页的资产");
				// 分页搜索到底量资产
				fullDataSearch = new FullDataSearch();
				fullDataSearch.setOrdertype(BaseSearch.ORDER_BY_NEWSORDER);
				fullDataSearch.setPageinfo(pageInfo);

				List<Program> programList = fullDataSearch.getProgramList();// 获得第一页的数据
				pagenum = pageInfo.getPageNum();

				for (Program program : programList) {
					newNume++;

					try {
						String video_type = program.getVideo_type();

						pg = new Pg();
						int total = 1;
						String category = "";
						Node rootNode = null;
						Node currentNode = null;
						total = StringHelper.stringParseInt(
								program.getTotal_several(), 1);

						if (program.getNode_id() > 0) {
							currentNode = NodeTree.getNodeById(String
									.valueOf(program.getNode_id()));
						}

						if (null != currentNode) {
							rootNode = NodeTree.getNodeById(String
									.valueOf(currentNode.getRootid()));
						}

						if (null != rootNode) {
							category = rootNode.getName();
						}

						nodeNew = new NodeNews();
						nodeNew.setNodeId(program.getNode_id());
						nodeNew.setNewsId(program.getId());
						pg.setTitle(program.getTitle());
						
						
						//nodeNew.setNewsName(program.getTitle());
						nodeNew.setStatus(1);
						if (program.isIn_search_result()) {
							nodeNew.setInresultpage(1);
						} else {
							nodeNew.setInresultpage(0);
						}
						

						pg.setId(String.valueOf(program.getId()));
						
						pg.setEpisode_count(total);// 总集数
						pg.setEpisode_updated(program.getFile_count());// 当前集
						pg.setCost(DataManager.ppvisFree(program.getPpv_id()));// 收费类型，免费
																				// 收费
						pg.setNode_id(program.getNode_id()); // 栏目id
						pg.setCategory(category);// 电影，电视剧,综艺，娱乐，体育，少儿，家庭影院等
						pg.setDirectors(SpecialCharacterHelper
								.replaceSpecStr(program.getDirector()));// 导演
						pg.setActors(SpecialCharacterHelper
								.replaceSpecStr(program.getActor()));// 演员
						pg.setRegions(program.getArea());// 地域
						pg.setIssue_date(program.getYear());// 发布年代
						pg.setLanguages(program.getLanguage());// 语言，(中文/英文等)
						pg.setDescription(program.getDescription());// 视频简介
						pg.setPoster(program.getPicurl());// 海报url
						pg.setThumb_url(program.getPicurl2());// 海报缩略图url
						pg.setScore(program.getScore());// 评分 float
						pg.setIs_online(1);// 上线
						pg.setFocus(program.getView_point());// 看点
						pg.setPublishers(program.getArea());// 发行地
						pg.setContent_channel(program.getContent_channel());
						pg.setPpvid(program.getPpv_id());
						pg.setOnlinetime(program.getPubtime());	
						pg.setVideo_type(video_type);
						pg.setLayoutCode(ProgramType.getLayoutCode(video_type,
								pg.getContent_channel()));// 详情页布局
						pg.setColumnName(program.getColumnName());// column_name
																	// 综艺壳名称

						// 先查询数据库中是否存在这个记录
						// logger.info("【底量】【nodeid:"+nodeNew.getNodeId()+";newId:"+nodeNew.getNewsId()+"】先查询数据库中是否存在这个记录");
						NodeNews querynn = nodeNewService.getNodeNews(nodeNew);
						
						JSONObject jsonObject = null;
						
						if (null == querynn) {
							
							othernume++;

								jsonObject = onLineService
										.movieAndNewsOnLine(pg);
							
							Date date = new Date(pg.getOnlinetime());//用Date构造方法，将long转Date
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String stringDate = format.format(date);//用SimpleDateFormat将Date转xxxx-xx-xx格式的字符
							
							nodeNew.setOnlinetime(stringDate);
							nodeNew.setOfflinetime(null);
							// logger.info("【底量】nodeid:"+nodeNew.getNodeId()+";newId:"+nodeNew.getNewsId()+";name:"+pg.getTitle());
							nodeNewService.insert(nodeNew);
							 logger.info("【底量】添加资产【"+program.getId()+"】和栏目【"+program.getNode_id()+"】上线关系");
						} else {

							continue ;
							// logger.info("【底量】【nodeid:"+nodeNew.getNodeId()+";newId:"+nodeNew.getNewsId()+"】在数据库中存在，上线状态是："
							// + querynn.getStatus()+"(1:上线，-1：下线),是否搜索状态是："+
							// querynn.getInresultpage()+"(1:能搜索到 0：不能搜索到)");
						}

						Writer write = new OutputStreamWriter(
								new FileOutputStream(file, true), "UTF-8");

						write.write(jsonObject.toString() + "\n");

						write.flush();
						write.close();

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						logger.error("【底量】底量处理资产，处理单个资产出现异常，" + e.toString());
					}
				}
				logger.info("总共" + pagenum + "页，第" + i + "页的资产已经处理完");
				pageInfo.setCurrentPage(i + 1);
			}
			logger.info("【底量】一共处理" + newNume + "个资产" + othernume);

		} catch (Exception e) {
			//flag = false;
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ss = "http://www.baidu.com";

		System.out.println(Base64.encode(ss.getBytes()));
	}

}
