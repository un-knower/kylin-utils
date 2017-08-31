package com.kylin.utils.base;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	
	public static String getSignMsg(String paaParamsArray[]) {
		
		String paaStr = "";
		for (int i = 0; i < paaParamsArray.length; i++) {
			if( !"".equals(paaParamsArray[i]) && null!= paaParamsArray[i]){
				paaStr += paaParamsArray[i + 1] + "=" + paaParamsArray[i] + "&";
			}
			i++;
		}
		
		String md5Str = md5(paaStr.substring(0, paaStr.length() - 1));
		
		return md5Str;

	}

	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		return hexString(hash);
	}

	public static final String hexString(byte[] bytes) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			buffer.append(hexString(bytes[i]));
		}
		return buffer.toString();
	}

	public static final String hexString(byte byte0) {
		char ac[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
		char ac1[] = new char[2];
		ac1[0] = ac[byte0 >>> 4 & 0xf];
		ac1[1] = ac[byte0 & 0xf];
		String s = new String(ac1);
		return s;
	}
}
