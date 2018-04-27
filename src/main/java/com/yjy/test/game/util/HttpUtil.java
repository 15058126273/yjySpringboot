package com.yjy.test.game.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求类
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * http客户端
     */
    private CloseableHttpClient httpClient;

    private String CHARSET = "UTF-8";

    private static HttpUtil instance = new HttpUtil();

    public static HttpUtil getInstance() {
        return instance;
    }

    public static final String TYPE_JSON = "application/json; charset=UTF-8";
    public static final String TYPE_TEXT = "text/plain; charset=UTF-8";

    /**
     * 创建http客户端
     *
     * @throws Exception
     */
    private void generateHttpClient() throws Exception {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(2000).setSocketTimeout(2000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return    页面内容
     */
    public String doGet(String url,Map<String,String> params) throws Exception{
        if(StringUtils.isBlank(url)){
            return null;
        }

        if (httpClient == null) generateHttpClient();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        HttpGet httpGet = null;
        try {
            if(params != null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
            }

            httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }

            entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, CHARSET);
            }
            return result;
        } catch (Exception e) {
            logger.error("http doGet throw an error: {}", e.getLocalizedMessage());
        } finally {
            if(entity != null) EntityUtils.consume(entity);
            if (response != null) response.close();
            if (httpGet != null)  httpGet.releaseConnection();
        }
        return null;
    }

    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return    页面内容
     */
    public String doPost(String url,Map<String,String> params) throws Exception {
        if(StringUtils.isBlank(url)){
            return null;
        }

        if (httpClient == null) generateHttpClient();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        HttpPost httpPost = null;
        try {
            List<NameValuePair> pairs = null;
            if(params != null && !params.isEmpty()){
                pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
            }

            httpPost = new HttpPost(url);
            if(pairs != null && pairs.size() > 0){
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
            }

            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }

            entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, CHARSET);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error("http doPost throw an error", e);
        } finally {
            if(entity != null) EntityUtils.consume(entity);
            if (response != null) response.close();
            if (httpPost != null) httpPost.releaseConnection();
        }
        return null;
    }

    /**
     * 对象转数组
     * @param obj
     * @return
     */
    public static byte[] transformObjectToByteArray (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * 数组转对象
     * @param bytes
     * @return
     */
    public static Object transformBytesToObject (byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    /**
     * 密钥信任策略（信任所有）
     */
    class AnyTrustStrategy implements TrustStrategy {

        @Override
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            return true;
        }

    }

}
