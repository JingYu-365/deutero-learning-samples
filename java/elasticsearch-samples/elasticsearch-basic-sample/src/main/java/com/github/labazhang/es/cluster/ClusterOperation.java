package com.github.labazhang.es.cluster;

import com.github.labazhang.es.ElasticSearch;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * TODO
 *
 * @author Laba Zhang
 */
public class ClusterOperation {


    private static RestHighLevelClient client = ElasticSearch.getInstance();

    public static void nodeInfo() throws IOException {
        ClusterHealthRequest request = new ClusterHealthRequest();
        ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
        int numberOfNodes = response.getNumberOfNodes();
        System.out.println(numberOfNodes);
    }

    public static void main(String[] args) throws IOException {
        nodeInfo();
    }
}