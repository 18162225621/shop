package com.jwzt.datagener.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Coder {
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

public MD5Coder() {
}

private static String byteToArrayString(byte bByte) {

	int iRet = bByte;
	// System.out.println("iRet="+iRet);
	if (iRet < 0) {
		iRet += 256;
	}
	int iD1 = iRet / 16;
	int iD2 = iRet % 16;
	return strDigits[iD1] + strDigits[iD2];
}

@SuppressWarnings("unused")
private static String byteToNum(byte bByte) {
	int iRet = bByte;
	System.out.println("iRet1=" + iRet);
	if (iRet < 0) {
		iRet += 256;
	}
	return String.valueOf(iRet);
}

private static String byteToString(byte[] bByte) {
	StringBuffer sBuffer = new StringBuffer();
	for (int i = 0; i < bByte.length; i++) {
		sBuffer.append(byteToArrayString(bByte[i]));
	}
	return sBuffer.toString();
}

/*
 * md5 intf
 */
public static String GetMD5Code(String strObj) {
	String resultString = null;
	try {
		resultString = new String(strObj);
		MessageDigest md = MessageDigest.getInstance("MD5");
		// md.digest()
		resultString = byteToString(md.digest(strObj.getBytes()));
	} catch (Exception ex) {
		ex.printStackTrace();
	}
	return resultString;
}

/*
 * md5 intf
 */
public static String GetMD5Code(byte[] bytesObj) {
	String resultString = null;
	try {

		MessageDigest md = MessageDigest.getInstance("MD5");
		// md.digest()
		resultString = byteToString(md.digest(bytesObj));
	} catch (NoSuchAlgorithmException ex) {
		ex.printStackTrace();
	}
	return resultString;
}
}
