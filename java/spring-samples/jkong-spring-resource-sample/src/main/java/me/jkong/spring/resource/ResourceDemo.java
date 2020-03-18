package me.jkong.spring.resource;

import org.springframework.core.io.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author JKong
 * @version v1.0
 * @description 通过classPathResource获取资源
 * @date 2020/3/18 12:13.
 */
public class ResourceDemo {
    public static void main(String[] args) throws IOException {
        Resource resource = new ClassPathResource("jkong.txt");
        deploy(resource);

        System.out.println("\n=============================================\n");
        resource = new FileSystemResource("D:\\jkong.txt");
        deploy(resource);

        System.out.println("\n=============================================\n");
        resource = new UrlResource("https://docs.spring.io/spring/docs/5.2.2.RELEASE/spring-framework-reference/pdf/core.pdf");
        deploy(resource);

        System.out.println("\n=============================================\n");
        resource = new ByteArrayResource("Hello".getBytes());
        System.out.println(resource.getInputStream());
    }

    private static void deploy(Resource resource) throws IOException {
        // 判断 文件是否存在
        if (resource.exists()) {
            System.out.println("文件存在");
        }

        // 判断 资源文件是否可读
        if (resource.isReadable()) {
            System.out.println("文件可读");
        }

        // 判断 底层资源是否已经打开
        if (resource.isOpen()) {
            System.out.println("资源文件已打开");
        }
        // 获取资源所在的URL
        System.out.println("url: " + resource.getURL());
        // 获取资源所在的URI
        System.out.println("uri: " + resource.getURI());
        // 返回当前资源对应的File。
        if (!(resource instanceof UrlResource)) {
            File file = resource.getFile();
            System.out.println("file: " + file);
        }
        // 输出内容长度
        System.out.println("contentLength: " + resource.contentLength());
        // 返回当前Resource代表的底层资源的最后修改时间。
        System.out.println("modifyTime: " + resource.lastModified());
        // 根据资源的相对路径创建新资源。[默认不支持创建相对路径资源]
        resource.createRelative("MyFile");
        // 获取资源文件名
        System.out.println("fileName: " + resource.getFilename());
        // 获取文件资源描述
        System.out.println("fileDescription: " + resource.getDescription());

        //获取当前资源代表的输入流
        if (resource.isReadable()) {
            InputStream is = resource.getInputStream();
            System.out.println(is);
            is.close();
        }
    }
}