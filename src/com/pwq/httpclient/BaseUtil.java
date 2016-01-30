package com.pwq.httpclient;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by 枫叶 on 2016/1/17.
 */
public class BaseUtil {
    private CloseableHttpClient httpclient = null;

    public CloseableHttpClient getHttpClient() {
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException arg0, int executionCount, HttpContext arg2) {
                System.out.println(executionCount + " times connecting failed, try again...");
                if(executionCount > 5) {
                    System.out.println("Fail to connect, exit.");
                    return false;
                }
                return true;
            }
        };
 //       HttpHost proxy = new HttpHost("127.0.0.1", 8888);       //开发时用
        httpclient = HttpClients.custom().setRetryHandler(myRetryHandler)
               /*.setProxy(proxy)*/.build();
        return httpclient;
    }

    public HttpGet getHttpGet(String url) {
        HttpGet httpget = new HttpGet(url);
        setRequest(httpget);
        return httpget;
    }

    public HttpPost getHttpPost(String url) {
        HttpPost httppost = new HttpPost(url);
        setRequest(httppost);
        return httppost;
    }

    private void setRequest(HttpRequestBase request) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(2000)
                .setCookieSpec(CookieSpecs.STANDARD_STRICT).setSocketTimeout(5000).
                        setConnectTimeout(5000).build();
        request.setConfig(requestConfig);
    }
}
