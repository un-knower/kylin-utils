package com.kylin.utils.base;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

public class Base64Util {
	/**
	 * 将param编码
	 * @param param
	 * @return
	 */
	public static String encode(String param) {
		if (param == null)
			return null;
		return (new BASE64Encoder()).encode(param.getBytes());
	}

	/**
	 * 将param解码
	 * @param param
	 * @return
	 */
	public static String decode(String param) {
		if (param == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(param);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}
	
    // 加密  
    public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
  
    // 解密  
    public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "GBK");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
}  
	
