package com.github.reflect;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author laba zhang
 */
@Data
@Slf4j(topic = "ReflectClass")
public class ReflectClass {

    public static void main(String[] args) {
        reflectPrivateConstructor();
        reflectPrivateField();
        reflectPrivateMethod();
        reflectNewInstance();
    }

    /**
     * 创建对象
     */
    public static void reflectNewInstance() {
        try {
            Class<?> classBook = Class.forName("com.github.reflect.Book");
            Object objectBook = classBook.newInstance();
            Book book = (Book) objectBook;
            book.setName("Java");
            book.setAuthor("laba zhang");
            log.info("reflectNewInstance book = " + book.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 反射私有的构造方法
     */
    public static void reflectPrivateConstructor() {
        try {
            Class<?> classBook = Class.forName("com.github.reflect.Book");
            Constructor<?> declaredConstructorBook = classBook.getDeclaredConstructor(String.class, String.class);
            declaredConstructorBook.setAccessible(true);
            Object objectBook = declaredConstructorBook.newInstance("JAVA", "laba");
            Book book = (Book) objectBook;
            log.info("reflectPrivateConstructor book = " + book.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 反射私有属性
     */
    public static void reflectPrivateField() {
        try {
            Class<?> classBook = Class.forName("com.github.reflect.Book");
            Object objectBook = classBook.newInstance();
            Field fieldTag = classBook.getDeclaredField("TAG");
            fieldTag.setAccessible(true);
            String tag = (String) fieldTag.get(objectBook);
            log.info("reflectPrivateField tag = " + tag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 反射私有方法
     */
    public static void reflectPrivateMethod() {
        try {
            Class<?> classBook = Class.forName("com.github.reflect.Book");
            Method methodBook = classBook.getDeclaredMethod("declaredMethod", int.class);
            methodBook.setAccessible(true);
            Object objectBook = classBook.newInstance();
            String string = (String) methodBook.invoke(objectBook, 0);

            log.info("reflectPrivateMethod string = " + string);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}