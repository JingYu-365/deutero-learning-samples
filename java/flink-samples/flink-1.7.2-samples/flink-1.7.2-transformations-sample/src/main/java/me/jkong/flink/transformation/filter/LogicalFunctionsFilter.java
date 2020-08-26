package me.jkong.flink.transformation.filter;

import me.jkong.flink.transformation.BaseFlinkOperation;
import org.apache.flink.table.api.Table;

/**
 * 逻辑操作符过滤
 *
 * <url>https://ci.apache.org/projects/flink/flink-docs-release-1.7/dev/table/functions.html#logical-functions</url>
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/25 17:57.
 */
public class LogicalFunctionsFilter extends BaseFlinkOperation {


    public static void main(String[] args) throws Exception {
        new LogicalFunctionsFilter().executeFilterOperation();
    }

    /**
     * 11L, "xiao ming",   121, true,  "备注1"
     * 12L, "xiao hong",   122, false, "备注2"
     * 13L, "hua hua",     123, true,  "备注3"
     * 14L, "xiao li zi",  124, false, "备注4"
     * 15L, "da gou zi",   125, true,  "备注5"
     *
     * @param table 表
     * @return table
     */
    @Override
    protected Table initTableOperation(Table table) {
        return table
                .filter("!gender && age != 122");
    }
}