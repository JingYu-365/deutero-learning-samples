package com.github.labazhang.security.basic.controller;

import com.github.labazhang.security.basic.entity.Users;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于注解实现权限控制
 *
 * @author laba zhang
 */
@RestController
@RequestMapping("/anno")
public class AnnotationController {


    @GetMapping("/update/role")
    @Secured({"ROLE_sale", "ROLE_manager"}) // 用户具有 sale 或者 manager 角色才可以访问
    public String update() {
        return "Role Update Page!";
    }

    @GetMapping("/update/preUpdate")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String preUpdate() {
        return "PreAuth Update Page!";
    }

    @GetMapping("/update/postUpdate")
    @PostAuthorize("hasAnyAuthority('admin')")
    public String postUpdate() {
        return "PostAuth Update Page!";
    }

    @RequestMapping("getAll")
    @PreAuthorize("hasRole('ROLE_管理员')")
    @PostFilter("filterObject.username == 'admin1'")
    @ResponseBody
    public List<Users> getAllUser() {
        ArrayList<Users> list = new ArrayList<>();
        list.add(new Users(1, "admin1", "6666"));
        list.add(new Users(2, "admin2", "888"));
        return list;
    }

    @PostMapping("/all")
    @PreAuthorize("hasRole('ROLE_admin')")
    @PreFilter(value = "filterObject.id%2==0")
    @ResponseBody
    public List<Users> getTestPreFilter(@RequestBody List<Users> list) {
        list.forEach(t -> System.out.println(t.getId() + "\t" + t.getUsername()));
        return list;
    }
}
