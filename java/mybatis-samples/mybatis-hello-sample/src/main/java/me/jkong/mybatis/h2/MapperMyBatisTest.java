package me.jkong.mybatis.h2;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 通过Mapper获取statementId，再执行SQL，获取查询结果
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/10/23 10:39.
 */
public class MapperMyBatisTest {
    public static void main(String[] args) {
        InputStream in = null;
        SqlSession session = null;
        try {
            //1.读取配置文件
            in = Resources.getResourceAsStream("mybatis.xml");
            //2.创建SqlSessionFactory工厂
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
            //3.使用工厂生产SqlSession对象
            session = sqlSessionFactory.openSession();
            //4.执行Sql语句
            UserMapper userMapper = session.getMapper(UserMapper.class);
            //5. 打印结果
            System.out.println(userMapper.findUserById(1L));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            if (session != null) {
                session.close();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignore) {
                    // ignore
                }
            }
        }
    }
}
