package com.jwzt.datagener.servlet;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestFile {
	
	

	public static Long convertTimeToLong(String time) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdf.parse(time);
			return date.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	
	}
	
	public static void main(String[] args) {
		
		long longTime = 1509601138000l;
		Date date = new Date(longTime );//用Date构造方法，将long转Date
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stringDate = format.format(date);//用SimpleDateFormat将Date转xxxx-xx-xx格式的字符

		System.out.println(stringDate);
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String s = sdf.format(new Date());
//		System.out.println(s);
		String s ="2017-02-26 00:58:02";
		System.out.println(convertTimeToLong(s));
	
		
		
		
	}

}
