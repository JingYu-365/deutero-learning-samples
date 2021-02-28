package com.github.labazhang.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    @RequestMapping("/hello/{id}")
    public String hello(@PathVariable("id") Integer id, ModelMap modelMap) {
        System.out.println(id);
        modelMap.put("userId", id);
        return "success";
    }
}