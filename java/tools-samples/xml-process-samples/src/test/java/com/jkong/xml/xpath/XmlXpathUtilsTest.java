package com.jkong.xml.xpath;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

/**
 * @author JKong
 * @version v1.0
 * @description xml xpath utils test class
 * @date 2019/8/8 14:19.
 */
public class XmlXpathUtilsTest {

    private static String str = "";

    @BeforeAll
    public static void setUp() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>")
                .append("<classes>")
                .append("   <teachers>")
                .append("       <teacher>")
                .append("           <name>JKong</name>")
                .append("           <age>18</age>")
                .append("       </teacher>")
                .append("   </teachers>")
                .append("   <students>")
                .append("       <student>")
                .append("           <name>张三</name>")
                .append("           <age>23</age>")
                .append("           <scores>93</scores>")
                .append("       </student>")
                .append("       <student>")
                .append("           <name>李四</name>")
                .append("           <age>24</age>")
                .append("           <scores>94</scores>")
                .append("       </student>")
                .append("       <student>")
                .append("           <name>王五</name>")
                .append("           <age>25</age>")
                .append("           <scores>95</scores>")
                .append("       </student>")
                .append("    </students>")
                .append("</classes>");
        str = sb.toString();
    }


    @Test
    @DisplayName("测试获取XPath实例")
    public void testGetXpathInstance() {
        XPath xpathInstance = XmlXpathUtils.xpath();
        Assertions.assertNotNull(xpathInstance);
    }


    @Test
    @DisplayName("测试通过xpath获取Integer型数据")
    public void testAsInteger() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        Document document = XmlXpathUtils.document(str);
        XPath xpath = XmlXpathUtils.xpath();
        Integer age = XmlXpathUtils.asInteger("/classes/students/student[age>24]/age", document, xpath);
        Assertions.assertEquals(age, 25);
    }

    @Test
    @DisplayName("测试通过默认xpath获取Integer型数据")
    void testAsIntegerByDefaultXPath() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        Document document = XmlXpathUtils.document(str);
        Integer age = XmlXpathUtils.asInteger("/classes/students/student[age>24]/age", document);
        Assertions.assertEquals(age, 25);
    }

    @Test
    @DisplayName("测试通过默认xpath获取String型数据")
    void testAsStringByDefaultXPath() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException, DocumentException {
        SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(str);
        Element rootElement = document.getRootElement();
        Element teachers = rootElement.element("teachers");
        System.out.println(teachers.asXML());
    }




}