package com.github.labazhang.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * ES 工具
 *
 * @author Laba Zhang
 */
public class ElasticSearch {

    private static RestHighLevelClient client;

    static {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));
    }

    public static RestHighLevelClient getInstance() {
        return client;
    }

    public static void close() {
        if (client != null) {
            try {
                client.close();
            } catch (IOException ignore) {
            }
        }
    }
}