package me.jkong.provider.util.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;

/**
 * @author JKong
 * @version v1.0
 * @description mongo template singleton
 * @date 2019/9/6 10:19.
 */
public class MongoTemplateSingleton {
    private static MongoTemplate mongoTemplate;
    private static final Object LOCK = new Object();

    public static MongoTemplate getMongoTemplate() throws UnknownHostException {
        if (mongoTemplate == null) {
            synchronized (LOCK) {
                if (mongoTemplate == null) {
                    mongoTemplate = new MongoTemplate(MongoConfig.mongoDbFactoryBean());
                }
            }
        }
        return mongoTemplate;
    }

    public static void main(String[] args) {
        MongoTemplate mongoTemplate = null;
        try {
            mongoTemplate = getMongoTemplate();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(mongoTemplate);
    }
}