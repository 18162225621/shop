package com.jwzt.datagener.model;

import com.jwzt.datagener.helper.MyPinyinHelper;

public class CnToSpell {
	
	public CnToSpell(){
		
	}
     
	public static String getPinYinHeadCharNoSpace(String fullSpell){
		
		return MyPinyinHelper.getPinYinHeadCharNoSpace(fullSpell);
	}
  	public static String getEndSpell(String fullSpell){
  		
		String spell = MyPinyinHelper.getPinYinHeadChar(fullSpell);
/*		String spells[] = spell.split(" ");
		StringBuffer spellMiddle = new StringBuffer();
		StringBuffer spellEnd = new StringBuffer();
		StringBuffer spellMiddle2 = new StringBuffer();
		StringBuffer spellEnd2 = new StringBuffer();
		for(int i = 0;i<spells.length;i++){
			spellMiddle.append(spells[i]);
			spellEnd.append(" ").append(spellMiddle);
		}
		for(int i = spells.length - 1;i > 1;i --){
			spellMiddle2 = spellMiddle2.insert(0,spells[i]);
			spellEnd2 = spellEnd2.append(" " + spellMiddle2);
		}
		String result = spellEnd.append(spellEnd2).toString();
		result = result.replace("·", "").replace("、", " ");
		return result;*/
		
		return spell;
	}

  	public static String getFirstSpell(String fullSpell){

  		if(fullSpell == null) return "";
  		return MyPinyinHelper.getPinYinFirstHeadChar(fullSpell).toLowerCase();
  	}
	public static boolean isEnglish(String str){
		if(str == null)
			return false;
		return str.matches("[0-9a-zA-Z,.;:]+");
	}
	
	public static void main(String[] args) {
//		String str = "盛夏晚晴天";
		//System.out.println(CnToSpell.getEndSpell(str));
		
	}
}
