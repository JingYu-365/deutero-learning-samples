package me.jkong.provider.dao;

import me.jkong.entity.ResourceType;
import me.jkong.provider.util.mongo.MongoTemplateSingleton;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @author JKong
 * @version v1.0
 * @description DAO
 * @date 2019/9/24 9:26.
 */
public class ResourceTypeDAO {

    private static MongoTemplate MONGO_TEMPLATE;

    static {
        try {
            MONGO_TEMPLATE = MongoTemplateSingleton.getMongoTemplate();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public List<ResourceType> listResourceType() {
        Query mongoQuery = new Query();
        mongoQuery.addCriteria(Criteria.where("creator_group_id").is("1"));
        mongoQuery.addCriteria(Criteria.where("creator_id").is("1"));
        return MONGO_TEMPLATE.find(mongoQuery, ResourceType.class);
    }
}