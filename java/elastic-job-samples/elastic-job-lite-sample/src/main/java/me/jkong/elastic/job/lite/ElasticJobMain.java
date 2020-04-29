package me.jkong.elastic.job.lite;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class ElasticJobMain {
    public static void main(String[] args) {
        // 配置注册中⼼zookeeper， zookeeper协调调度，不能让任务重复执⾏，通过命名空间分类管理任务，对应到zookeeper的⽬录
        ZookeeperConfiguration zookeeperConfiguration =
                new ZookeeperConfiguration("127.0.0.1:2181", "data-archive-job");
        CoordinatorRegistryCenter coordinatorRegistryCenter =
                new ZookeeperRegistryCenter(zookeeperConfiguration);
        coordinatorRegistryCenter.init();

        // 配置任务
        JobCoreConfiguration jobCoreConfiguration =
                JobCoreConfiguration.newBuilder("archive-job", "*/2 * * * * ? ", 1).build();
        SimpleJobConfiguration simpleJobConfiguration =
                new SimpleJobConfiguration(jobCoreConfiguration, BackupJob.class.getName());

        // 启动任务
        new JobScheduler(coordinatorRegistryCenter,
                LiteJobConfiguration.newBuilder(simpleJobConfiguration).build()).init();
    }
}