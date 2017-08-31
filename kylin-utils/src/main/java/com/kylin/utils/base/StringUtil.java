package com.kylin.utils.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thonny on 2015-6-3.
 */
public class StringUtil {

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static Integer calculStrings(String a,String b){
        if(CheckUtil.checkNullOrEmpty(a))
            throw new NullPointerException("Params 1 is null or empty!");
        if(CheckUtil.checkNullOrEmpty(b))
            throw new NullPointerException("Params 2 is null or empty!");
        return Integer.valueOf(a)-Integer.valueOf(b);
    }
    
    /**
     * 将请求返回的key=value&形式的参数解析放到map中
     * @param param
     * @return
     */
    public static Map<String,String> parseParam(String param){
		String[] paramArr = param.split("&");
		Map<String,String> result = new HashMap<String,String>();
		for (String keyVal : paramArr) {
			String[] p = keyVal.split("=");
			if(p.length<2){
				result.put(p[0], "");
			}else {
				result.put(p[0], p[1]);
			}
		}
		return result;
    }
    
}
