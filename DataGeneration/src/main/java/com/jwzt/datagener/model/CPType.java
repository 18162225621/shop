package com.jwzt.datagener.model;

public enum CPType {
	WaSu("01"),
	
	SoHu("02"),
	
	YouKu("03"),
	
	LeTv("04"),
	
	IQiYi("05"),
	
	VirtualCinema("06"),
	
	NetEase("07"),
	
	ShiGuang("08"),
	
	WeiJian("09"),
	
	ZheJiangUnicom("10"),
	
	NetEaseMUSIC("11"),
	
	MiGu("12"),
	
	StereoWorld3D("13"),
	
	TianLaiKGE("14"),
	
	SonyHDR("15");
	
	private final String cpId;
	
	private CPType(String cpId){
		this.cpId = cpId;
	}
	
	public String getString() {
		return this.cpId;
	}
}

