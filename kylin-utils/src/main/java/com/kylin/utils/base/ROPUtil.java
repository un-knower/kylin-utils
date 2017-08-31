//package com.kylin.utils.base;
//
//import com.Rop.api.ApiException;
//import com.Rop.api.DefaultRopClient;
//import com.Rop.api.RopRequest;
//import com.Rop.api.RopResponse;
//import com.Rop.api.request.ExternalSessionGetRequest;
//import com.Rop.api.response.ExternalSessionGetResponse;
//import com.google.common.base.Strings;
//import com.rkylin.order.contants.Constants;
//import com.rop.utils.SpringBeanUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.beans.BeanInfo;
//import java.beans.IntrospectionException;
//import java.beans.Introspector;
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.InvocationTargetException;
//import java.util.Date;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * Created by thonny on 2015-5-27.
// */
//public class ROPUtil {
//
//    private static Logger logger = LoggerFactory.getLogger(ROPUtil.class);
//
//    private static Properties prop = (Properties) SpringBeanUtils.getBean("userProperties");
//
//    public static RopResponse getResponse(RopRequest request){
//
//          return  getResponse(request, Constants.ACCOUNT_PRODUCTS_CODE);
//    }
//
//    public static RopResponse getResponse(String request,Map<String,String> requestMap){
//        try {
//            Class<?> name = Class.forName(request);
//            Object instance = name.newInstance();
//            BeanInfo beanInfo = Introspector.getBeanInfo(name);
//            for (PropertyDescriptor s : beanInfo.getPropertyDescriptors()) {
//
//                String v = requestMap.get(s.getName());
//                if(!Strings.isNullOrEmpty(v)){
//                    if (s.getPropertyType() == Integer.class) {
//                        Integer integer = Integer.valueOf( v);
//                        s.getWriteMethod().invoke(instance, integer);
//                    }else if(s.getPropertyType() == Long.class){
//                        Long aLong = Long.valueOf(v);
//                        s.getWriteMethod().invoke(instance, aLong);
//                    }else if (s.getPropertyType() == Date.class){
//                        Date date = DateUtils.parseDate(Constants.DATE_FORMAT_YYYYMMDDHHMMSS, v);
//                        s.getWriteMethod().invoke(instance, date);
//                    }else{
//                        s.getWriteMethod().invoke(instance, v);
//                    }
//                }
//            }
//            return getResponse((RopRequest) instance);
//        } catch (ClassNotFoundException e) {
//            logger.error("Class {"+request+"找不到该类");
//        } catch (InstantiationException e) {
//            logger.error("Class {" + request + " newInstance 失败");
//        } catch (IllegalAccessException e) {
//            logger.error("Class {" + request + " 类型错误");
//        } catch (IntrospectionException e) {
//            logger.error("Class {" + request + " 内省时失败");
//        } catch (InvocationTargetException e) {
//            logger.error("Class {" + request + " 方法写入时失败");
//        }
//        return null;
//    }
//    /**
//     *
//     * @param request
//     * @param product 要调用的项目的name JRD 君融贷,INVOKE 内部
//     * @return
//     */
//    public static RopResponse getResponse(RopRequest request,String product){
//
//        String urlKey = "";
//        String jsonOrXml = "xml";
//        String appKey = prop.getProperty(product+"_KEY");
//        String appSecret = prop.getProperty(product+"_SECRET");
//        String ropUrl = prop.getProperty(product+"_URL");
//        DefaultRopClient ropClient = new DefaultRopClient(ropUrl, appKey,
//                appSecret, jsonOrXml);
//        logger.info("ROP请求参数为:------------------------------------------------------");
//        logger.info(JsonUtil.bean2Json(request));
//        logger.info("-------------------------------------------------------------------");
//        try {
//            RopResponse rsp = ropClient.execute(request,
//                    sessionGet(ropUrl, appKey, appSecret));
//            if (rsp != null) {
//
//                if(rsp.isSuccess()){
//                    Map<String, String> params = rsp.getParams();
//                }else{
//                    urlKey="no-"+rsp.getMsg();
//                }
//            }
//            logger.info("ROP返回参数为:------------------------------------------------------");
//            logger.info(JsonUtil.bean2Json(rsp));
//            logger.info("-------------------------------------------------------------------");
//            return  rsp;
//        } catch (ApiException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        return  null;
//    }
//
//    /**
//     * 若获取到指定异常，重发
//     * @param request
//     * @param product
//     * @return
//     */
//    public static RopResponse multiGetRespronse(RopRequest request,String product){
//
//        String urlKey = "";
//        String jsonOrXml = "xml";
//        String appKey = prop.getProperty(product+"_KEY");
//        String appSecret = prop.getProperty(product+"_SECRET");
//        String ropUrl = prop.getProperty(product+"_URL");
//        DefaultRopClient ropClient = new DefaultRopClient(ropUrl, appKey,
//                appSecret, jsonOrXml);
//        logger.info("ROP请求参数为:------------------------------------------------------");
//        logger.info(JsonUtil.bean2Json(request));
//        logger.info("-------------------------------------------------------------------");
//        try {
//            //重发三次
//            RopResponse rsp = ropClient.execute(request,
//                    sessionGet(ropUrl,appKey,appSecret));
//            if(rsp!=null){
//                if(!rsp.isSuccess()){
//                    if(rsp.getErrorCode().startsWith("S")){
//                        rsp = ropClient.execute(request,
//                                sessionGet(ropUrl,appKey,appSecret));
//                        if(rsp!=null){
//                            if(!rsp.isSuccess()){
//                                if(rsp.getErrorCode().startsWith("S")){
//                                    rsp = ropClient.execute(request,
//                                            sessionGet(ropUrl,appKey,appSecret));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            logger.info("ROP返回参数为:------------------------------------------------------");
//            logger.info(JsonUtil.bean2Json(rsp));
//            logger.info("-------------------------------------------------------------------");
//            return  rsp;
//        } catch (ApiException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        return  null;
//    }
//
//
//    private static  String sessionGet(String ropUrl,String appKey,String appSecret) {
//        String sessionKey = null;
////		String ropUrl=PropertiesUtils.get("ROP_URL");
////		String appKey=PropertiesUtils.get("APP_KEY");
////		String appSecret=PropertiesUtils.get("APP_SECRET");
////		  String ropUrl = "https://115.159.25.44:30005/ropapi";
////		  String appKey = "7E59D764-6484-4F77-9DB1-0EB953E55DBC";
////		  String appSecret = "2845A40B-1D90-44CD-92B1-69FB56009EC1";
//        DefaultRopClient ropClient = new DefaultRopClient(ropUrl, appKey,
//                appSecret);
//        try {
//            ExternalSessionGetRequest sessionGetReq = new ExternalSessionGetRequest();
//
//            ExternalSessionGetResponse sessionGetRsp = ropClient
//                    .execute(sessionGetReq);
//            sessionKey = sessionGetRsp.getSession();
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//
//        }
//        return sessionKey;
//    }
//
//}
