package me.jkong.provider.dao;

import me.jkong.entity.ResourceType;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author JKong
 * @version v1.0
 * @description DAO
 * @date 2019/9/24 9:26.
 */
public class ResourceTypeDAO {

    public List<ResourceType> listResourceType() {
        ResourceType resourceType = new ResourceType();
        resourceType.setId(UUID.randomUUID().toString())
                .setTypeName("typeName")
                .setBizType("bizType")
                .setOrderNo(1)
                .setTypeDesc("description")
                .setTypeStatus(ResourceType.TypeStatus.CREATED)
                .setCreatorGroupId("creatorGroupId")
                .setCreatorId("creatorId")
                .setCreatorName("creatorName")
                .setCreateTime(System.currentTimeMillis())
                .setModifiedTime(System.currentTimeMillis());

        return Collections.singletonList(resourceType);
    }
}