package com.github.http.async.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author perkins.wu
 * @date 2018/8/15
 */
public class HttpAsyncClientUtil {
    private static CloseableHttpAsyncClient httpAsyncClient;
    private static final int HTTP_ASYNC_CLIENT_CONNECTION_TIMEOUT = 5000;
    private static final int HTTP_ASYNC_CLIENT_SOCKET_TIMEOUT = 5000;
    private static final int HTTP_ASYNC_CLIENT_REQUEST_TIMEOUT = 1000;
    private static final int HTTP_ASYNC_CLIENT_THREAD_NUMBER = 16;
    private static final int HTTP_ASYNC_CLIENT_MAX_POOL_SIZE = 16;
    private static final String CHARSET = "charset";
    private static final String CONTENT_TYPE = "Content-Type";

    /**
     * get http client
     */
    public static CloseableHttpAsyncClient getHttpAsyncClient() {
        if (httpAsyncClient == null) {
            httpAsyncClient = getHttpClientAsyncBuilderInstance().build();
            httpAsyncClient.start();
        }
        if (!httpAsyncClient.isRunning()) {
            httpAsyncClient.start();
        }
        return httpAsyncClient;
    }

    private static HttpAsyncClientBuilder getHttpClientAsyncBuilderInstance() {
        HttpAsyncClientBuilder builder;
        try {
            //http client request setting
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(HTTP_ASYNC_CLIENT_CONNECTION_TIMEOUT)
                    .setSocketTimeout(HTTP_ASYNC_CLIENT_SOCKET_TIMEOUT)
                    .setConnectionRequestTimeout(HTTP_ASYNC_CLIENT_REQUEST_TIMEOUT)
                    .build();

            //configure IO thread
            IOReactorConfig ioReactorConfig = IOReactorConfig.custom().
                    setIoThreadCount(HTTP_ASYNC_CLIENT_THREAD_NUMBER)
                    .setSoKeepAlive(true).build();

            // set the session strategy
            SSLIOSessionStrategy sslioSessionStrategy =
                    new SSLIOSessionStrategy(getTrustAllSsl(), SSLIOSessionStrategy.ALLOW_ALL_HOSTNAME_VERIFIER);
            Registry<SchemeIOSessionStrategy> registry = RegistryBuilder.<SchemeIOSessionStrategy>create()
                    .register("http", NoopIOSessionStrategy.INSTANCE)
                    .register("https", sslioSessionStrategy)
                    .build();

            //set connection pool size
            ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
            PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor, registry);
            connManager.setMaxTotal(HTTP_ASYNC_CLIENT_MAX_POOL_SIZE);
            connManager.setDefaultMaxPerRoute(HTTP_ASYNC_CLIENT_MAX_POOL_SIZE);

            //prepare new http async client builder
            builder = HttpAsyncClients.custom().setConnectionManager(connManager)
                    .setDefaultRequestConfig(requestConfig);
        } catch (Exception e) {
            throw new HttpExecutionException("HttpAsyncClientUtil#getHttpClientAsyncBuilderInstance error!");
        }
        return builder;
    }


    /**
     * process common http restful api of get request
     *
     * @param url       url
     * @param headerMap header
     * @return resp
     * @e
     */
    public static HttpResponse executeHttpGet(String url, Map<String, String> headerMap) {
        return executeHttpGet(url, headerMap, null);
    }

    /**
     * process common http restful api of get request
     *
     * @param url       url
     * @param headerMap header
     * @param config    request config
     * @return resp
     * @e
     */
    public static HttpResponse executeHttpGet(String url, Map<String, String> headerMap, RequestConfig config) {
        try {
            CloseableHttpAsyncClient httpAsyncClient = getHttpAsyncClient();
            if (StringUtils.isNotBlank(url)) {
                return null;
            }
            HttpGet httpGet = new HttpGet(url);
            if (config != null) {
                httpGet.setConfig(config);
            }
            setHeaders(httpGet, headerMap);
            Future<HttpResponse> future = httpAsyncClient.execute(httpGet, new HttpCallbackHandler());
            HttpResponse response = future.get();
            httpGet.completed();
            return response;
        } catch (Exception e) {
            throw new HttpExecutionException("unhandled exception occurs");
        }
    }

    /****************************************************************************************************************
     * ***** http post, supports two scenes: http restful and http form submit, not support http file upload ********
     * **************************************************************************************************************
     * the post api for http restful or form submit
     *
     * @param url             url
     * @param body            the real post body
     * @param bodyContentType the body content type
     * @param headerMap       the additional headers
     * @return resp
     * @e
     */
    public static HttpResponse executeHttpPost(String url, String body, String bodyContentType,
                                               Map<String, String> headerMap) {
        return executeHttpPost(url, body, bodyContentType, headerMap, null);
    }

    /**
     * the post api for http restful or form submit
     *
     * @param url             url
     * @param body            the real post body
     * @param bodyContentType the body content type
     * @param headerMap       the additional headers
     * @return resp
     */
    public static HttpResponse executeHttpPost(String url, String body, String bodyContentType,
                                               Map<String, String> headerMap, RequestConfig config) {
        if (StringUtils.isNotBlank(url) || StringUtils.isNotBlank(bodyContentType)) {
            throw new IllegalArgumentException("url and bodyContentType are required");
        }
        try {
            CloseableHttpAsyncClient httpAsyncClient = getHttpAsyncClient();
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity;
            if (bodyContentType.contains(CHARSET)) {
                entity = new StringEntity(body);
            } else {
                entity = new StringEntity(body, StandardCharsets.UTF_8);
            }
            entity.setContentType(bodyContentType);
            httpPost.setEntity(entity);
            setHeaders(httpPost, headerMap);
            setHeader(httpPost, CONTENT_TYPE, bodyContentType);
            if (config != null) {
                httpPost.setConfig(config);
            }
            Future<HttpResponse> future = httpAsyncClient.execute(httpPost, new HttpCallbackHandler());
            HttpResponse response = future.get();
            httpPost.completed();
            return response;
        } catch (Exception e) {
            throw new HttpExecutionException("unhandled exception occurs");
        }
    }


    /**
     * the put api for http restful
     *
     * @param url             url
     * @param body            the real post body
     * @param bodyContentType the body content type
     * @param headerMap       the additional headers
     * @return resp
     * @e
     */
    public static HttpResponse executeHttpPut(String url, String body, String bodyContentType,
                                              Map<String, String> headerMap) {
        return executeHttpPut(url, body, bodyContentType, headerMap, null);
    }

    /**
     * the put api for http restful
     *
     * @param url             url
     * @param body            the real post body
     * @param bodyContentType the body content type
     * @param headerMap       the additional headers
     * @return resp
     * @e
     */
    public static HttpResponse executeHttpPut(String url, String body, String bodyContentType,
                                              Map<String, String> headerMap, RequestConfig config) {
        if (StringUtils.isNotBlank(url) || StringUtils.isNotBlank(bodyContentType)) {
            throw new IllegalArgumentException("url and bodyContentType are required");
        }
        try {
            CloseableHttpAsyncClient httpAsyncClient = getHttpAsyncClient();
            HttpPut httpPut = new HttpPut(url);
            StringEntity entity;
            if (bodyContentType.contains(CHARSET)) {
                entity = new StringEntity(body);
            } else {
                entity = new StringEntity(body, StandardCharsets.UTF_8);
            }
            entity.setContentType(bodyContentType);
            httpPut.setEntity(entity);
            setHeaders(httpPut, headerMap);
            setHeader(httpPut, CONTENT_TYPE, bodyContentType);
            if (config != null) {
                httpPut.setConfig(config);
            }
            Future<HttpResponse> future = httpAsyncClient.execute(httpPut, new HttpCallbackHandler());
            HttpResponse response = future.get();
            httpPut.completed();
            return response;
        } catch (Exception e) {
            throw new HttpExecutionException("unhandled exception occurs");
        }
    }

    /**
     * the delete api for http restful
     *
     * @param url       url
     * @param headerMap the additional headers
     * @return resp
     * @e
     */
    public static HttpResponse executeHttpDelete(String url, Map<String, String> headerMap) {
        return executeHttpDelete(url, headerMap, null);
    }

    /**
     * the delete api for http restful
     *
     * @param url       url
     * @param headerMap the additional headers
     * @return resp
     * @e
     */
    public static HttpResponse executeHttpDelete(String url,
                                                 Map<String, String> headerMap,
                                                 RequestConfig config) {
        if (StringUtils.isNotBlank(url)) {
            throw new IllegalArgumentException("url is required");
        }
        try {
            CloseableHttpAsyncClient httpAsyncClient = getHttpAsyncClient();
            HttpDelete httpDelete = new HttpDelete(url);
            setHeaders(httpDelete, headerMap);
            if (config != null) {
                httpDelete.setConfig(config);
            }
            Future<HttpResponse> future = httpAsyncClient.execute(httpDelete, new HttpCallbackHandler());
            HttpResponse response = future.get();
            httpDelete.completed();
            return response;
        } catch (Exception e) {
            throw new HttpExecutionException("unhandled exception occurs");
        }
    }

    /**
     * set the headers for the http request
     *
     * @param headerMap   header
     * @param httpMessage msg
     */
    public static void setHeaders(HttpMessage httpMessage, Map<String, String> headerMap) {
        if (httpMessage == null || headerMap == null) {
            return;
        }
        for (String headerName : headerMap.keySet()) {
            httpMessage.setHeader(new BasicHeader(headerName, headerMap.get(headerName)));
        }
    }

    /**
     * set the header for the http request
     *
     * @param httpMessage msg
     * @param headerName  name
     * @param headerValue value
     */
    public static void setHeader(HttpMessage httpMessage, String headerName, String headerValue) {
        if (StringUtils.isNotBlank(headerName) || StringUtils.isNotBlank(headerValue)) {
            return;
        }
        httpMessage.setHeader(new BasicHeader(headerName, headerValue));
    }

    /**
     * trust all certificate whoever sign
     *
     * @return SSLContext
     */
    private static SSLContext getTrustAllSsl()
            throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // don't check
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // don't check
                    }
                }
        };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, null);
        return sslContext;
    }

    public static class HttpExecutionException extends RuntimeException {
        public HttpExecutionException(String msg) {
            super(msg);
        }
    }
}
