package com.xinfago.properties.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Person
 *
 * @author laba zhang
 */
@Data
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private int id;
    private String name;
    private List<String> hobby;
    private String[] family;
    private Map<String, String> map;
    private Pet pet;
}
