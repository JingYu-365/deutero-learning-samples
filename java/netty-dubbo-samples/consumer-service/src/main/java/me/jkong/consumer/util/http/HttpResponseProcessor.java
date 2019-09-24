package me.jkong.consumer.util.http;

import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;
import me.jkong.consumer.constant.CodeEnum;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JKong
 * @version v1.0
 * @description http response processor
 * @date 2019/9/17 9:19.
 */
public class HttpResponseProcessor {
    private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
    private static final AsciiString DATE = new AsciiString("Date");
    private static final AsciiString SERVER = new AsciiString("Server");


    public static final String APPLICATION_JSON = "application/json;charset=utf8";
    private static final String RESPONSE_CONTENT_TYPE_APPLICATION_JSON = "application/json;charset=utf-8";
    private static final String SAS_SERVER = "sas server";
    private static final String ERROR_CODE = "errorCode";
    private static final String ERROR_INFO = "errorInfo";


    public static FullHttpResponse prepareResponse(String content, HttpResponseStatus status, String contentType) {
        byte[] result = content.getBytes();
        FullHttpResponse rep = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.wrappedBuffer(result));
        rep.headers().set(CONTENT_TYPE, contentType);
        rep.headers().set(CONTENT_LENGTH, rep.content().readableBytes());
        rep.headers().set(DATE, new Date());
        rep.headers().set(SERVER, SAS_SERVER);
        return rep;
    }

    /**
     * url映射不存在时使用此处理
     *
     * @return FullHttpResponse
     */
    public static FullHttpResponse getSuccessResponse(String data) {
        return HttpResponseProcessor.prepareResponse(data,
                HttpResponseStatus.OK, HttpResponseProcessor.APPLICATION_JSON);
    }

    /**
     * url映射不存在时使用此处理
     *
     * @return FullHttpResponse
     */
    public static FullHttpResponse getApiNotFoundResponse() {
        Map<String, String> errorMap = new HashMap<>(4);
        errorMap.put(ERROR_CODE, CodeEnum.API_NOT_FOUND.getCode());
        errorMap.put(ERROR_INFO, CodeEnum.API_NOT_FOUND.getInfo());
        return HttpResponseProcessor.prepareResponse(new Gson().toJson(errorMap),
                HttpResponseStatus.NOT_FOUND, HttpResponseProcessor.APPLICATION_JSON);
    }

    /**
     * url映射不存在时使用此处理
     *
     * @return FullHttpResponse
     */
    public static FullHttpResponse getRequestMethodErrorResponse() {
        Map<String, String> errorMap = new HashMap<>(4);
        errorMap.put(ERROR_CODE, CodeEnum.REQUEST_METHOD_ERROR.getCode());
        errorMap.put(ERROR_INFO, CodeEnum.REQUEST_METHOD_ERROR.getInfo());
        return HttpResponseProcessor.prepareResponse(new Gson().toJson(errorMap),
                HttpResponseStatus.METHOD_NOT_ALLOWED, HttpResponseProcessor.APPLICATION_JSON);
    }

    /**
     * 缺少请求参数
     *
     * @return FullHttpResponse
     */
    public static FullHttpResponse getMissingParameterResponse() {
        Map<String, String> errorMap = new HashMap<>(4);
        errorMap.put(ERROR_CODE, CodeEnum.MISSING_PARAMETER.getCode());
        errorMap.put(ERROR_INFO, CodeEnum.MISSING_PARAMETER.getInfo());
        return HttpResponseProcessor.prepareResponse(new Gson().toJson(errorMap),
                HttpResponseStatus.BAD_REQUEST, HttpResponseProcessor.APPLICATION_JSON);
    }

    /**
     * 内部错误异常
     *
     * @return FullHttpResponse
     */
    public static FullHttpResponse getInternalErrorResponse() {
        Map<String, String> errorMap = new HashMap<>(4);
        errorMap.put(ERROR_CODE, CodeEnum.INTERNAL_ERROR.getCode());
        errorMap.put(ERROR_INFO, CodeEnum.INTERNAL_ERROR.getInfo());
        return HttpResponseProcessor.prepareResponse(new Gson().toJson(errorMap),
                HttpResponseStatus.INTERNAL_SERVER_ERROR, HttpResponseProcessor.APPLICATION_JSON);
    }

    /**
     * 内部错误异常
     *
     * @return FullHttpResponse
     */
    public static FullHttpResponse getRequestErrorResponse() {
        Map<String, String> errorMap = new HashMap<>(4);
        errorMap.put(ERROR_CODE, CodeEnum.API_REQUEST_ERROR.getCode());
        errorMap.put(ERROR_INFO, CodeEnum.API_REQUEST_ERROR.getInfo());
        return HttpResponseProcessor.prepareResponse(new Gson().toJson(errorMap),
                HttpResponseStatus.REQUEST_TIMEOUT, HttpResponseProcessor.APPLICATION_JSON);
    }


    /**
     * 响应数据解析错误异常
     *
     * @return FullHttpResponse
     */
    public static FullHttpResponse getResponseParseErrorResponse() {
        Map<String, String> errorMap = new HashMap<>(4);
        errorMap.put(ERROR_CODE, CodeEnum.RESPONSE_PARSE_ERROR.getCode());
        errorMap.put(ERROR_INFO, CodeEnum.RESPONSE_PARSE_ERROR.getInfo());
        return HttpResponseProcessor.prepareResponse(new Gson().toJson(errorMap),
                HttpResponseStatus.UNPROCESSABLE_ENTITY, HttpResponseProcessor.APPLICATION_JSON);
    }

    /**
     * 响应数据解析错误异常
     *
     * @return FullHttpResponse
     */
    public static FullHttpResponse getResponseParseErrorResponse(String msg) {
        Map<String, String> errorMap = new HashMap<>(4);
        errorMap.put(ERROR_CODE, CodeEnum.RESPONSE_PARSE_ERROR.getCode());
        errorMap.put(ERROR_INFO, msg);
        return HttpResponseProcessor.prepareResponse(new Gson().toJson(errorMap),
                HttpResponseStatus.UNPROCESSABLE_ENTITY, HttpResponseProcessor.APPLICATION_JSON);
    }

}