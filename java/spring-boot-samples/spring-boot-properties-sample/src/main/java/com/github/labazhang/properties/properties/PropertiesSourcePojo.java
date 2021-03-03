package com.github.labazhang.properties.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * test for Properties Source
 *
 * @author laba zhang
 */
@Data
@Component
@PropertySource(value = "classpath:test.properties")
@ConfigurationProperties(prefix = "test")
public class PropertiesSourcePojo {
    private Integer id;
    private String name;
}
