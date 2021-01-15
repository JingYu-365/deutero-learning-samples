package com.github.laba.sharding;

import com.github.laba.sharding.service.HealthLevelService;
import com.github.laba.sharding.service.HealthRecordService;
import com.github.laba.sharding.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;

@MapperScan(basePackages = "com.github.laba.sharding.repository")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoApplication {

    public static void main(final String[] args) throws SQLException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
        UserService userService = applicationContext.getBean(UserService.class);
        userService.processUsers();
        userService.getUsers();

        HealthLevelService healthLevelService = applicationContext.getBean(HealthLevelService.class);
        healthLevelService.processLevels();

        HealthRecordService healthRecordService = applicationContext.getBean(HealthRecordService.class);
        healthRecordService.processHealthRecords();

//      HintService hintService = applicationContext.getBean(HintService.class);
//      hintService.processWithHintValueForShardingDatabases();
//      hintService.processWithHintValueForShardingDatabasesAndTables();
//      hintService.processWithHintValueMaster();

    }
}
