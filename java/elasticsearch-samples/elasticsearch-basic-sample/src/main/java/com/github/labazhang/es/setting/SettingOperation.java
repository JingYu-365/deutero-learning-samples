package com.github.labazhang.es.setting;

import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Map;

/**
 * Setting Operation
 *
 * @author Laba Zhang
 */
public class SettingOperation {

    private static RestHighLevelClient client;

    public static String getSettings(String index) throws IOException {
        GetSettingsRequest req = new GetSettingsRequest().indices(index);
        GetSettingsResponse resp = client.indices().getSettings(req, RequestOptions.DEFAULT);
        return resp.toString();
    }

    public static boolean updateIndicesSettings(Map<String, Object> settings, String... index) throws IOException {
        // Update settings for one index
        UpdateSettingsRequest request = new UpdateSettingsRequest(index);
        //Update settings for multiple indices
//        UpdateSettingsRequest requestMultiple = new UpdateSettingsRequest("index1", "index2");
        //Update settings for all indices
//        UpdateSettingsRequest requestAll = new UpdateSettingsRequest();

        // set setting infos
        request.settings(settings);
        // 是否保持已经存在的setting，true：not update; false: update
        request.setPreserveExisting(false);

        AcknowledgedResponse resp = client.indices().putSettings(request, RequestOptions.DEFAULT);
        return resp.isAcknowledged();
    }
}