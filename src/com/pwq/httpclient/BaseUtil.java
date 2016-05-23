package com.pwq.httpclient;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by 枫叶 on 2016/1/17.
 */
@SuppressWarnings("all")
public class BaseUtil {
    private static class Nested {
        private static CloseableHttpClient instance = HttpClients.custom().setRetryHandler(new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException arg0, int executionCount, HttpContext arg2) {
                System.out.println(executionCount + " times connecting failed, try again...");
                if(executionCount > 5) {
                    System.out.println("Fail to connect, exit.");
                    return false;
                }
                return true;
            }
        })/*.setProxy(new HttpHost("127.0.0.1", 8888))*/.build();
    }

    public static CloseableHttpClient getHttpClient() {
        return Nested.instance;
    }

    public static HttpGet getHttpGet(String url) {
        HttpGet httpget = new HttpGet(url);
        setRequest(httpget);
        return httpget;
    }

    public static HttpPost getHttpPost(String url) {
        HttpPost httppost = new HttpPost(url);
        setRequest(httppost);
        return httppost;
    }

    private static void setRequest(HttpRequestBase request) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(2000)
                .setCookieSpec(CookieSpecs.STANDARD_STRICT).setSocketTimeout(5000).
                        setConnectTimeout(5000).build();
        request.setConfig(requestConfig);
    }

    public static void setFont(String fontType) {
        UIManager.put("Button.font", fontType);
//        UIManager.put("CheckBox.font", fontType);
        UIManager.put("ComboBox.font", fontType);
        UIManager.put("Label.font", fontType);
//        UIManager.put("CheckBoxMenuItem.font", fontType);
        UIManager.put("Panel.font", fontType);
        UIManager.put("ScrollPane.font", fontType);
        UIManager.put("TabbedPane.font", fontType);
        UIManager.put("Table.font", fontType);
        UIManager.put("TableHeader.font", fontType);
        UIManager.put("PasswordField.font", fontType);
    }

    public static void setReqHeader(HttpEntityEnclosingRequestBase req, String refer) {
        req.setHeader("Content-Type", "multipart/form-data");
        req.setHeader(HttpHeaders.REFERER, refer);
        req.setHeader("_clientType", "unieap");
        req.setHeader("ajaxRequest", "true");
        req.setHeader("render", "unieap");
        req.setHeader("resourceid", null);
        req.setHeader("workitemid", null);
    }

}
