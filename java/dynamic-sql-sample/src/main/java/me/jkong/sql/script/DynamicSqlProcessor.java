package me.jkong.sql.script;

import me.jkong.sql.script.ongl.ExpressionEvaluator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 动态SQL脚本处理工具
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/10/24 10:48.
 */
public class DynamicSqlProcessor {

    private static ExpressionEvaluator evaluator = new ExpressionEvaluator();
    private static final String SELECT_LABEL = "select";
    private static final String IF_LABEL = "if";
    private static final String TEST_ATTR = "test";
    public static final String PROCESSED_STR = "processed_str";

    private static final Pattern PLACE_HOLDER_PATTERN = Pattern.compile("(#\\{\\w+})");

    /**
     * 处理占位符并将处理后的字符串返回
     * <pre>
     *     str:
     *      <select>
     *         select id, name, age, hobby from user where 1 = 1
     *           <if test = "id != null" >
     *             AND id = #{id}
     *           </if>
     *           <if test = "name != null">
     *             AND name like #{name}
     *           </if>
     *           <if test = "age != null" >
     *             AND age IN
     *             <foreach open="(" close=")" separator=",">
     *                #{age}
     *             </foreach>
     *           </if>
     *      </select>
     *
     *      param:
     *      {
     *          "id" = 123,
     *          "name" = "kong",
     *          "age" = "'23','34'"
     *      }
     * </pre>
     *
     * @param str      SQL脚本
     * @param paramMap SQL中的参数
     * @return 处理后的SQL及参数
     * key = -1 时，value = SQL
     * key > 0 时，value = 参数
     */
    public static Map<String, Object> processSqlPlaceHolderAndOrderParam(String str, Map<String, Object> paramMap) {
        Matcher matcher = PLACE_HOLDER_PATTERN.matcher(str);
        Map<String, Object> resultMap = new LinkedHashMap<>();
        while (matcher.find()) {
            // 将占位符替换为 ？
            str = str.replace(matcher.group(), " ? ");
            // 获取参数名
            String param = matcher.group().substring(2, matcher.group().length() - 1);
            resultMap.put(param, paramMap.get(param));
        }
        resultMap.put(PROCESSED_STR, str);
        return resultMap;
    }


    public static String buildSql(String xml, Map<String, Object> param) throws JDOMException, IOException {
        Document document = new SAXBuilder().build(new StringReader(xml));
        if (!SELECT_LABEL.equals(document.getRootElement().getName())) {
            throw new IllegalArgumentException("sql script is illegal, must start with <select>. " +
                    "\nerror xml: \n " + xml);
        }
        String sql = processIfEle(document.getRootElement(), param);
        if (null == sql || "".equals(sql)) {
            throw new IllegalArgumentException("process sql script end, but processed sql is empty." +
                    "\nerror xml: \n " + xml + "\nprocessed sql：" + sql);
        }
        return sql;
    }

    /**
     * 处理 if 标签
     *
     * @param selectEle select element
     * @param param     参数
     * @return 处理后的SQL片段
     */
    private static String processIfEle(Element selectEle, Map<String, Object> param) {
        List<Element> childElements = selectEle.getChildren();
        Iterator<Element> iterator = childElements.iterator();
        List<Integer> removedChild = new ArrayList<>();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            if (IF_LABEL.equals(element.getName())) {
                // 计算表达式是否为true
                if (!evaluator.evaluateBoolean(element.getAttributeValue(TEST_ATTR), param)) {
                    int i = selectEle.indexOf(element);
                    removedChild.add(i);
                    continue;
                }

                // process foreach label in if label
                List<Element> ifChildren = element.getChildren();
                for (Element ifChild : ifChildren) {
                    if (FOREACH_LABEL.equals(ifChild.getName())) {

                    }

                }

            }
        }
        for (int i = removedChild.size() - 1; i >= 0; i--) {
            selectEle.removeContent(removedChild.get(i));
        }
        return selectEle.getValue();
    }


    private static final String FOREACH_LABEL = "if";
    private static final String OPEN_LABEL = "if";
    private static final String CLOSE_LABEL = "if";
    private static final String OPERATOR_LABEL = "if";
    /**
     * 处理 foreach 标签
     *
     * @param foreachEle foreach element
     * @param param 参数
     * @return 处理后的SQL片段
     */
    private static String processForeachEle(Element foreachEle, Map<String, Object> param) {


        return null;
    }
}