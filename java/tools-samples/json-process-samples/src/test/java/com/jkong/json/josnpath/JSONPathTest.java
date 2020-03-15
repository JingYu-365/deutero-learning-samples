package com.jkong.json.josnpath;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JKong
 * @version v1.0
 * @description json path test
 * @date 2019/8/9 12:47.
 */
@Slf4j
public class JSONPathTest {

    private static String json = "{\"className\": \"计算机1班\",\"classNo\": \"00011101\",\"excellent\": 9.5,\"students\": [" +
            "{\"age\": 23,\"gradePoint\": 9.8,\"name\": \"zhangSan\",\"stuId\": \"a1\",\"stuNo\": \"123\"}," +
            "{\"age\": 24,\"gradePoint\": 9.4,\"name\": \"liSi\",\"stuId\": \"a2\",\"stuNo\": \"124\"}," +
            "{\"age\": 25,\"gradePoint\": 9.9,\"name\": \"wangWu\",\"stuId\": \"a3\",\"stuNo\": \"125\",\"repeater\":true}," +
            "{\"age\": 26,\"gradePoint\": 9.6,\"name\": \"zhaoLiu\",\"stuId\": \"a4\",\"stuNo\": \"126\"}]," +
            "\"teacher\": {\"age\": 30,\"name\": \"JKong\",\"teacherId\": \"$1\"}}";

    public static void main(String[] args) {
        // todo 测试
        System.out.println(JsonPath.parse(json).read("$.teacher",String.class));
    }
    @Test
    @DisplayName("获取根节点元素 【$】")
    void getRoot() {
        Map<String, Object> all = JsonPath.parse(json).read("$");
        if (log.isInfoEnabled()) {
            log.info(all.toString());
        }

        Assertions.assertNotNull(all);
        Assertions.assertEquals("计算机1班", all.get("className"));
    }


    @Test
    @DisplayName("所有学生名称 【$.students[*].name】")
    void testGetAllStudentName() {
        List<String> names = JsonPath.parse(json).read("$.students[*].name");
        if (log.isInfoEnabled()) {
            log.info(names.toString());
        }

        Assertions.assertEquals(4, names.size());
    }


    @Test
    @DisplayName("所有绩点【$..gradePoint】")
    void testGetAllGradePoint() {
        List<Double> gradePoints = JsonPath.parse(json).read("$..gradePoint");
        if (log.isInfoEnabled()) {
            log.info(gradePoints.toString());
        }

        Assertions.assertEquals(4, gradePoints.size());
        Assertions.assertEquals(9.9, gradePoints.get(2));
    }

    /**
     * todo：不足之处：
     * 1. 使用推荐的 TypeRef，无法把 List<LinkedHashMap<String, Object>> 转为 List<Student>
     */
    @Test
    @DisplayName("所有学生信息 【$.students.*】")
    void testGetAllStudentInfos() {
        List<LinkedHashMap<String, Object>> students = JsonPath.parse(json).read("$.students.*");
        if (log.isInfoEnabled()) {
            log.info(students.toString());
        }

        Assertions.assertEquals(4, students.size());
        Assertions.assertEquals(9.9, students.get(2).get("gradePoint"));
    }

    @Test
    @DisplayName("所有名称（包括老师及学生）【$..name】")
    void testGetAllNames() {
        List<String> names = JsonPath.parse(json).read("$..name");
        if (log.isInfoEnabled()) {
            log.info("names = {}", names);
        }

        // 按照字符串中name出现的先后顺序，而非嵌套层次
        Assertions.assertEquals(5, names.size());
        Assertions.assertEquals("JKong", names.get(4));
    }

    @Test
    @DisplayName("第三个学生【$..students[2]】")
    void getThirdStudent() {
        List<LinkedHashMap<String, Object>> students = JsonPath.parse(json).read("$..students[2]");
        if (log.isInfoEnabled()) {
            log.info("student = {}", students);
        }

        Assertions.assertNotNull(students);
        Assertions.assertEquals("wangWu", students.get(0).get("name"));
    }


    @Test
    @DisplayName("倒数第二个学生 【$..students[-2]】")
    void testLastSecondStudent() {
        // 倒着数是从下标1开始计算的
        List<LinkedHashMap<String, Object>> students = JsonPath.parse(json).read("$..students[-2]");
        if (log.isInfoEnabled()) {
            log.info("student = {}", students);
        }

        Assertions.assertNotNull(students);
        Assertions.assertEquals("wangWu", students.get(0).get("name"));
    }

    @Test
    @DisplayName("倒数第二个学生 【$..students[0,1]】")
    void testOneAndOneStudents() {
        List<LinkedHashMap<String, Object>> students = JsonPath.parse(json).read("$..students[0,1]");
        if (log.isInfoEnabled()) {
            log.info("student = {}", students);
        }

        Assertions.assertNotNull(students);
        Assertions.assertEquals(2, students.size());
        Assertions.assertEquals("zhangSan", students.get(0).get("name"));
    }

