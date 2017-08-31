package com.kylin.utils.validation;

public class ErrorMsg {

    /** 错误信息 */
    private String msg;
    /** 错误属性 */
    private String propertyPath;
    /** 曾则表达式 */
    private String regexp;
    /** 错误值 */
    private String invalidValue;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(String invalidValue) {
        this.invalidValue = invalidValue;
    }

    @Override
    public String toString() {
        return "ErrorMsg [msg=" + msg + ", propertyPath=" + propertyPath + ", regexp=" + regexp + ", invalidValue="
                + invalidValue + "]";
    }

}
