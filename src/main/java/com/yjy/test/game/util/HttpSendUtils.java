package com.yjy.test.game.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.*;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 前台用户对接的请求方法
 *
 * @author wdy
 * @version ：2016年4月18日 下午4:39:30
 */
public class HttpSendUtils {

    public static final String CODE = "ret";//请求接口返回的代码

    public static final String MESSAGE = "msg";//请求接口返回的错误消息

    public static final String SERVICE_NAME = "service";//服务的参数名称

    public static final int SUCCESS_CODE = 200;//请求接口返回的代码-200 成功

    public static final String SIGN_KEY = "xxxxxxxxxxxxxxxxxx";//加密的密钥

    /**
     * oauth2GetUrl = PropertyUtils.getPropertyValue(new File(realPathResolver.get(Constants.JEECMS_CONFIG)), OAUTH2_KEY);
     *
     * @param url        服务名
     * @param parames 参数以json格式传递
     * @throws IOException
     * @author wdy
     * @version ：2016年4月18日 下午4:53:25
     */
    public static JSONObject sendPost(String url, Map<String, Object> parames) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        CloseableHttpResponse response = null;
        JSONObject json = null;//返回值
        try {
            parames = signString(parames);
            List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
            for (String key : parames.keySet()) {
                String value = null;
                Object val = parames.get(key);
                if (null != val)
                    value = val.toString();
                list.add(new BasicNameValuePair(key, value));
            }
            httppost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            response = httpclient.execute(httppost);
            HttpEntity httpEntity = response.getEntity();
            String html = EntityUtils.toString(httpEntity, "UTF-8");
            json = JSONObject.parseObject(html);
        } catch (ClientProtocolException e) {
            throw new ClientProtocolException(e);
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            response.close();
        }
        return json;
    }

    /**
     * 判断该接口的请求是否是成功的
     * 如果成功，根据不同的接口获取data信息
     * 如果失败，获取message信息
     *
     * @param json
     * @return
     * @author wdy
     * @version ：2016年10月12日 上午10:39:50
     */
    public static boolean isSuccess(JSONObject json) {
        boolean success = false;
        if (null != json) {
            Object code = json.get(CODE);
            if (null != code) {
                int codeInt = ((Integer) code).intValue();
                if (SUCCESS_CODE == codeInt) {
                    success = true;
                }
            }
        }
        return success;
    }

    protected static Map<String, Object> signString(Map<String, Object> parames) {
        Map<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
        if (null != parames) {
            Set<String> keySet = parames.keySet();
            Iterator<String> iter = keySet.iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                String value = null;
                Object val = parames.get(key);
                if (null != val)
                    value = val.toString();
                map.put(key, value);
            }
        } else {
            parames = new HashMap<String, Object>();
        }

        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key).append("=").append(map.get(key));
        }
        sb.append(SIGN_KEY);
        String sign = MD5.MD5Encode(sb.toString()).toLowerCase();
        parames.put("sign", sign);

        return parames;
    }


    public static String signKey(Map<String, Object> parames) {
        Map<String, Object> newP = signString(parames);
        return (String) newP.get("sign");
    }

    public static void main(String[] args) {
        try {
            Map<String, Object> parames = new HashMap<String, Object>();
            parames.put("service", "User.Login");
            parames.put("userName", "feemoo");
            parames.put("password", "987654321");
//			parames.put("token", "daded03502b391fd3472131cae28f65c");
            JSONObject json = sendPost("http://h5.mixs.cn/h5game/", parames);
            System.out.println(json.toJSONString());
            JSONObject js = json.getJSONObject("data");
            String detail = js.getJSONObject("userInfo").toJSONString();

            System.out.println("用户基本信息：" + (detail));

            String str = json.toJSONString();

            System.out.println("这是我取到的" + (str));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送get请求
     *
     * @param url
     * @return
     * @throws Exception
     * @author wdy
     * @version ：2016年10月21日 上午10:44:09
     */
    public static String sendGet(String url) throws Exception {
        String html = null;
        HttpGet httpGet = new HttpGet(new URI(url));
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse httpresponse = httpclient.execute(httpGet);
        HttpEntity entity = httpresponse.getEntity();
        html = EntityUtils.toString(entity, "UTF-8");
        httpGet = null;

        return html;
    }

    /**
     * 发送xml数据请求到server端
     *
     * @param url       xml请求数据地址
     * @param xmlString 发送的xml数据流
     * @return null发送失败，否则返回响应内容
     */
    @SuppressWarnings("deprecation")
    public static String xmlPost(String url, String xmlString) {
        //关闭   
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");

        //创建httpclient工具对象   
        HttpClient client = new HttpClient();
        //创建post请求方法   
        PostMethod myPost = new PostMethod(url);
        //设置请求超时时间   
        client.setConnectionTimeout(300 * 1000);
        String responseString = null;
        try {
            //设置请求头部类型   
            myPost.setRequestHeader("Content-Type", "text/xml");
            myPost.setRequestHeader("charset", "utf-8");

            //设置请求体，即xml文本内容，注：这里写了两种方式，一种是直接获取xml内容字符串，一种是读取xml文件以流的形式   
            myPost.setRequestBody(xmlString);

            myPost.setRequestEntity(new StringRequestEntity(xmlString, "text/xml", "utf-8"));
            int statusCode = client.executeMethod(myPost);
            if (statusCode == HttpStatus.SC_OK) {
                BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int count = 0;
                while ((count = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, count);
                }
                byte[] strByte = bos.toByteArray();
                responseString = new String(strByte, 0, strByte.length, "utf-8");
                bos.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        myPost.releaseConnection();
        return responseString;
    }

}
