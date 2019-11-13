package me.jkong.consumer.util.http;
// Nian Shao Mo Qing Kuang!

import com.google.gson.Gson;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.util.*;


/**
 * @author JKong
 * @version v1.0
 * @description Netty 请求参数处理器
 * @date 2019/9/19 15:49.
 */
public class NettyRequestParamProcessor {

    private static final int MAP_DEFAULT_SIZE = 16;
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";


    /**
     * 解析request，获取 query 请求参数
     *
     * @param request 请求
     * @return 返回解析的query参数
     * 示例：
     * ?name=JKong&age=26
     * {
     * "name":["JKong"],
     * "age":["26"]
     * }
     * <p>
     * ?name=JKong&age=26&name=zhangsan
     * {
     * "name":[
     * "JKong","zhangsan"
     * ],
     * "age":["26"]
     * }
     */
    public static Map<String, List<String>> parseForQueryParams(FullHttpRequest request) {
        if (Objects.equals(request, null)) {
            return new HashMap<>(MAP_DEFAULT_SIZE);
        }
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.parameters();
    }


    /**
     * 解析request，获取 query 请求参数。
     * （如果不存在一个name对应多个值得情况使用。）
     *
     * @param request 请求
     * @return 返回解析的query参数
     */
    public static Map<String, String> parseForQueryParamsForSingle(FullHttpRequest request) {
        if (Objects.equals(request, null)) {
            return new HashMap<>(MAP_DEFAULT_SIZE);
        }
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> parameters = decoder.parameters();
        Map<String, String> queryParams = new HashMap<>();
        for (String name : parameters.keySet()) {
            queryParams.put(name, parameters.get(name).get(0));
        }
        return queryParams;
    }


    /**
     * 解析request，获取request body的参数
     *
     * @param request 请求
     * @return body请求参数
     * <p>
     * 目前 body 体数据只支持两种 Content-Type：
     * - application/json
     * - multipart/form-data
     */
    public static Map<String, String> parseForBodyParams(FullHttpRequest request) {
        Map<String, String> bodyParams = new HashMap<>(MAP_DEFAULT_SIZE);

        String contentType = request.headers().get(CONTENT_TYPE);
        // 处理一般 POST 请求
        if (request.content().isReadable() && contentType.equals(APPLICATION_JSON)) {
            String json = request.content().toString(CharsetUtil.UTF_8);
            bodyParams.putAll(new Gson().fromJson(json, Map.class));
        }

        // 处理 form 表单
        if (request.content().isReadable() && contentType.startsWith(MULTIPART_FORM_DATA)) {
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
            decoder.offer(request);
            List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
            for (InterfaceHttpData parm : parmList) {
                Attribute data = (Attribute) parm;
                try {
                    bodyParams.put(data.getName(), data.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bodyParams;
    }


    /**
     * 解析request，获取 request headers 的参数
     *
     * @param request 请求
     * @return headers 请求参数
     */
    public static Map<String, String> parseForHeaderParams(FullHttpRequest request) {
        Map<String, String> headerParams = new HashMap<>(MAP_DEFAULT_SIZE);
        HttpHeaders headers = request.headers();
        for (Map.Entry<String, String> header : headers) {
            headerParams.put(header.getKey(), header.getValue());
        }
        return headerParams;
    }

    /**
     * 解析request，获取 request headers 的参数
     *
     * @param request 请求
     * @param names   需要获取的 header 的 name
     * @return headers 请求参数
     */
    public static Map<String, String> parseForHeaderParams(FullHttpRequest request, Collection<String> names) {
        Map<String, String> headerParams = new HashMap<>(MAP_DEFAULT_SIZE);
        HttpHeaders headers = request.headers();
        for (String name : names) {
            if (headers.contains(name)) {
                headerParams.put(name, headers.get(name));
            }
        }
        return headerParams;
    }
}