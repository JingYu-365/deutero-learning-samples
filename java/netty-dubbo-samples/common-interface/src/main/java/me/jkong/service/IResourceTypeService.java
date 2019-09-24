package me.jkong.service;

import me.jkong.entity.ResourceType;

import java.util.List;

/**
 * @author JKong
 * @version v1.0
 * @description interface
 * @date 2019/9/24 9:07.
 */
public interface IResourceTypeService {
    /**
     * 查询所有资源类型数据
     *
     * @return list
     */
    List<ResourceType> listResourceType();
}