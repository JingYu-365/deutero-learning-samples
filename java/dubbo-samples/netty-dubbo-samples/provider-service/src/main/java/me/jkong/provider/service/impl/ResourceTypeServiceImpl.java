package me.jkong.provider.service.impl;

import me.jkong.entity.ResourceType;
import me.jkong.provider.dao.ResourceTypeDAO;
import me.jkong.service.IResourceTypeService;

import java.util.List;

/**
 * @author JKong
 * @version v1.0
 * @description interface impl
 * @date 2019/9/24 9:18.
 */
public class ResourceTypeServiceImpl implements IResourceTypeService {

    @Override
    public List<ResourceType> listResourceType() {
        return new ResourceTypeDAO().listResourceType();
    }
}