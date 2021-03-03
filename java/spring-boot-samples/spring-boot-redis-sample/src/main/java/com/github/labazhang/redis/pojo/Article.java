package com.github.labazhang.redis.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public class Article {
    @Id
    private Integer id;
    private String title;
    private String content;
}