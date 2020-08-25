package me.jkong.flink.datasink.custom;

import org.junit.jupiter.api.Test;

/**
 * 测试自定义sink
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/25 07:22.
 */
public class CustomSinkToMysqlTest {

    @Test
    public void testCustomSinkToMysql() {
        // TODO: 2020/8/25 通过 flink 将数据装载到MySQL中

    }

    /**
     * <pre>
     *     -- ----------------------------
     *     -- Table structure for userinfo
     *     -- ----------------------------
     *     DROP TABLE IF EXISTS `userinfo`;
     *     CREATE TABLE `userinfo` (
     *       `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
     *       `name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
     *       `password` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
     *       `age` int(10) DEFAULT NULL,
     *       PRIMARY KEY (`id`)
     *     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     * </pre>
     *
     * @return CustomSinkToMysql
     */
    private CustomSinkToMysql getSinkToMysql() {
        String url = "jdbc:mysql://localhost:3306/flink-db?useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "123456";
        String sql = "insert into userinfo (id, name, password, age) values (?, ?, ?, ?)";

        return CustomSinkToMysql.CustomSinkToMysqlBuilder.getInstance()
                .url(url)
                .username(username)
                .password(password)
                .insertSql(sql)
                .build();
    }

}