package com.jwzt.datagener.model;

public enum Duration {

	MOVIE(5400),//90分钟
	
	SERIES(2400),//40分钟
	
	COLUMN(7200),//1200分钟
	
	NEWS(600); //10分钟
	
	private final int newsDuration;

	private  Duration(int duration) {
		this.newsDuration = duration;
	}
	
	public int getInt(){
		return this.newsDuration;
	}
	
	
}
