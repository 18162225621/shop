package com.jwzt.datagener.mgr;

public class IdMgr {

	public static String columnParentId(String id){
		return "pid"+ id;
	}
	
	public static String setId(String pid,int set){
		return pid+"set"+set;
	}
}
