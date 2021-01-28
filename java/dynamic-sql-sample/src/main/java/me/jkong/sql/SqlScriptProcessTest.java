package me.jkong.sql;

import me.jkong.sql.script.DynamicSqlProcessor;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 动态SQL测试
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/10/23 19:12.
 */
public class SqlScriptProcessTest {


//    public static void main(String[] args) throws JDOMException, IOException {
//
////        String xml = "<select> " +
////                "   select id, name, age, hobby from user where 1 = 1 " +
////                "     <if test = \"id != null\" >" +
////                "       AND id = #{id}" +
////                "     </if>" +
////                " and hobby = 'football' " +
////                "     <if test = \"name != null\">" +
////                "       AND name like #{name}" +
////                "     </if>" +
////                "</select>";
//
//        String xml = "<select>" +
//                "  select * from jzsz_pxzt.train_organ where 1=1" +
//                "  <if test = \"region != null\">" +
//                "    and region = #{region}" +
//                "  </if>" +
//                "  <if test = \"name != null\">" +
//                "    and name like concat(concat('%',#{name}),'%')" +
//                "  </if>" +
//                "  <if test = \"status != null\">" +
//                "   and status = #{status}" +
//                "  </if>" +
//                "  <if test = \"start_time != null\">" +
//                "    and created_time &gt;= #{start_time}" +
//                "  </if>" +
//                "  <if test = \"end_time != null\">" +
//                "    and created_time &gt; #{end_time}" +
//                "   </if>" +
//                "</select>";
//
//        Map<String, Object> param = new HashMap<>();
//        param.put("region", 123);
//        param.put("name", "xiao ming");
//        param.put("status", 24);
//        param.put("start_time", "football");
//        param.put("end_time", "football");
//
//        String sql = DynamicSqlProcessor.buildSql(xml, param);
//        System.out.println(sql);
//        // 替换占位符，并将参数排序
//        // 将占位符#{xxx}替换为？，并按照占位符出现的顺序
//        Map<String, Object> resultMap = DynamicSqlProcessor.processSqlPlaceHolderAndOrderParam(sql, param);
//        System.out.println(resultMap.remove(DynamicSqlProcessor.PROCESSED_STR));
//        System.out.println(resultMap);
//    }
}