package me.jkong.blink.batch;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

/**
 * 使用Blink Planner 实现Table批处理
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/25 20:59.
 */
public class BlinkBatchQuery {
    public static void main(String[] args) {
        EnvironmentSettings bbSettings =
                EnvironmentSettings.newInstance().useBlinkPlanner().inBatchMode().build();
        TableEnvironment bbTableEnv = TableEnvironment.create(bbSettings);
    }
}
