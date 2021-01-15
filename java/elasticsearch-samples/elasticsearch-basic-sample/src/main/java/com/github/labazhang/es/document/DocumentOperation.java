package com.github.labazhang.es.document;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;

/**
 * 文档操作
 *
 * @author Laba Zhang
 */
public class Operation {

    private static RestHighLevelClient client;

    public static String index(String index, String type, String docId, String data) throws IOException {
        IndexRequest request = new IndexRequest(index, type, docId);

        request.source(data, XContentType.JSON);

        IndexResponse resp = client.index(request, RequestOptions.DEFAULT);
        return resp.getResult().toString();
    }

    /**
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-document-get.html
     */
    public static String get(String index, String type, String docId) throws IOException {
        GetRequest req = new GetRequest(index, type, docId);
        // 配置输出的字段
        String[] includes = new String[]{"message", "*Date"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        req.fetchSourceContext(fetchSourceContext);
        GetResponse resp = client.get(req, RequestOptions.DEFAULT);
        return resp.getSourceAsString();
    }

    /**
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-document-exists.html
     */
    public static boolean exist(String index, String type, String docId) throws IOException {
        GetRequest req = new GetRequest(index, type, docId);
        req.fetchSourceContext(new FetchSourceContext(false));
        req.storedFields("_none_");
        return client.exists(req, RequestOptions.DEFAULT);
    }

    /**
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-document-delete.html
     */
    public static String delete(String index, String type, String docId) throws IOException {
        DeleteRequest request = new DeleteRequest(index, type, docId);
        request.version(2);
        request.timeout(TimeValue.timeValueMinutes(2));
        request.timeout("2m");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        return response.toString();
    }

    /**
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-document-update.html
     */
    public static String update(String index, String type, String docId, String data) throws IOException {
        UpdateRequest request = new UpdateRequest(index, type, docId);
        request.doc(data, XContentType.JSON);
//        request.waitForActiveShards(2);
//        request.waitForActiveShards(ActiveShardCount.ALL);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        return response.getGetResult().sourceAsString();
    }

    /**
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-document-update.html
     */
    public static String upsert(String index, String type, String docId, String data) throws IOException {
        UpdateRequest request = new UpdateRequest(index, type, docId);
        request.upsert(data, XContentType.JSON);
//        request.waitForActiveShards(2);
//        request.waitForActiveShards(ActiveShardCount.ALL);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        return response.getGetResult().sourceAsString();
    }

    public static void bulk(String index, String type, String docId, String data) throws IOException {
        BulkRequest indexRequest = new BulkRequest();
        indexRequest.add(new IndexRequest("posts", "doc", "1").source(XContentType.JSON, "field", "foo"));
        indexRequest.add(new IndexRequest("posts", "doc", "2").source(XContentType.JSON, "field", "bar"));
        indexRequest.add(new IndexRequest("posts", "doc", "3").source(XContentType.JSON, "field", "baz"));

        BulkRequest docRequest = new BulkRequest();
        docRequest.add(new DeleteRequest("posts", "doc", "3"));
        docRequest.add(new UpdateRequest("posts", "doc", "2").doc(XContentType.JSON, "other", "test"));
        docRequest.add(new IndexRequest("posts", "doc", "4").source(XContentType.JSON, "field", "baz"));

        BulkResponse docBulkResponse = client.bulk(docRequest, RequestOptions.DEFAULT);
        BulkItemResponse[] items = docBulkResponse.getItems();
        for (BulkItemResponse item : items) {
            System.out.println(item.isFailed());
        }
    }


    public static void multiGet(String index, String type, String docId, String data) throws IOException {
        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item("index", "type", "example_id"));
        request.add(new MultiGetRequest.Item("index", "type", "another_id"));

        MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
        MultiGetItemResponse[] responses = response.getResponses();
        for (MultiGetItemResponse resp : responses) {
            System.out.println(resp.getResponse().getSourceAsString());
        }
    }
}