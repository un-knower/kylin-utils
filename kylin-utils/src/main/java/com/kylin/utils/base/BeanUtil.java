//package com.kylin.utils.base;
//
//import java.beans.BeanInfo;
//import java.beans.IntrospectionException;
//import java.beans.Introspector;
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.common.collect.Maps;
//import com.jfinal.config.Constants;
//
//public class BeanUtil {
//	
//	/** 日期格式 */
//	// yyyy-MM-dd HH:mm:ss
//	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
//	// yyyy-MM-dd HH:mm:ss
//	public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";
//	//yyyyMMddHHmmss
//	public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS_ = "yyyyMMddHHmmss";
//	
//	public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
//	public static final String DATE_FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
//
//	public static final String MZH_DATE_FORMAT = "MM/dd/YYYY HH:mm:ss";
//	
//	public static final String DATE_FORMAT_yyyyMMddHH = "yyyyMMddHH";
//
//	private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);
//
//	public static <T> Object  mapToBean(Class<? extends Object> cls,Map<String,String> m ){
//		Collection<String>  c = new ArrayList<String>();
//		Field[] declaredFields = cls.getDeclaredFields();
//		for (Field field : declaredFields) {
//			c.add(field.getName().toLowerCase());
//		}
//		Map<String,Object> _m = Maps.newHashMap();
//		for (Entry<String, String> entry : m.entrySet()) {
//			   if(c.contains(entry.getKey())){
//				   String value = entry.getValue();
//				   if(value.length()<=0) continue;
//				   String v = value;
//				   _m.put(entry.getKey(), v);
//			   }
//		}
//		Object convertMap2Bean = convertMap2Bean(cls, _m);
//		
//		return convertMap2Bean;
//	}
//	
//	public static <T> Object  maptobean(Class<? extends Object> cls,Map<String,String[]> m ){
//		Collection<String>  c = new ArrayList<String>();
//		Field[] declaredFields = cls.getDeclaredFields();
//		for (Field field : declaredFields) {
//			c.add(field.getName().toLowerCase());
//		}
//		Map<String,Object> _m = Maps.newHashMap();
//		for (Entry<String, String[]> entry : m.entrySet()) {
//			   if(c.contains(entry.getKey())){
//				   String[] value = entry.getValue();
//				   if(value.length<=0) continue;
//				   String v = value[0];
//				   _m.put(entry.getKey(), v);
//			   }
//		}
//		Object convertMap2Bean = convertMap2Bean(cls, _m);
//		
//		
//		return convertMap2Bean;
//	}
//	
//	public static final <T> T convertMap2Bean(Class<T> clazz, Map<String,? extends Object> map){
//		T t = null;
//		try {
//			t = clazz.newInstance();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		
//		BeanInfo beanInfo = null;
//		try {
//			beanInfo = Introspector.getBeanInfo(clazz);
//		} catch (IntrospectionException e) {
//			e.printStackTrace();
//			return null;
//		}
//		
//		PropertyDescriptor[] targetPds = beanInfo.getPropertyDescriptors();
//		for (PropertyDescriptor targetPd : targetPds) {
//
//			Method writeMethod = targetPd.getWriteMethod();
//			if(writeMethod != null && map.containsKey(targetPd.getName().toLowerCase())){
//				try {
//					if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
//						writeMethod.setAccessible(true);
//					}
//					Object object = map.get(targetPd.getName().toLowerCase());
//					if (targetPd.getPropertyType() == Integer.class) {
//						Integer integer = Integer.valueOf((String) object);
//						writeMethod.invoke(t, integer);
//					}else if(targetPd.getPropertyType() == Long.class){
//						Long aLong = Long.valueOf((String) object);
//						writeMethod.invoke(t, aLong);
//					}else if (targetPd.getPropertyType() == Date.class){
//						Date date = DateUtil.convertStringToDate(DATE_FORMAT_YYYYMMDDHHMMSS, String.valueOf(object));
//						writeMethod.invoke(t, date);
//					}else{
//						writeMethod.invoke(t, object);
//					}
//
//
//				} catch (Throwable ex) {
//
//					throw new RuntimeException("Could not write property '" + targetPd.getName() + "' from bean", ex);
//				}
//			}
//		}
//		
//		return t;
//	}
//
//
//	/**
//	 * 将Map<String,String[]> 转换为Map<String,String>
//	 * @param m
//	 * @return
//	 */
//	public static Map<String,String>  converMap(Map<String,String[]> m ){
//		Collection<String>  c = new ArrayList<String>();
//		Map<String,String> _m = Maps.newHashMap();
//		for (Entry<String, String[]> entry : m.entrySet()) {
//				String[] value = entry.getValue();
//				if(value.length<=0) continue;
//				String v = value[0];
//				logger.info("key= " + entry.getKey() + " and value= " + v);
//			_m.put(entry.getKey(), v);
//		}
//		return _m;
//	}
//
//}
