package me.jkong.elastic.job.lite;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.util.List;
import java.util.Map;

public class BackupJob implements SimpleJob {
    // 定时任务每执⾏⼀次都会执⾏如下的逻辑
    @Override
    public void execute(ShardingContext shardingContext) {
        /*
            从resume数据表查找1条未归档的数据，将其归档到 resume_bak 表，并更新状态为已归档（不删除原数据）
        */
        // 查询出⼀条数据
        String selectSql = "select * from resume where state = '未归档' limit 1 ";
        List<Map<String, Object>> list = JdbcUtil.executeQuery(selectSql);
        if (list == null || list.size() == 0) {
            return;
        }
        Map<String, Object> stringObjectMap = list.get(0);
        long id = (long) stringObjectMap.get("id");
        String name = (String) stringObjectMap.get("name");
        String education = (String) stringObjectMap.get("education");
        // 打印出这条记录
        System.out.println("======>>>id： " + id + " name： " + name + " education： " + education);
        // 更改状态
        String updateSql = "update resume set state='已归档' where id =?";
        JdbcUtil.executeUpdate(updateSql, id);
        // 归档这条记录
        String insertSql = "insert into resume_bak select * from resume where id =?";
        JdbcUtil.executeUpdate(insertSql, id);
    }
}