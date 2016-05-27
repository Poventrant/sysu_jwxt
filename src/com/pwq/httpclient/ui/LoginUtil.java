package com.pwq.httpclient.ui;

import com.pwq.httpclient.BaseUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by 枫叶 on 2016/1/19.
 */
public class LoginUtil extends BaseUtil {
    public static String rno = null;

    public Image getImage(String url, HttpClient httpclient) {

        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(2000)
                .setSocketTimeout(5000).setConnectTimeout(5000).build();
        httpGet.setConfig(requestConfig);

        Image image = null;
        try {
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            InputStream is = new BufferedInputStream(entity.getContent());
            image = ImageIO.read(is);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }

        return image;
    }

    public boolean login(String username, String password, String captchaCode) {
        //设置request
        HttpPost httppost = getHttpPost(Paths.LOGIN);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            java.util.List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("j_username", username));
            params.add(new BasicNameValuePair("j_password", password));
            params.add(new BasicNameValuePair("rno", rno));
            params.add(new BasicNameValuePair("jcaptcha_response", captchaCode));
            httppost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = getHttpClient().execute(httppost);
            String reurl = response.getLastHeader("Location").getValue();
            if(reurl == null || reurl.equals("")) return false;
        } catch(Exception e) {
            return false;
        } finally {
            httppost.releaseConnection();
        }
        return true;
    }

    public String getCookie() {
        try {
            InputStream fileStream = this.getClass().getResourceAsStream("/backup");
            BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

}
