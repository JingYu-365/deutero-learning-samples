package me.jkong.spring.loader;

import org.springframework.core.io.*;

/**
 * @author JKong
 * @version v1.0
 * @description ResourceLoader 测试
 * @date 2020/3/18 12:35.
 */
public class ResourceLoaderDemo {
    public static void main(String[] args) {
        ResourceLoader loader = new DefaultResourceLoader();

        Resource resource = loader.getResource("http://www.baidu.com");
        System.out.println(resource instanceof UrlResource);

        resource = loader.getResource("classpath:jkong.txt");
        System.out.println(resource instanceof ClassPathResource);

        resource = loader.getResource("jkong.txt");
        System.out.println(resource instanceof ClassPathResource);
    }
}