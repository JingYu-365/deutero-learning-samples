package me.jkong.flink.transformation.select;

import me.jkong.flink.transformation.BaseFlinkOperation;
import org.apache.flink.table.api.Table;

/**
 * TODO
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/25 18:16.
 */
public class SelectUuidOperation extends BaseFlinkOperation {

    public static void main(String[] args) throws Exception {
        new SelectUuidOperation().executeFilterOperation();
    }

    @Override
    protected Table initTableOperation(Table table) {
        return table.select("id, name, age, gender, uuid() as desc");
    }
}