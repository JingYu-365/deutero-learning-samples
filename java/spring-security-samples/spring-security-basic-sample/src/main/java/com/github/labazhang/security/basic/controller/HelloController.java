package com.github.labazhang.security.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Hello Spring Security
 *
 * @author laba zhang
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello Page!";
    }

    @GetMapping("/hello/auth")
    @ResponseBody
    public String auth() {
        return "Auth Page!";
    }

    @GetMapping("/hello/noauth")
    @ResponseBody
    public String noAuth() {
        return "No Auth Page!";
    }

    @GetMapping("/hello/role")
    @ResponseBody
    public String role() {
        return "Role Page!";
    }

    @GetMapping("/hello/norole")
    @ResponseBody
    public String noRole() {
        return "No Role Page!";
    }

    @GetMapping("/index")
    @ResponseBody
    public String index() {
        return "Index Page!";
    }

//    @GetMapping("login")
//    public String login() {
//        return "login";
//    }
}
