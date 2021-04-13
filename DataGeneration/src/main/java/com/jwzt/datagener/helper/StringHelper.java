package com.jwzt.datagener.helper;

import com.alibaba.fastjson.JSONObject;
import com.jwzt.datagener.model.Pg;

public class StringHelper {
	
	public static int stringParseInt(String param,int defaultValue){
		
		int returnParma = defaultValue;
		try {
			returnParma = Integer.parseInt(param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnParma;
	}
	
	public  static String getStr(String para){
		if(null == para || "null".equals(para)){
			return "";
		}else{
			return para;
		}
	}

	public static void main(String[] args) {
		Pg pg = new Pg();
		
		JSONObject ob = new JSONObject();
		ob.put("1", pg.getActors());
		ob.put("2", getStr(pg.getActors()));
		
		System.out.println(ob.toJSONString());
	}
}