    @Test
    @DisplayName("从第一个（包括）到第三个（不包括）学生 【$..students[:2]】")
    void testToStudents() {
        List<LinkedHashMap<String, Object>> students = JsonPath.parse(json).read("$..students[:2]");
        if (log.isInfoEnabled()) {
            log.info("student = {}", students);
        }

        Assertions.assertNotNull(students);
        Assertions.assertEquals(2, students.size());
        Assertions.assertEquals("zhangSan", students.get(0).get("name"));
        Assertions.assertEquals("liSi", students.get(1).get("name"));
    }

    @Test
    @DisplayName("从第二个（包括）到第三个（不包括）学生 【$..students[:2]】")
    void testFromToStudents() {
        List<LinkedHashMap<String, Object>> students = JsonPath.parse(json).read("$..students[1:2]");
        if (log.isInfoEnabled()) {
            log.info("student = {}", students);
        }

        Assertions.assertNotNull(students);
        Assertions.assertEquals(1, students.size());
        Assertions.assertEquals("liSi", students.get(0).get("name"));
    }


    @Test
    @DisplayName("最后两个学生 【$..students[-2:]】")
    void testLastTwoStudents() {
        List<LinkedHashMap<String, Object>> students = JsonPath.parse(json).read("$..students[-2:]");
        if (log.isInfoEnabled()) {
            log.info("student = {}", students);
        }

        Assertions.assertNotNull(students);
        Assertions.assertEquals(2, students.size());
        Assertions.assertEquals("wangWu", students.get(0).get("name"));
    }


    @Test
    @DisplayName("第三个及其以后的学生 【$..students[2:]】")
    void testFromThirdStudents() {
        List<LinkedHashMap<String, Object>> students = JsonPath.parse(json).read("$..students[2:]");
        if (log.isInfoEnabled()) {
            log.info("student = {}", students);
        }

        Assertions.assertNotNull(students);
        Assertions.assertEquals(2, students.size());
        Assertions.assertEquals("wangWu", students.get(0).get("name"));
    }


    @Test
    @DisplayName("所有带有repeater属性的学生 【$..students[?(@.repeater)]】")
    void testWithAttributesStudents() {
        List<LinkedHashMap<String, Object>> students = JsonPath.parse(json).read("$..students[?(@.repeater)]");
        if (log.isInfoEnabled()) {
            log.info("student = {}", students);
        }

        Assertions.assertNotNull(students);
        Assertions.assertEquals(1, students.size());
        Assertions.assertEquals("wangWu", students.get(0).get("name"));
    }


    @Test
    @DisplayName("年龄大于24的学生 【$.students[?(@.age > 24)]】")
    void testWithAgeGreaterStudents() {
        List<LinkedHashMap<String, Object>> students = JsonPath.parse(json).read("$.students[?(@.age > 24)]");
        if (log.isInfoEnabled()) {
            log.info("student = {}", students);
        }

        Assertions.assertNotNull(students);
        Assertions.assertEquals(2, students.size());
        Assertions.assertEquals("wangWu", students.get(0).get("name"));
        Assertions.assertEquals("zhaoLiu", students.get(1).get("name"));
    }


    @Test
    @DisplayName("没有达到优秀绩点的学生 【$..students[?(@.gradePoint <= $['excellent'])]】")
    void testWithGradePointComparedStudents() {
        List<LinkedHashMap<String, Object>> students = JsonPath.parse(json).read("$..students[?(@.gradePoint <= $['excellent'])]");
        if (log.isInfoEnabled()) {
            log.info("student = {}", students);
        }

        Assertions.assertNotNull(students);
        Assertions.assertEquals(1, students.size());
        Assertions.assertEquals("liSi", students.get(0).get("name"));
    }


    @Test
    @DisplayName("名字匹配的学生（忽略大小写） 【$..students[?(@.name =~/.*San/i)]】")
    void testWithNameRegexStudents() {
        List<LinkedHashMap<String, Object>> students = JsonPath.parse(json).read("$..students[?(@.name =~ /.*San/i)]");
        if (log.isInfoEnabled()) {
            log.info("student = {}", students);
        }

        Assertions.assertNotNull(students);
        Assertions.assertEquals(1, students.size());
        Assertions.assertEquals("zhangSan", students.get(0).get("name"));
    }


    @Test
    @DisplayName("学生的数量 【$..students.length()】")
    void testLengthOfStudents() {
        List<Integer> length = JsonPath.parse(json).read("$..students.length()");
        if (log.isInfoEnabled()) {
            log.info("length = {}", length.get(0));
        }

        Assertions.assertNotNull(length);
        Assertions.assertEquals(4, length.get(0));
    }

}