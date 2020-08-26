package me.jkong.flink.transformation.filter;

import me.jkong.flink.transformation.BaseFlinkOperation;
import org.apache.flink.table.api.Table;

/**
 * 比较过滤操作
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/25 13:34.
 */
public class ComparisonFunctionFilter extends BaseFlinkOperation {

    public static void main(String[] args) throws Exception {
        new ComparisonFunctionFilter().executeFilterOperation();
    }


    @Override
    protected Table initTableOperation(Table table) {
        return table
//                .filter("(id >= 13 && age < 124) || (1d < 13 && age = 122 && gender = false)")
                .filter("id.between(11, 13) && age.in(123,124)")
                .select("id, name, age, gender, desc");
    }
}