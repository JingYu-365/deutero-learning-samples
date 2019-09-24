package me.jkong.provider.util.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import me.jkong.provider.constant.MongoConstant;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class MongoConfig {
    public static MongoDbFactory mongoDbFactoryBean() throws UnknownHostException {
        String username = MongoConstant.MONGO_USERNAME;
        String password = MongoConstant.MONGO_PASSWORD;
        String authDatabase = MongoConstant.MONGO_AUTHDBNAME;
        String database = MongoConstant.MONGO_DBNAME;
        String cluster = MongoConstant.MONGO_HOST;
        String port = MongoConstant.MONGO_PORT;


        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        MongoClientOptions build = builder.build();

        MongoCredential credential = MongoCredential.createCredential(username, authDatabase, password.toCharArray());
        List<MongoCredential> credentialList = new ArrayList<MongoCredential>();
        credentialList.add(credential);
        //mongo cluster
        String[] mongoHosts = cluster.split(",");
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
        for (String host : mongoHosts) {
            ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(port));
            addrs.add(serverAddress);
        }
        MongoClient mongoClient = new MongoClient(addrs, credentialList, build);
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, database);

        return mongoDbFactory;
    }
}
