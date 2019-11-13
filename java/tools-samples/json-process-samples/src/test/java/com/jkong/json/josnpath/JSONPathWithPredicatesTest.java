package com.jkong.json.josnpath;

/**
 * @author JKong
 * @version v1.0
 * @description 自定义过滤测试
 * @date 2019/8/9 15:35.
 */
public class JSONPathWithPredicatesTest {


    private static String json = "{\"className\": \"计算机1班\",\"classNo\": \"00011101\",\"excellent\": 9.5,\"students\": [" +
            "{\"age\": 23,\"gradePoint\": 9.8,\"name\": \"zhangSan\",\"stuId\": \"a1\",\"stuNo\": \"123\"}," +
            "{\"age\": 24,\"gradePoint\": 9.4,\"name\": \"liSi\",\"stuId\": \"a2\",\"stuNo\": \"124\"}," +
            "{\"age\": 25,\"gradePoint\": 9.9,\"name\": \"wangWu\",\"stuId\": \"a3\",\"stuNo\": \"125\",\"repeater\":true}," +
            "{\"age\": 26,\"gradePoint\": 9.6,\"name\": \"zhaoLiu\",\"stuId\": \"a4\",\"stuNo\": \"126\"}]," +
            "\"teacher\": {\"age\": 30,\"name\": \"JKong\",\"teacherId\": \"$1\"}}";







}