package com.github.labazhang.redis.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@RedisHash("h_comment")
public class Comment {
    @Id
    private Integer id;
    @Indexed
    private String content;
    @Indexed
    private String author;
    private Integer aId;
}