package com.github.labazhang.jpa.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "t_article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
}