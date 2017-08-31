package com.kylin.utils.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Pattern;
import javax.validation.metadata.ConstraintDescriptor;

public class ValidatorUtil {

    static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    /**
     * 
     * Discription:校验
     * 
     * @param e
     * @return List<ErrorMsg>
     * @throws Exception
     * @author whl
     * @since 2016年1月22日
     */
    public static <E> List<ErrorMsg> valide(E e) throws Exception {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<E>> constraintViolations = validator.validate(e);
        return iterator(e, constraintViolations);
    }

    /**
     * 
     * Discription:分组校验
     * 
     * @param e
     * @param z
     * @return List<ErrorMsg>
     * @throws Exception
     * @author whl
     * @since 2016年1月22日
     */
    public static <E> List<ErrorMsg> valide(E e, Class<?> z) throws Exception {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<E>> constraintViolations = validator.validate(e, z);
        return iterator(e, constraintViolations);
    }

    public static <E> List<ErrorMsg> iterator(E e, Set<ConstraintViolation<E>> constraintViolations) throws Exception {
        List<ErrorMsg> errorMsgList = new ArrayList<ErrorMsg>();
        Iterator<ConstraintViolation<E>> it = constraintViolations.iterator();

        while (it.hasNext()) {
            ErrorMsg errorMsg = new ErrorMsg();
            ConstraintViolation<E> tVilidateRs = it.next();

            errorMsg.setPropertyPath(tVilidateRs.getPropertyPath().toString());
            errorMsg.setMsg(tVilidateRs.getMessage());
            errorMsg.setInvalidValue(tVilidateRs.getInvalidValue() != null ? tVilidateRs.getInvalidValue().toString()
                    : "");
            ConstraintDescriptor<?> t = tVilidateRs.getConstraintDescriptor();
            Map<String, Object> s = t.getAttributes();
            errorMsg.setRegexp(s.get("regexp") != null ? s.get("regexp").toString() : "");
            boolean flags = true;
            if (errorMsg.getRegexp().equals(
                    "^[0-9]\\d*$|^([0-9]{0,1}(\\.[0-9][0-9]?))$|^([1-9][0-9]*(\\.[0-9][0-9]?))$")) {// 大于或等于零、最多两位小数的阿拉伯数字正则表达式
                String value = errorMsg.getInvalidValue();
                String regEx = "^[0-9]{1,}\\.[0-9]{2,}[1-9]$";// 两位以上的小数正则表达式
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(value);
                boolean boo = matcher.matches();
                if (boo) {
                    DecimalFormat df = new DecimalFormat("#.00");
                    System.out.println(df.format(Double.parseDouble(value)));
                    String mothanme =
                            "set"
                                    + errorMsg.getPropertyPath().replaceFirst(
                                            errorMsg.getPropertyPath().substring(0, 1),
                                            errorMsg.getPropertyPath().substring(0, 1).toUpperCase());
                    e.getClass().getMethod(mothanme, String.class).invoke(e, df.format(Double.parseDouble(value)));
                    flags = false;
                }
            }
            if (errorMsg.getRegexp().equals("^([0-9][0-9]*)+(.[0-9]{1,4})?$")) {// 大于或等于零、最多四位小数的阿拉伯数字正则表达式
                String value = errorMsg.getInvalidValue();
                String regEx = "^[0-9]{1,}\\.[0-9]{4,}[1-9]$";// 四位以上的小数正则表达式
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(value);
                boolean boo = matcher.matches();
                if (boo) {
                    DecimalFormat df = new DecimalFormat("#.0000");
                    System.out.println(df.format(Double.parseDouble(value)));
                    String mothanme =
                            "set"
                                    + errorMsg.getPropertyPath().replaceFirst(
                                            errorMsg.getPropertyPath().substring(0, 1),
                                            errorMsg.getPropertyPath().substring(0, 1).toUpperCase());
                    e.getClass().getMethod(mothanme, String.class).invoke(e, df.format(Double.parseDouble(value)));
                    flags = false;
                }
            }
            if (errorMsg.getRegexp().equals(
                    "^-?[0-9]\\d*$|^-?([0-9]{0,1}(\\.[0-9][0-9]?))$|^-?([1-9][0-9]*(\\.[0-9][0-9]?))$")) {// 大于或等于零、最多四位小数的阿拉伯数字正则表达式
                String value = errorMsg.getInvalidValue();
                String regEx = "^([0-9]{1,}\\.[0-9]{2,}[1-9])|(-[0-9]{1,}\\.[0-9]{2,}[1-9])$";// 两位以上的小数正则表达式 整数和 负数
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(value);
                boolean boo = matcher.matches();
                if (boo) {
                    DecimalFormat df = new DecimalFormat("#.00");
                    System.out.println(df.format(Double.parseDouble(value)));
                    String mothanme =
                            "set"
                                    + errorMsg.getPropertyPath().replaceFirst(
                                            errorMsg.getPropertyPath().substring(0, 1),
                                            errorMsg.getPropertyPath().substring(0, 1).toUpperCase());
                    e.getClass().getMethod(mothanme, String.class).invoke(e, df.format(Double.parseDouble(value)));
                    flags = false;
                }
            }
            if (flags) {
                errorMsgList.add(errorMsg);
            }
        }
        return errorMsgList;
    }

