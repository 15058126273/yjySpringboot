package com.yjy.test.game.tencent;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.yjy.test.game.tencent.pojo.WxConfig;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * 微信分享实现加密
 *
 * @author wdy
 * @version ：2015年11月18日 下午4:41:56
 */
public class WxShareSign {

    public static Map<String, String> sign(String appId, String appSecret, String url) {

        Map<String, String> ret = null;
        if (StringUtils.isBlank(appId) || StringUtils.isBlank(appSecret) || StringUtils.isBlank(url)) {
            return ret;
        }
        ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";
        String jsapi_ticket = getJsapi(appId, appSecret);
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("appId", appId);
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    public static WxConfig sign(String jsapi_ticket, String url) {

        WxConfig wxConfig = null;
        if (StringUtils.isBlank(url)) {
            return wxConfig;
        }
        wxConfig = new WxConfig();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        wxConfig.setJsapiTicket(jsapi_ticket);
        wxConfig.setNonceStr(nonce_str);
        wxConfig.setTimestamp(timestamp);
        wxConfig.setSignature(signature);
        return wxConfig;
    }

    private static String getAccessToken(String appId, String appSecret) {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
        String accessToken = null;
        try {
            JSONObject demoJson = httpGet(url);
            accessToken = demoJson.getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public static String getJsapi(String appId, String appSecret) {
        String accessToken = getAccessToken(appId, appSecret);
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + accessToken;
        String jsapi = null;
        try {
            JSONObject demoJson = httpGet(url);
            jsapi = demoJson.getString("ticket");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsapi;

    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private static JSONObject httpGet(String url) {
        JSONObject jsonObject = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            jsonObject = JSONObject.parseObject(message);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


}

