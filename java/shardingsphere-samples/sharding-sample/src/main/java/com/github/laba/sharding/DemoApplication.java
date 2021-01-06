package com.github.laba.sharding;

import java.io.IOException;
import java.sql.SQLException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.github.laba.sharding.entity.EncryptUser;
import com.github.laba.sharding.hint.HintService;
import com.github.laba.sharding.service.EncryptUserService;
import com.github.laba.sharding.service.HealthLevelService;
import com.github.laba.sharding.service.HealthRecordService;
import com.github.laba.sharding.service.UserService;

@ComponentScan("com.github.laba.sharding")
@MapperScan(basePackages = "com.github.laba.sharding.repository")
@SpringBootApplication
public class DemoApplication {
	
    public static void main(final String[] args) throws SQLException, IOException {
        try (ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args)) {
        	UserService userService = applicationContext.getBean(UserService.class);
        	userService.processUsers();        	
        	userService.getUsers(); 
        	
//        	HealthLevelService healthLevelService = applicationContext.getBean(HealthLevelService.class);
//        	healthLevelService.processLevels();
//        	
//        	HealthRecordService healthRecordService = applicationContext.getBean(HealthRecordService.class);
//        	healthRecordService.processHealthRecords(); 
        	
//        	HintService hintService = applicationContext.getBean(HintService.class);
//        	hintService.processWithHintValueForShardingDatabases();
//        	hintService.processWithHintValueForShardingDatabasesAndTables();
//        	hintService.processWithHintValueMaster();
        }
    }
}
