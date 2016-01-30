package com.pwq.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DownloadFile {

    final static String basePath = "/";

    public String getFileNameByUrl(String url, String contentType) {
        url = url.substring(7);
        if(contentType.indexOf("html") != -1) {
            url = url.replace("[\\?/:*|<>\"]", "_") + ".html";
            return url;
        } else {
            return url.replace("[\\?/:*|<>\"]", "_") + "." +
                    contentType.substring(contentType.lastIndexOf("/") + 1);
        }
    }

    private void saveToLocal(HttpEntity entity, String filePath) throws IOException {
        if(entity == null) return;

        File dir = new File(basePath );
        if(!dir.exists()) {
            dir.mkdir();
        }

        String [] temp = filePath.split("/");
        String destfilename = temp[temp.length-1];
        File file = new File(basePath+destfilename);
        if (file.exists()) {
            file.delete();
        }
        InputStream in = entity.getContent();
        FileOutputStream fout = new FileOutputStream(file);
        try {
            int l;
            byte[] tmp = new byte[2048];
            while ( (l = in.read(tmp)) != -1 ) {
                fout.write(tmp, 0, l);
            }
        } finally {
            fout.close();
            in.close();
        }
    }

    public void downloadFile(String url, HttpClient httpclient) {
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(2000)
                .setSocketTimeout(5000).setConnectTimeout(5000).build();
        httpGet.setConfig(requestConfig);

        try {
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            ContentType.getOrDefault(entity).getMimeType();
            String filePath = basePath + getFileNameByUrl(url, ContentType.getOrDefault(entity).getMimeType());
            saveToLocal(entity, filePath);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
    }
}
