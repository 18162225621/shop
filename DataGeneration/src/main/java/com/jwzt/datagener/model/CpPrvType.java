package com.jwzt.datagener.model;

public enum CpPrvType {
	
	Viod("1001"), //视频类
	
	Music("1002"), //音乐类
	
	Application("1003"), //应用类
	
	Written("1004"), //文字类
	
	Picture("1005"), //图片类

	VoidAndMusic("1006"), //视频&音乐类
	
	Other("1009"), //其他
	
	CpPrvCode("51000002"),
	
	CpPrvdName("wasujx");
	
	
	
	
	private final String cpsId;
	
	private CpPrvType(String cpsId){
		this.cpsId = cpsId;
	}
	
	public String getString() {
		return this.cpsId;
	}
	
}

