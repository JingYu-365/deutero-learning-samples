package com.github.http.async.client;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

/**
 * TODO
 *
 * @author Laba Zhang
 */
public class HttpCallbackHandler implements FutureCallback<HttpResponse> {
    @Override
    public void completed(HttpResponse result) {
        
    }

    @Override
    public void failed(Exception ex) {

    }

    @Override
    public void cancelled() {

    }
}