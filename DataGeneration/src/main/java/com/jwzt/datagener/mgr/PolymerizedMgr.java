package com.jwzt.datagener.mgr;

import com.jwzt.datagener.model.CPType;

public class PolymerizedMgr {

	public static boolean isYouKu(String contentChannel){
		String IS_REGEX ="^(.*"+CPType.YouKu.getString()+")[0-9]{2}$";
		return contentChannel.matches(IS_REGEX);
	}
	
	public static boolean isSoHu(String contentChannel){
		String IS_REGEX ="^(.*"+CPType.SoHu.getString()+")[0-9]{2}$";
		return contentChannel.matches(IS_REGEX);
	}
	
	public static boolean isHDR(String contentChannel){
		String IS_REGEX ="^(.*"+CPType.SonyHDR.getString()+")[0-9]{2}$";
		return contentChannel.matches(IS_REGEX);
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
