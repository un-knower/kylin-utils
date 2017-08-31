package com.kylin.utils;

import com.thoughtworks.xstream.XStream;

public class XmlUtil {

    public static <T> String objToXmlStr(String rootName, T t, Class c) {
        XStream xstream = new XStream();
        xstream.alias(rootName, c);
        return xstream.toXML(t);
    }
}
