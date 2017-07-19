package com.xiangzhu.plat.utils;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * get java bean from xml file or xmlString
 * Created by liluoqi on 15/7/31.
 */
public class XMLUtils {

    private static final String CHAR_SET = "UTF-8";
    private static XStream xStream = null;

    private static <T> XStream getSingleXStream(Class<T> clazz) {
        if (xStream == null) {
            xStream = new XStream();
            xStream.autodetectAnnotations(true);
            xStream.processAnnotations(clazz);
        } else {
            xStream.autodetectAnnotations(true);
            xStream.processAnnotations(clazz);
        }
        return xStream;
    }

    /**
     * @param file  xml文件
     * @param clazz 类型
     * @param <T>   泛型
     * @return
     */
    public static <T> T convertFromXMLFile(File file, Class<T> clazz) {
        try {
            return (T) getSingleXStream(clazz).fromXML(file);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convertFromXMLFile(String path, Class<T> clazz) {
        try {
            return (T) getSingleXStream(clazz).fromXML(new File(path));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param xmlString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convertFromXMLString(String xmlString, Class<T> clazz) {
        try {
            return (T) getSingleXStream(clazz).fromXML(xmlString);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param object 对象
     * @param clazz  类型
     * @param <T>    类型泛型
     * @return xml字符串
     */
    public static <T> String convertObjectToXml(Object object, Class<T> clazz) {
        try {
            return getSingleXStream(clazz).toXML(object);
        } catch (Exception e) {
            return null;
        }
    }

    public static String addNodePayee(String xmlString, String parentNode, String node, String Value, String attribute, String attributeValue) {
        try {

            StringReader stringReader = new StringReader(xmlString);
            InputSource inputSource = new InputSource(stringReader);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputSource);
            Element docElement = doc.createElement(node);
            Text payeeText = doc.createTextNode(Value);
            if (StringUtils.isNotBlank(attribute)) {
                Attr attr = doc.createAttribute(attribute);
                attr.setValue(attributeValue);
                docElement.setAttributeNode(attr);
            }
            docElement.appendChild(payeeText);
            NodeList parentNodeList = doc.getElementsByTagName(parentNode);
            for (int i = 0; i < parentNodeList.getLength(); i++) {
                parentNodeList.item(i).appendChild(docElement);
            }
            return convertXmlDocumentToString(doc);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return xmlString;
    }

    public static String convertXmlStringWithNoEscape(String originString) {
        return originString.replaceAll("\n", "").replaceAll("\\s{2,}", "");
    }

    public static String convertXmlDocumentToString(Document document) {
        String result = null;

        if (document != null) {
            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            try {
                javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, CHAR_SET);
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                // text
                transformer.setOutputProperty(
                        "{http://xml.apache.org/xslt}indent-amount", "4");
                transformer.transform(new DOMSource(document.getDocumentElement()),
                        streamResult);
            } catch (Exception e) {
                System.err.println("XML.toString(Document): " + e);
            }
            result = streamResult.getWriter().toString();
            try {
                stringWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
