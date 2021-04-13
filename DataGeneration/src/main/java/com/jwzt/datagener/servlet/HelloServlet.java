package com.jwzt.datagener.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jwzt.datagener.lucence.NewsSearch;
import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.NodeNews;
import com.jwzt.datagener.model.Pg;
import com.jwzt.datagener.model.Program;
import com.jwzt.datagener.model.ProgramType;
import com.jwzt.datagener.model.SearchConfigure;
import com.jwzt.datagener.service.NodeNewService;
import com.jwzt.datagener.service.OffLineService;
import com.jwzt.datagener.service.OnLineService;
import com.jwzt.datagener.service.impl.AllAssetMgr;



@Controller  
@Scope("prototype")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(HelloServlet.class);
	
	@Autowired
	NodeNewService nodeNewService;
	
	@Autowired
	OnLineService onLineService;
	
	@Autowired
	OffLineService offLineService;
	
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {
		//获得所有需要屏蔽的栏目
				SearchConfigure search = AppConextConfig.getSearchConfig();
				Set nodelist = search.getNotSearchNodeList();
				NodeNews nodeNews = new NodeNews();
				Pg pg = null;
				Iterator<Integer> iterator = nodelist.iterator();
				
				
				while(iterator.hasNext()){
					int str = iterator.next();
					nodeNews.setNodeId(str);
					List<NodeNews> nns = nodeNewService.getNodeNewsByNodeId(nodeNews);
					
					   for (NodeNews nodeNews2 : nns) {
						   //logger.info("找到资产nodeid:"+nodeNews2.getNodeId()+";newsid:"+nodeNews2.getNewsId());
						   //根据资产id，搜索资产，获得资产类型
						   NewsSearch newSearch = new NewsSearch();
						   newSearch.setId(nodeNews2.getNewsId());
						   Program program =newSearch.getProgramList().get(0);
						   boolean baiduAssetStatus = false;			
						   pg = new Pg();
						   String currentVideoType = ProgramType.MOVIE.toString().toLowerCase();
						   if(null != program){
							   currentVideoType = program.getVideo_type();
							   pg.setColumnName(program.getColumnName());
						   }
									
						   if(ProgramType.COLUMN.toString().toLowerCase().equals(currentVideoType)){
							  // baiduAssetStatus = offLineService.offLineColumn(pg);
						   }else{
							   //电影，短视频下线
							 //  baiduAssetStatus = offLineService.offLineMovieAndServiceAndNews(pg);
						   }
						   
						   if(baiduAssetStatus){
							   //修改nodenews表，资产的是否是搜索栏目字段
							   nodeNews2.setInresultpage(0);
							   int temp = nodeNewService.updateInresultpage(nodeNews2);
							   logger.info("将资产[nodeid:"+nodeNews2.getNodeId()+";newsid:"
									   +nodeNews2.getNewsId()+"]修改为屏蔽栏目资产结果："+ temp);
						   }else{
							   logger.info("将资产[nodeid:"+nodeNews2.getNodeId()+";newsid:"
									   +nodeNews2.getNewsId()+"]通知百度失败"); 
						   }    
					   }
				}
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
    	doGet(request,response);      
    } 
}
