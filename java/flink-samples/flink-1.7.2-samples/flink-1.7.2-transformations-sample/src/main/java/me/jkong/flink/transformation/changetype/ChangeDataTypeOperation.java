package me.jkong.flink.transformation.changetype;

import me.jkong.flink.transformation.BaseFlinkOperation;
import me.jkong.flink.transformation.udf.StringToDate;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.functions.ScalarFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * table 数据类型转换
 * <p>
 * - 字段定义别名
 * - 字段修改类型
 * -
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/26 11:34.
 */
public class ChangeDataTypeOperation extends BaseFlinkOperation {

    public static void main(String[] args) throws Exception {
        new ChangeDataTypeOperation().executeFilterOperation();
    }


    @Override
    protected Table initTableOperation(Table table) {

//        "id, name, age, gender, desc"

        TableSchema schema = table.getSchema();

        String[] fieldNames = schema.getFieldNames();
        TypeInformation<?>[] fieldTypes = schema.getFieldTypes();
        Map<String, String> fieldInfoMap = new HashMap<>(16);
        for (int i = 0; i < fieldTypes.length; i++) {
            fieldInfoMap.put(fieldNames[i], fieldTypes[i].toString());
        }

        Optional<String> oldFields = Arrays.stream(fieldNames).reduce((s, s2) -> s + "," + s2);

        String castField = null;
        if (oldFields.isPresent()) {
            castField = oldFields.get()
                    .replace("id", "((id).cast(double) as id).round(3) as id")
                    .replace("birth", "(birth).stringToDate('yyyy-MM-dd HH:mm:ss') as birth")
                    .replace("name", "((name).cast(String) as name).substring(3) as name");
        }
        if (StringUtils.isBlank(castField)) {
            return table;
        }
        System.out.println(castField);
        table = table.select(castField);
        return table;
    }


    @Override
    protected void registerFunction(BatchTableEnvironment tEnv) {
        tEnv.registerFunction("stringToDate", new StringToDate());
    }
}
