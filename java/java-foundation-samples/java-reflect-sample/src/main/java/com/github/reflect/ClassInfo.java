package com.github.reflect;

/**
 * 获取Class信息
 * 在运行期间，一个类只有一个Class对象与之相对应
 *
 * @author Laba Zhang
 */
public class ClassInfo {
    public static void main(String[] args) throws ClassNotFoundException {
        // 1. 通过对象的 getClass 方式获取 Class 对象
        ClassInfo classInfo = new ClassInfo();
        Class<? extends ClassInfo> infoClass = classInfo.getClass();
        System.out.println(infoClass.getName());

        // 通过类的静态属性 class 属性获取
        Class<ClassInfo> infoClass1 = ClassInfo.class;
        System.out.println(infoClass1.getName());
        System.out.println(infoClass.equals(infoClass1));

        // 通过 Class#froName() 方式获取 Class 对象
        Class<?> aClass = Class.forName("com.github.reflect.ClassInfo");
        System.out.println(aClass.getName());
        System.out.println(infoClass1.equals(aClass));
    }
}
