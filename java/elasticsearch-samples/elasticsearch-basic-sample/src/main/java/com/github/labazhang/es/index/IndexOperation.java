package com.github.labazhang.es.index;

import com.alibaba.fastjson.JSON;
import com.github.labazhang.es.ElasticSearch;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.cluster.metadata.AliasMetaData;

import java.io.IOException;
import java.util.*;

/**
 * 索引操作
 *
 * @author Laba Zhang
 */
public class IndexOperation {

    private static RestHighLevelClient client = ElasticSearch.getInstance();

    public static String createIndex(String index) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(index);
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        return JSON.toJSONString(response);
    }

    public static boolean deleteIndex(String index) throws IOException {
        DeleteIndexRequest req = new DeleteIndexRequest(index);
        AcknowledgedResponse resp = client.indices().delete(req, RequestOptions.DEFAULT);
        return resp.isAcknowledged();
    }

    private static boolean existsIndex(String index) throws IOException {
        GetIndexRequest req = new GetIndexRequest(index);
        return client.indices().exists(req, RequestOptions.DEFAULT);
    }

    /**
     * 获取所有index
     */
    public static List<String> getAllIndices() throws IOException {
        List<String> resultList = new ArrayList<>();
        GetAliasesRequest request = new GetAliasesRequest();
        GetAliasesResponse alias = client.indices().getAlias(request, RequestOptions.DEFAULT);
        Map<String, Set<AliasMetaData>> map = alias.getAliases();
        map.forEach((k, v) -> {
            // skip elastic search inner index
            if (!k.startsWith(".")) {
                resultList.add(k);
            }
        });
        return resultList;
    }

    public static String openIndex(String index) throws IOException {
        OpenIndexRequest req = new OpenIndexRequest(index);
        OpenIndexResponse resp = client.indices().open(req, RequestOptions.DEFAULT);
        return JSON.toJSONString(resp);
    }

    public static boolean closeIndex(String index) throws IOException {
        CloseIndexRequest req = new CloseIndexRequest(index);
        AcknowledgedResponse resp = client.indices().close(req, RequestOptions.DEFAULT);
        return resp.isAcknowledged();
    }

    public static String analyze(String analyzer, String... texts) throws IOException {
        AnalyzeRequest req = new AnalyzeRequest();
        req.text(texts);
        req.analyzer(analyzer);

        AnalyzeResponse resp = client.indices().analyze(req, RequestOptions.DEFAULT);
        return JSON.toJSONString(resp);
    }
}