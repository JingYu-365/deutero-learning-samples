package me.jkong.provider.dao;

import me.jkong.entity.ResourceType;
import org.junit.Test;

import java.util.List;

/**
 * @author JKong
 * @version v1.0
 * @description TODO
 * @date 2019/9/24 9:41.
 */
public class ResourceTypeDAOTest {

    @Test
    public void listResourceType() {
        List<ResourceType> resourceTypes = new ResourceTypeDAO().listResourceType();
        System.out.println(resourceTypes);
    }
}