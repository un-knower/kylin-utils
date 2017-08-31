//package com.kylin.utils.base;
//
//import com.google.common.collect.Maps;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by thonny on 2015-8-4.
// */
//public class HttpUtil {
//
//    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
//
//    public static String httpClient(String url,Map<String,String> m){
//        HttpClient httpclient = new DefaultHttpClient();
//        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//        for(Map.Entry<String,String> e:m.entrySet()){
//            formparams.add(new BasicNameValuePair(e.getKey(),e.getValue()));
//        }
//        try {
//            logger.info("即将发送的地址是：["+url+"]");
//            logger.info("即将发送的参数是：[" + JsonUtil.bean2Json(formparams) + "]");
//            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.setEntity(entity);
//            HttpResponse httpResponse = httpclient.execute(httpPost);
//            String s = EntityUtils.toString(httpResponse.getEntity());
//            logger.info("返回的参数时：["+s+"]");
//            return s;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static void main(String[] args) {
//        Map<String,String> m = Maps.newHashMap();
//        m.put("token","2750a7aa8516b4f410c291cc6e52299b");
//        m.put("freezeTime","10/15/2015 23:45:01");
//        m.put("amount","20");
//        m.put("code","5jSd26qbM3");
//        m.put("orderNumber","MZ_XS_10311");
//        m.put("userId","10390");
//        System.out.println(HttpUtil.httpClient("http://www.mianzhuang.com/cotton/control/cotFreezeNotification", m));
//        m = Maps.newHashMap();
//        m.put("token", "2750a7aa8516b4f410c291cc6e52299b");
//        m.put("freezeTime","10/15/2015 23:45:00");
//        m.put("amount","0");
//        m.put("code","KLRhShmyVo");
//        m.put("orderNumber","MZ_XS_10310");
//        m.put("userId", "10390");
//        System.out.println(HttpUtil.httpClient("http://www.mianzhuang.com/cotton/control/cotFreezeNotification", m));
//    }
//}
