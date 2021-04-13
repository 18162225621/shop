package com.jwzt.datagener.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecialCharacterHelper {

	/**
	 * 正则替换所有特殊字符
	 * @param orgStr
	 * @return
	 */
	public static String replaceSpecStr(String orgStr){
		if (null!=orgStr&&!"".equals(orgStr.trim())) {
			String regEx="[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(orgStr);
			return m.replaceAll("/");
		}
		return "";
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str= "我！@#￥%……&*（）——+：“《》？{}|!@#$%^&*()_+<>?:{}| 是一个中国人";
		System.out.println("替换前："+str+"；长度是："+str.length());
		str = replaceSpecStr(str);
		System.out.println("替换后："+str+"；长度是："+str.length());
		
		System.out.println("201803229104623346".length());
	}

}
