package com.jwzt.datagener.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jwzt.datagener.helper.JsonUtil;

import com.jwzt.datagener.model.Pg;

import com.jwzt.datagener.service.OnLineService;

public class OnLineServiceImpl implements OnLineService {

	@Override
	public JSONObject movieAndNewsOnLine(Pg pg) {
		JSONObject baiduJson = JsonUtil.creatBaiDuJson(pg);

		return baiduJson;
	}
	
}
