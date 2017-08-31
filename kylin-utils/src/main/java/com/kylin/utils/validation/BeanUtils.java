package com.kylin.utils.validation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kylin.utils.DateUtil;
import com.kylin.utils.FormatNum;
import com.kylin.utils.StringUtil;

public class BeanUtils {
	public static <E> E checkFieldValueNull(E bean) {
		if (bean == null) {
			return null;
		}
		Class<?> cls = bean.getClass();
		Method[] methods = cls.getDeclaredMethods();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			try {
				String fieldGetName = parGetName(field.getName());
				if (!checkGetMet(methods, fieldGetName)) {
					continue;
				}
				Method fieldGetMet = cls
						.getMethod(fieldGetName, new Class[] {});
				Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});
				if (fieldVal != null) {
					String type = field.getType().toString();// 得到此属性的类型
					if (type.endsWith("String")) {
						// System.out.println(field.getType()+"\t是String");
						field.setAccessible(true);
						field.set(bean, replaceBlank(fieldVal.toString())); // 给属性设值
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		return bean;
	}
	
	
	/**
	 * 初始化bean，对属性为null设置默认值
	 * @author kezhiyi
	 * @date 20150731
	 * @param bean
	 * @return 
	 */
	public static <E> E initBean(E bean) {
		if (bean == null) {
			return null;
		}
		Class<?> cls = bean.getClass();
		Method[] methods = cls.getDeclaredMethods();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			try {
				String fieldGetName = parGetName(field.getName());
				if (!checkGetMet(methods, fieldGetName)) {
					continue;
				}
				Method fieldGetMet = cls
						.getMethod(fieldGetName, new Class[] {});
				Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});
				if (fieldVal == null) {
					String type = field.getType().toString();// 得到此属性的类型
					if (type.toLowerCase().endsWith("string")) {
						// System.out.println(field.getType()+"\t是String");	
						    field.setAccessible(true);
							field.set(bean, ""); // 给属性设值																	
					}
					if (type.toLowerCase().endsWith("date")) {
						// System.out.println(field.getType()+"\t是Date");
							field.setAccessible(true);
							field.set(bean,DateUtil.smartFormat("0000-00-00")); // 给属性设值
										
					}
					if (type.toLowerCase().endsWith("integer")||type.toLowerCase().endsWith("int")) {
						// System.out.println(field.getType()+"\t是Integer");
							field.setAccessible(true);
							field.set(bean,StringUtil.getStrToInt("0")); // 给属性设值										
					}
					if (type.toLowerCase().endsWith("bigdecimal")) {
						// System.out.println(field.getType()+"\t是BigDecimal");
							field.setAccessible(true);
							field.set(bean,new BigDecimal(0.00)); // 给属性设值										
					}
					if (type.toLowerCase().endsWith("long")) {
						// System.out.println(field.getType()+"\t是Long");
							field.setAccessible(true);	
							field.set(bean,StringUtil.getStrToLong("0")); // 给属性设值											
					}
					if (type.toLowerCase().endsWith("double")) {
						// System.out.println(field.getType()+"\t是Long");
							field.setAccessible(true);
							field.set(bean,StringUtil.getStrToDouble("0")); // 给属性设值										
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		return bean;
	}

	/**
	 * 对bean中需要脱敏字段进行处理
	 * @author kezhiyi
	 * @date 20150801
	 * @param bean
	 * @param 
	 * @return 
	 */
	public static <E> E SensitiveBean(E bean,HashMap<String,String> SensitiveFields) {
		if (bean == null) {
			return null;
		}
		if(SensitiveFields==null||SensitiveFields.size()<1){
			return null;
		}
		Class<?> cls = bean.getClass();
		Method[] methods = cls.getDeclaredMethods();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			try {
				String fieldGetName = parGetName(field.getName());
				if (!checkGetMet(methods, fieldGetName)) {
					continue;
				}
				Method fieldGetMet = cls
						.getMethod(fieldGetName, new Class[] {});
				Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});
				if (fieldVal != null) {
					field.setAccessible(true);
					field.set(bean, replaceBlank(fieldVal.toString())); // 给属性设值
					if(SensitiveFields.containsKey(field.getName())){
						if(SensitiveFields.get(field.getName()).toLowerCase().equals("phone")){
							field.setAccessible(true);
							field.set(bean, FormatNum.convertPhone(fieldVal.toString())); // 给属性设值
						}else if(SensitiveFields.get(field.getName()).toLowerCase().equals("bankcard")){
							field.setAccessible(true);
							field.set(bean, FormatNum.convertBankCard(fieldVal.toString())); // 给属性设值
						}else if(SensitiveFields.get(field.getName()).toLowerCase().equals("fixphone")){
							field.setAccessible(true);
							field.set(bean, FormatNum.convertFixedPhone(fieldVal.toString())); // 给属性设值
						}else if(SensitiveFields.get(field.getName()).toLowerCase().equals("idcard")){
							field.setAccessible(true);
							field.set(bean, FormatNum.convertIdentityCard(fieldVal.toString())); // 给属性设值
						}else if (SensitiveFields.get(field.getName()).toLowerCase().equals("ezbzh")) {
							field.setAccessible(true);
							field.set(bean, FormatNum.convertEzbUserName(fieldVal.toString())); // 给属性设值
						}
												
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		return bean;
	}
	
	
	/**
	 * 拼接某属性的 get方法
	 *
	 * @param fieldName
	 * @return String
	 */
	public static String parGetName(String fieldName) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		int startIndex = 0;
		if (fieldName.charAt(0) == '_')
			startIndex = 1;
		return "get"
				+ fieldName.substring(startIndex, startIndex + 1).toUpperCase()
				+ fieldName.substring(startIndex + 1);
	}

	/**
	 * 判断是否存在某属性的 get方法
	 *
	 * @param methods
	 * @param fieldGetMet
	 * @return boolean
	 */
	public static boolean checkGetMet(Method[] methods, String fieldGetMet) {
		for (Method met : methods) {
			if (fieldGetMet.equals(met.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 去掉特殊字符
	 * <p> (描述)  </p>
	 * @Title: replaceBlank 
	 * @param @param str
	 * @param @return
	 * @throws 
	 * @author wanghaolong
	 * @date 2015年7月24日 下午1:02:04
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n|\f");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
			dest = dest.replace(" ", "");
			dest = dest.trim();
			dest = dest.replace("　", "");
			dest = StringUtil.strClear(dest, StringUtil.ascii2Char(55364));
			dest = StringUtil.strClear(dest, StringUtil.ascii2Char(57019));
			dest = StringUtil.strClear(dest, StringUtil.ascii2Char(12288));
		}
		return dest;
	}
	
	/**
	 * 对属性为空字符设置默认值null
	 * @author kezhiyi
	 * @date 20150731
	 * @param bean
	 * @return 
	 */
	public static <E> E emptyStringToNull(E bean) {
		if (bean == null) {
			return null;
		}
		Class<?> cls = bean.getClass();
		Method[] methods = cls.getDeclaredMethods();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			try {
				String fieldGetName = parGetName(field.getName());
				if (!checkGetMet(methods, fieldGetName)) {
					continue;
				}
				Method fieldGetMet = cls
						.getMethod(fieldGetName, new Class[] {});
				Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});
				if (fieldVal != null) {
					String type = field.getType().toString();// 得到此属性的类型
					if (type.toLowerCase().endsWith("string")) {
						if (StringUtil.isEmpty(fieldVal.toString())) {
							field.setAccessible(true);
							field.set(bean, null); // 给属性设值																	
						}
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		return bean;
	}
}
