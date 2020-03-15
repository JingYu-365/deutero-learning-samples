package com.jkong.xml.xpath;


import org.dom4j.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author JKong
 * @version v1.0
 * @description TODO
 * @date 2020/2/20 8:46 下午.
 */
public class JaxenTest {

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
    public void testJaxen() {
        try {
            String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<class>" +
                    "    <teacher>" +
                    "        <name>哈哈老师</name>" +
                    "        <sex>女</sex>" +
                    "        <id>110</id>" +
                    "    </teacher>" +
                    "    <students>" +
                    "        <student score=\"23\">" +
                    "            <name>\"张三\"</name>" +
                    "            <sex>女</sex>" +
                    "            <id>1003</id>" +
                    "        </student>" +
                    "        <student score=\"24\">" +
                    "            <name>\"李四\"</name>" +
                    "            <sex>男</sex>" +
                    "            <id>1004</id>" +
                    "        </student>" +
                    "    </students>" +
                    "</class>";
            Document doc = DocumentHelper.parseText(str);
            String xpath = "/classes/students/student[age>=24]";
            List<Node> list = doc.selectNodes(xpath);
            int i = 0;
            for (Node node : list) {
                System.out.println(++i);
                System.out.println(node.asXML());
            }

//            Node node = doc.selectSingleNode(xpath);
//            System.out.println(node.asXML());

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}