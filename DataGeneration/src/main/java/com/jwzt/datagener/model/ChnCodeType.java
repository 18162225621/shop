package com.jwzt.datagener.model;

public enum ChnCodeType {
	
	Movie("1000001"),
	
	Series("1000002"),
	
	Comic("1000003"), //动漫
	
	Variety("1000004"), //综艺
	
	Music("1000005"),

	Sport("1000006"),
	
	Education("1000007"),
	ShortVideo("1000008"), //片花
	Record("1000009"),  //纪录片
	Entertainment("1000010"), //娱乐
	Fashion("1000011"), //时尚
	News("1000019"),
	Other("1000013");
	
	
	private final String cpsId;
	
	private ChnCodeType(String cpsId){
		this.cpsId = cpsId;
	}
	
	public String getString() {
		return this.cpsId;
	}
	
}

