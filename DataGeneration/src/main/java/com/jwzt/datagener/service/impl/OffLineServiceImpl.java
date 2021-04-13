package com.jwzt.datagener.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jwzt.datagener.dao.NodeNewDao;
import com.jwzt.datagener.helper.JsonUtil;
import com.jwzt.datagener.helper.StringHelper;
import com.jwzt.datagener.lucence.NodeNewNameSearch;
import com.jwzt.datagener.mgr.BaiDuMgr;
import com.jwzt.datagener.mgr.IdMgr;
import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.Duration;
import com.jwzt.datagener.model.NodeNews;
import com.jwzt.datagener.model.NodeTree;
import com.jwzt.datagener.model.Pg;
import com.jwzt.datagener.model.Program;
import com.jwzt.datagener.service.OffLineService;

public class OffLineServiceImpl implements OffLineService {
	private static Logger logger = Logger.getLogger(OffLineServiceImpl.class);
	
	private static String varietyShellNodeId = AppConextConfig.getSearchConfig().getVarietyShellNodeId();//综艺壳存放栏目id
	
	@Autowired
	NodeNewDao nodeNewDao;
	
	@Override
	public JSONObject offLineMovieAndService(Pg pg) {
		// TODO Auto-generated method stub
			logger.info("【增量下线】影视剧资产【"+pg.getId()+"==>nodeid:"+pg.getNode_id()+"】需要下线");
			NodeNews nodeNews = new NodeNews();
			//先将数据库中当前资产修改为下线
			int newId = StringHelper.stringParseInt(pg.getId(),-1);
			nodeNews.setNodeId(pg.getNode_id());
			nodeNews.setNewsId(newId);
			nodeNews.setStatus(2);
			nodeNewDao.updateStatus(nodeNews);//先将资产修改为下线
			//可以通知百度下线
			pg.setIs_online(0);
			
			//生成局方的json
			JSONObject baiduJson = JsonUtil.creatBaiDuJson(pg);
			
			return baiduJson;
		
		
		
	}


}
