package com.jwzt.datagener.mgr;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jwzt.datagener.model.AppConextConfig;
import com.jwzt.datagener.model.SearchConfigure;


public class DataManager {
	
	/***
	 * 判断该ppvid是否免费的
	 * @param ppvid
	 * @return
	 */
	public static String ppvisFree(String ppvid){
		if(ppvid == null){
			return "0";
		}
		return  ppvid.equals(AppConextConfig.getSearchConfig().getFreePpvId()) ? "1" : "0";
	}
	
	/**
	 * 判断是否需要在搜索结果页里面搜到，true需要，false不需要
	 * @param id 栏目编号 
	 * @return
	 */
	public static boolean nodeInResult(String id) {
		SearchConfigure search = AppConextConfig.getSearchConfig();
		boolean insearchResult = false;
		if(StringUtils.isBlank(search.getNotSearchNodes())){
			insearchResult = true;
		}else{
			insearchResult = ! search.getNotSearchNodeList().contains(Integer.parseInt(id));
		}
		return insearchResult;
	}
	
	public static void main(String[] args) {
//		String ss = "78775#78696#77275#77293#77687#77298#78679#77381#90413#90404#82267#78545#90423#77412#82784#82293#118600#78604#77360#78431#92403#147078#178192";
//		if(StringUtils.isBlank(ss)){
//			System.out.println("yes");
//		}else {
//			System.out.println("no");
//		}
		
		
		Set<Integer> set = new HashSet<Integer>();
        set.add(78775);
        set.add(78696);
        set.add(77275);
        
        if(set.contains(Integer.parseInt("77275"))){
        	System.out.println("找到了");
        }else{
        	System.out.println("没有找到了");
        }
	}
}
