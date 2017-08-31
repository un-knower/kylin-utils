package com.kylin.utils.base;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXValidator;
import org.dom4j.util.XMLErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XMLUtil {
	
	 private static Logger logger = LoggerFactory.getLogger(XMLUtil.class);

	public static void main(String[] args) { 
//		targetNamespace="http://www.w3school.com.cn" 
//		xmlns="http://www.w3school.com.cn" 
		String requestXML = "<order><order_info><user_id>10002</user_id><const_id>M000017</const_id><product_id>P000017</product_id><request_time>2015-08-25T19:29:54</request_time><user_order_id>YNC2015122144551</user_order_id><amount>1000</amount><user_ip_address>172.0.0.1</user_ip_address></order_info><order_items><order_item id='YNC00003'><amount>400</amount><goods_count>4</goods_count><inter_merchant_code>YNC30303030</inter_merchant_code></order_item><order_item id='YNC00002'><amount>600</amount><goods_count>1</goods_count><inter_merchant_code>YNC30303030</inter_merchant_code></order_item></order_items></order>";
		String xsdFileName = "createorderRequest.xsd";
		Map<String, String> resultMap = validateXMLByXSD(requestXML, xsdFileName);
		System.out.println("result : " + resultMap.get("result"));
		System.out.println("msg : " + resultMap.get("msg"));
    } 

    /**
     * 通过XSD（XML Schema）校验XML 
     * @param requestXML
     * @param xsdFileName
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String, String> validateXMLByXSD(String requestXML, String xsdFileName) { 
    	Map<String, String> resultMap = new HashMap<String, String>();
    	String classPath = XMLUtil.class.getClassLoader().getResource("").toString();
        String xsdFilePath = classPath + "profiles/xsd/" + xsdFileName; 
        try { 
            //创建默认的XML错误处理器 
            XMLErrorHandler errorHandler = new XMLErrorHandler(); 
            //获取基于 SAX 的解析器的实例 
            SAXParserFactory factory = SAXParserFactory.newInstance(); 
            //解析器在解析时验证 XML内容。 
            factory.setValidating(true); 
            //指定由此代码生成的解析器将提供对 XML名称空间的支持。 
            factory.setNamespaceAware(true); 
            //使用当前配置的工厂参数创建 SAXParser的一个新实例。 
            SAXParser parser = factory.newSAXParser(); 
            //获取要校验xml文档实例 
            Document xmlDocument = DocumentHelper.parseText(requestXML);
            parser.setProperty( 
                    "http://java.sun.com/xml/jaxp/properties/schemaLanguage", 
                    "http://www.w3.org/2001/XMLSchema"); 
            //"file:/E:/SVN/Operation/Order/branch/order/target/classes/profiles/xsd/createorderRequest.xsd"
            parser.setProperty( 
                    "http://java.sun.com/xml/jaxp/properties/schemaSource", 
                    xsdFilePath); 
            //创建一个SAXValidator校验工具，并设置校验工具的属性 
            SAXValidator validator = new SAXValidator(parser.getXMLReader()); 
            //设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。 
            validator.setErrorHandler(errorHandler); 
            //校验 
            validator.validate(xmlDocument); 
            //如果错误信息不为空，说明校验失败，打印错误信息 
            if (errorHandler.getErrors().hasContent()) { 
                logger.info("-----------------XML文件通过XSD文件校验失败！---------------------"); 
                resultMap.put("result", "false");
                resultMap.put("msg","XML文件通过XSD文件校验失败！");
                Element errors = errorHandler.getErrors();
                List<Element> errorList = errors.elements();
                for (Iterator<Element> it = errorList.iterator(); it.hasNext();){
                	Element error = it.next();
                	logger.info("------------error: column=" + error.attributeValue("column")
                			 + ", line=" + error.attributeValue("line")
                			 + " : " + error.getText());
                }
                return resultMap;
            } else { 
                logger.info("-----------------XML文件通过XSD文件校验成功！---------------"); 
                resultMap.put("result", "true");
                return resultMap;
            } 
        } catch (DocumentException e) {
			resultMap.put("result", "false");
			resultMap.put("msg", "解析XML失败，请确认输入是否为有效的XML格式！");
			return resultMap;
		} catch (Exception ex) { 
            logger.info("---------------XML文件: " + requestXML + " 通过XSD文件:" + xsdFilePath + "检验失败。\n原因： " + ex.getMessage());
            resultMap.put("result", "false");
            resultMap.put("msg", "XML校验失败！");
            return resultMap;
        } 
    } 
	
}
