package me.jkong.object.storage.dao.test;

import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import me.jkong.object.storage.dao.DataSourceConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 基类测试类
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/22 14:18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Import(DataSourceConfig.class)
@PropertySource("classpath:application.properties")
@ComponentScan("me.jkong.object.storage.*")
@MapperScan("me.jkong.object.storage.*")
class BaseTest {

}