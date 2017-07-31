package com.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.common.base.common.BaseLogger;
import com.common.base.constant.CommonConstant;

/**
 * httpClient工具
 * 
 * @author: HuiJunLuo
 * @date:2016年3月31日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class HttpClientUtil extends BaseLogger {

    private static HttpClientUtil httpClientUtil = null;

    private static CloseableHttpClient httpClient = null;

    public synchronized static HttpClientUtil getInstance() {
        if (httpClientUtil == null) {
            httpClientUtil = new HttpClientUtil();
        }
        httpClient = HttpClients.createDefault();
        return httpClientUtil;
    }

    public synchronized static HttpClientUtil getInstance(HttpClientType httpClientType) {
        if (httpClientUtil == null) {
            httpClientUtil = new HttpClientUtil();
        }
        if (httpClientType == HttpClientType.Default) {
            httpClient = HttpClients.createDefault();
        } else if (httpClientType == HttpClientType.SSL) {
            httpClient = httpClientUtil.createSSLClientDefault();
        }
        return httpClientUtil;
    }

    public synchronized static HttpClientUtil getInstance(File keystoreFile, String password) {
        if (httpClientUtil == null) {
            httpClientUtil = new HttpClientUtil();
        }
        httpClient = httpClientUtil.ssl(keystoreFile, password);
        return httpClientUtil;
    }

    private HttpClientUtil() {
    }

    enum HttpClientType {
        Default, SSL
    }

    /**
     * 创建SSL客户端
     * 
     * @return
     */
    public CloseableHttpClient createSSLClientDefault() {
        try {
            logger.error("[HttpClientUtil][createSSLClientDefault]");
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            logger.error("[HttpClientUtil][createSSLClientDefault]" + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("[HttpClientUtil][createSSLClientDefault]" + e.getMessage(), e);
        } catch (KeyStoreException e) {
            logger.error("[HttpClientUtil][createSSLClientDefault]" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("[HttpClientUtil][createSSLClientDefault]" + e.getMessage(), e);
        }
        return HttpClients.createDefault();
    }

    /**
     * HttpClient连接SSL
     */
    @SuppressWarnings("deprecation")
    public CloseableHttpClient ssl(File keystoreFile, String password) {
        CloseableHttpClient httpclient = null;
        try {

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream instream = new FileInputStream(keystoreFile);
            try {
                // 加载keyStore d:\\tomcat.keystore
                trustStore.load(instream, password.toCharArray());
            } catch (CertificateException e) {
                logger.error("[HttpClientUtil][ssl]" + e.getMessage(), e);
            } finally {
                try {
                    instream.close();
                } catch (Exception e) {
                    logger.error("[HttpClientUtil][ssl]" + e.getMessage(), e);
                }
            }

            // 相信自己的CA和所有自签名的证书
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                    .build();

            // 只允许使用TLSv1协议
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
                    null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        } catch (IOException e) {
            logger.error("[HttpClientUtil][ssl]" + e.getMessage(), e);
        } catch (KeyManagementException e) {
            logger.error("[HttpClientUtil][ssl]" + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("[HttpClientUtil][ssl]" + e.getMessage(), e);
        } catch (KeyStoreException e) {
            logger.error("[HttpClientUtil][ssl]" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("[HttpClientUtil][ssl]" + e.getMessage(), e);
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    logger.error("[HttpClientUtil][ssl]" + e.getMessage(), e);
                }
            }
        }
        return httpclient;
    }

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public String post(String url, String param) {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = null;
        try {
            // 创建httppost
            HttpPost httppost = new HttpPost(url);
            // 创建参数队列
            httppost.setEntity(new StringEntity(param, CommonConstant.CHARSET));

            logger.debug("executing post request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, CommonConstant.CHARSET);
                    logger.debug("Response content: " + result);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error("[HttpClientUtil][post][url=" + url + "]" + e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            logger.error("[HttpClientUtil][post][url=" + url + "]" + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("[HttpClientUtil][post][url=" + url + "]" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("[HttpClientUtil][post][url=" + url + "]" + e.getMessage(), e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("[HttpClientUtil][post][url=" + url + "]" + e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public String post(String url, Map<String, String> param) {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = httpClient;
        String result = null;
        try {
            // 创建httppost
            HttpPost httppost = new HttpPost(url);
            // 创建参数队列
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            if (MapUtils.isNotEmpty(param)) {
                Set<Entry<String, String>> set = param.entrySet();
                for (Entry<String, String> entry : set) {
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }

            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, CommonConstant.CHARSET);
            httppost.setEntity(uefEntity);

            logger.debug("executing post request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, CommonConstant.CHARSET);
                    logger.debug("Response content: " + result);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error("[HttpClientUtil][post][url=" + url + "]" + e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            logger.error("[HttpClientUtil][post][url=" + url + "]" + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("[HttpClientUtil][post][url=" + url + "]" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("[HttpClientUtil][post][url=" + url + "]" + e.getMessage(), e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("[HttpClientUtil][post][url=" + url + "]" + e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * 发送 get请求
     */
    public String get(String url) {
        CloseableHttpClient httpclient = httpClient;
        String result = null;
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            logger.debug("executing get request " + httpget.getURI());

            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity resEntity = response.getEntity();
                // 打印响应状态
                logger.debug(response.getStatusLine());
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, CommonConstant.CHARSET);
                    // 打印响应内容长度
                    logger.debug("Response content length: " + resEntity.getContentLength());
                    // 打印响应内容
                    logger.debug("Response content: " + result);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error("[HttpClientUtil][get][url=" + url + "]" + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("[HttpClientUtil][get][url=" + url + "]" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("[HttpClientUtil][get][url=" + url + "]" + e.getMessage(), e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("[HttpClientUtil][get][url=" + url + "]" + e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * 上传文件
     */
    public String upload(String url, File file, String content) {
        CloseableHttpClient httpclient = httpClient;
        String result = null;
        try {
            HttpPost httppost = new HttpPost(url);

            FileBody fileBody = new FileBody(file);
            StringBody stringBody = new StringBody(content, ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", fileBody)
                    .addPart("stringBody", stringBody).build();

            httppost.setEntity(reqEntity);

            logger.debug("executing upload request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                logger.debug(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, CommonConstant.CHARSET);
                    logger.debug("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error("[HttpClientUtil][upload][url=" + url + "]" + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("[HttpClientUtil][upload][url=" + url + "]" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("[HttpClientUtil][upload][url=" + url + "]" + e.getMessage(), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("[HttpClientUtil][upload][url=" + url + "]" + e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * 转string
     * 
     * @param entity
     * @return
     */
    public String httpEntityToString(HttpEntity entity) {
        StringBuffer buff = new StringBuffer();
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(entity.getContent());
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                buff.append(line);
            }
        } catch (IOException e) {
            logger.error("[HttpClientUtil][httpEntityToString]" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("[HttpClientUtil][httpEntityToString]" + e.getMessage(), e);
        } finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                logger.error("[HttpClientUtil][httpEntityToString]" + e.getMessage(), e);
            }
        }
        if (StringUtils.isNotEmpty(buff.toString())) {
            return buff.toString();
        }
        return null;
    }

    public static void main(String[] args) {

        try {
            // String url =
            // "https://api.element14.com/catalog/products?term=any%3A&storeInfo.id=cn.element14.com&resultsSettings.offset=0&resultsSettings.numberOfResults=1&resultsSettings.responseGroup=large&callInfo.omitXmlSchema=false&callInfo.responseDataFormat=json&callinfo.apiKey=53pzxuz48ey99tm5783233wu";
            // String result = getInstance(HttpClientType.SSL).get(url);

            String url = "http://test.sinotms.cn:8065/cmd.svc/Api/ApiLogin";
            Map param = new HashMap<String,String>();
            param.put("Date", "2017-12-22");
            param.put("Key", "4D2F9DCB49258D0B892D561FD72AAC3E");
            param.put("User", "test");
            param.put("IP", "1.1.1.1");
            param.put("Port", "808");

            String result = getInstance(HttpClientType.Default).post(url, JSON.toJSONString(param));
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
