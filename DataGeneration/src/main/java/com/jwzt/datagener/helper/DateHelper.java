package com.jwzt.datagener.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
	private static SimpleDateFormat S_D_F=new SimpleDateFormat("yyyyMMddHHmmssSSS") ;
	private static SimpleDateFormat S_D_F_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat S_D_F_3=new SimpleDateFormat("yyyyMMddHHmmss") ;
	private static SimpleDateFormat S_D_F_DATE=new SimpleDateFormat("yyyyMMdd") ;
	private static SimpleDateFormat S_D_F_DATE_2 =  new SimpleDateFormat("yyyymmdd");
	
	
	/**
	 * 分别获得年，月，日
	 * @param type
	 * @return
	 */
	public static String get(String type){
		String returnValue="";
		Date date = new Date();
		
		if("year".equals(type)){
			returnValue = (date.getYear()+1900)+"";//得到年份。因为得到的是1900年后至今过了多少年，所以要加1900
		}else if("month".equals(type)){
			int month = (date.getMonth()+1);//因为得到的结果是0~11,故而加一。
			if(month < 10){
				returnValue = "0"+month;
			}else{
				returnValue = month+"";
			}
		}else if("day".equals(type)){
			int day = date.getDate();//得到月份中今天的号数
			if(day<10){
				returnValue = "0"+day;
			}else{
				returnValue = day+"";
			}
		}

		return returnValue;
	}

	/**
	 * 获得日期
	 * @return
	 */
	public static  String  getDateFromYearToMillisecond(){
		return S_D_F.format(new Date());
	}
	
	
	public static String getDate(){
		return S_D_F_DATE.format(new Date());
	}
	
	public static String getDateFromYearToMillisecond2(){
		return S_D_F_3.format(new Date());
	}
	
	public static String getDateString(String str){
		
		String year = str.substring(0,4);
		String month = str.substring(4,6);
		String day = str.substring(6,8);
		String HH = str.substring(8,10);
		String mm = str.substring(10,12);
		String ss = str.substring(12,14);
		return year+"-"+month+"-"+day+" "+HH+":"+mm+":"+ss;
	}
	
	/**
	 * 获得日期
	 * @return
	 */
	public static  String getData(){
		
		return S_D_F_DATE_2.format(new Date());
	}
	
	/**
	 * 获得固定格式的日期
	 * @param type
	 * @return
	 */
	public static String getDate(String type){
		SimpleDateFormat sdf=new SimpleDateFormat(type) ;
		return sdf.format(new Date());
	}
	
	/**
	 * 
	 * @return
	 */
	public static long getTimeMillis(){
		return System.currentTimeMillis();
	}

	
	/***
	 * 由时间串获取时间戳
	 * @param timeStr
	 * @return
	 */
	public static long getTime(String timeStr){
		
		try{
			return S_D_F_2.parse(timeStr).getTime();
		}catch (Exception e) {
			return 0;
		}
	}
	
	
	/***
	 * 将毫秒转换为日期字符串yyyy-MM-dd HH:mm:ss
	 * @param mills
	 * @return
	 */
	public static String MillSecondsToyMdHmsss(long mills,String type){
		
		return new SimpleDateFormat(type).format(new Date(mills));
	}
	
	/**
	 * 注意，a和b的格式，长度必须保持一致
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isGt(String a,String b){
		if(a.compareTo(b) > 0){
			return true;
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getTimeMillis());
		//System.out.println(isGt("18-09-05","20180904"));
	}
		
}
