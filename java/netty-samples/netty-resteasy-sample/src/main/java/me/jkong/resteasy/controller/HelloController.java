package me.jkong.resteasy.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JKong
 * @version v1.0
 * @description Controller
 * @date 2019/11/11 14:45.
 */
@Path("/")
public class HelloController {

    @GET
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> sayHello() {
        Map<String, String> content = new HashMap<>(4);
        content.put("name", "jkong");
        return content;
    }
}