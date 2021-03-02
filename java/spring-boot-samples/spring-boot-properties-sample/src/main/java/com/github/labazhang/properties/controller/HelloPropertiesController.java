package com.github.labazhang.properties.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test for Properties
 *
 * @author laba zhang
 */
@RestController
public class HelloPropertiesController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Properties!";
    }
}
