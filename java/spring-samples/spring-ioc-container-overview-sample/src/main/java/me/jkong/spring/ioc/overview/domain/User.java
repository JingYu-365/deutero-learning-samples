package me.jkong.spring.ioc.overview.domain;

/**
 * @author JKong
 * @version v1.0
 * @description 用户类
 * @date 2020-03-14 23:06.
 */
public class User {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static User createUser() {
        User user = new User();
        user.setName("JKong234");
        user.setAge(25);
        return user;
    }
}