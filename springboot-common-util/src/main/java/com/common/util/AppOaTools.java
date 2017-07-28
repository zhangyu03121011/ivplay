package com.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class AppOaTools {

    private static Map<String, String> map = new HashMap<String, String>();

    private static final String OA_LOGIN = "login";

    private static final String OA_USER = "user";

    private static final String OA_WORK_ON = "work_on";

    private static final String OA_WORK_OFF = "work_off";

    private static final String CHARSET = "UTF-8";

    private static final String URL = "http://oa.ceacsz.com.cn";

    static {
        map.put(OA_LOGIN, URL + "/Account/LoginUser.html");
        map.put(OA_USER, URL + "/Account/GetCurrentUserInfo.html");
        map.put(OA_WORK_ON, URL + "/Attendance/SignInToWork.html");
        map.put(OA_WORK_OFF, URL + "/Attendance/SignInFromWork.html");
    }

    public static void main(String[] args) throws Exception {

        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient client = builder.build();

        System.out.println("******************************登录******************************");
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        NameValuePair nameValuePair = new BasicNameValuePair("username", "huijun.luo");
        NameValuePair pwdValuePair = new BasicNameValuePair("password", "123456");
        parameters.add(nameValuePair);
        parameters.add(pwdValuePair);
        HttpEntity entity = null;
        if (parameters != null && parameters.size() > 0) {
            entity = new UrlEncodedFormEntity(parameters, CHARSET);
        }
        postRequest(client, entity, map.get(OA_LOGIN));

        System.out.println("******************************输出用户信息******************************");
        postRequest(client, null, map.get(OA_USER));

        System.out.println("******************************上班******************************");
        // postRequest(client, null, map.get(OA_WORK_ON));

        System.out.println("******************************下班******************************");
        postRequest(client, null, map.get(OA_WORK_OFF));
    }

    public static boolean postRequest(CloseableHttpClient client, HttpEntity entity, String url) {
        HttpPost post = new HttpPost(url);
        boolean flag = false;
        try {
            if (entity != null) {
                post.setEntity(entity);
            }
            CloseableHttpResponse response = client.execute(post);
            if (response == null || response.getStatusLine().getStatusCode() != 200) {
                post.abort();
                System.out.println("http请求异常----------------------");
                throw new RuntimeException("HttpClient,error status code :" + response.getStatusLine().getStatusCode());
            }
            flag = true;
            getMessage(response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void getMessage(CloseableHttpResponse response) {
        try {
            String msg = null;
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                msg = EntityUtils.toString(entity, CHARSET);
            }
            EntityUtils.consume(entity);
            System.out.println(msg);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