    /**
     * 验证Integer、Long不能超过最大值
     * 
     * @param bean
     * @param p
     * @return
     * @throws Exception
     */
    public static <E, P> List<ErrorMsg> valideMaxValue(E bean, P p) throws Exception {
        List<ErrorMsg> errorMsgList = new ArrayList<ErrorMsg>();
        Class<?> cls = bean.getClass();
        Class<?> pcls = p.getClass();
        Method[] methods = pcls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();
        String temp = "";
        String type = "";
        String message = ",超过最大值";
        for (Field field : fields) {
            try {
                String fieldGetName = BeanUtils.parGetName(field.getName());
                if (!BeanUtils.checkGetMet(methods, fieldGetName)) {
                    continue;
                }
                Method fieldGetMet = pcls.getMethod(fieldGetName, new Class[] {});
                Object fieldVal = fieldGetMet.invoke(p, new Object[] {});

                if (fieldVal != null) {
                    Field pfields = pcls.getDeclaredField(field.getName());
                    type = field.getType().toString();// 得到此属性的类型
                    if (type.endsWith("Integer")) {
                        temp = BeanUtils.replaceBlank(fieldVal.toString());
                        if (temp.length() > 10) {
                            errorMsgList.add(init(pfields, temp, message + Integer.MAX_VALUE + "!"));
                        } else if (temp.length() == 10) {
                            if (String.valueOf(Integer.MAX_VALUE).compareTo(temp) < 0) {
                                errorMsgList.add(init(pfields, temp, message + Integer.MAX_VALUE + "!"));
                            }
                        }
                    }
                    if (type.endsWith("Long")) {
                        temp = BeanUtils.replaceBlank(fieldVal.toString());
                        if (temp.length() > 19) {
                            errorMsgList.add(init(pfields, temp, message + Long.MAX_VALUE + "!"));
                        } else if (temp.length() == 19) {
                            if (String.valueOf(Long.MAX_VALUE).compareTo(temp) < 0) {
                                errorMsgList.add(init(pfields, temp, message + Long.MAX_VALUE + "!"));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        return errorMsgList;
    }

    public static ErrorMsg init(Field field, String temp, String message) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String values = "";
        ErrorMsg errorMsg = new ErrorMsg();
        if (field.isAnnotationPresent(Pattern.class)) {
            Annotation ann = field.getAnnotation(Pattern.class);
            Method m = ann.getClass().getDeclaredMethod("message", null);
            values = (String) m.invoke(ann, null);
        }
        errorMsg.setMsg(values + message);
        errorMsg.setInvalidValue(temp);
        errorMsg.setPropertyPath(field.getName());
        return errorMsg;
    }

    public static void main(String[] args) throws Exception {

    }
}
