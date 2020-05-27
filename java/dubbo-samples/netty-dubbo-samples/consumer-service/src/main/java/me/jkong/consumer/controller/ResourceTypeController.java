package me.jkong.consumer.controller;

import me.jkong.consumer.Consumer;
import me.jkong.entity.ResourceType;
import me.jkong.service.IResourceTypeService;

import java.util.List;

/**
 * @author JKong
 * @version v1.0
 * @description controller
 * @date 2019/9/24 9:16.
 */
public class ResourceTypeController {

    public List<ResourceType> listResourceType() {
        IResourceTypeService service = (IResourceTypeService) Consumer.CONTEXT.getBean("resourceTypeService");
        long start = System.currentTimeMillis();
        List<ResourceType> resourceTypes = service.listResourceType();
        System.out.println(System.currentTimeMillis() - start);
        return resourceTypes;
    }
}