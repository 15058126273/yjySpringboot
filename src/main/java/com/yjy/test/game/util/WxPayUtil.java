package com.yjy.test.game.util;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.yjy.test.game.tencent.WxPaySign;
import com.yjy.test.game.tencent.pojo.WxPaySendData;
import com.yjy.test.game.web.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class WxPayUtil {

    /**
     * 统一下单
     * 获得PrePayId
     *
     * @param body         商品或支付单简要描述
     * @param out_trade_no 商户系统内部的订单号,32个字符内、可包含字母
     * @param total_fee    订单总金额，单位为分
     * @param IP           APP和网页支付提交用户端ip
     * @param notify_url   接收微信支付异步通知回调地址
     * @param openid       用户openId
     * @throws Exception
     * @throws IOException
     */
    public static String unifiedorder(String url, String body, String out_trade_no, Integer total_fee, String ip, String notify_url, String openId) throws Exception {
        /**
         * 设置访问路径
         */
        HttpPost httppost = new HttpPost(url);
        String nonce_str = getNonceStr();//随机数据
        Map<String, Object> parameters = new TreeMap<String, Object>();
        /**
         * 组装请求参数
         * 按照ASCII排序
         */
        parameters.put("appid", Constants.appId);
        parameters.put("body", body);
        parameters.put("mch_id", Constants.mchId);
        parameters.put("nonce_str", nonce_str);
        parameters.put("out_trade_no", out_trade_no);
        parameters.put("notify_url", notify_url);
        parameters.put("spbill_create_ip", ip);
        parameters.put("total_fee", total_fee.toString());
        parameters.put("trade_type", Constants.TRADE_TYPE);
        parameters.put("openid", openId);

        String sign = WxPaySign.getSign(parameters);

        /**
         * 组装XML
         */
        StringBuilder sb = new StringBuilder("");
        sb.append("<xml>");
        setXmlKV(sb, "appid", Constants.appId);
        setXmlKV(sb, "body", body);
        setXmlKV(sb, "mch_id", Constants.mchId);
        setXmlKV(sb, "nonce_str", nonce_str);
        setXmlKV(sb, "notify_url", notify_url);
        setXmlKV(sb, "out_trade_no", out_trade_no);
        setXmlKV(sb, "spbill_create_ip", ip);
        setXmlKV(sb, "total_fee", total_fee.toString());
        setXmlKV(sb, "trade_type", Constants.TRADE_TYPE);
        setXmlKV(sb, "sign", sign);
        setXmlKV(sb, "openid", openId);
        sb.append("</xml>");

        StringEntity reqEntity = new StringEntity(new String(sb.toString().getBytes("UTF-8"), "ISO8859-1"));//这个处理是为了防止传中文的时候出现签名错误
        httppost.setEntity(reqEntity);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpResponse response = httpclient.execute(httppost);
        String strResult = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));

        return strResult;

    }

    //插入XML标签
    public static StringBuilder setXmlKV(StringBuilder sb, String Key, String value) {
        sb.append("<");
        sb.append(Key);
        sb.append(">");

        sb.append(value);

        sb.append("</");
        sb.append(Key);
        sb.append(">");

        return sb;
    }

    //商户订单号
    public static String getOut_trade_no() {
        String prefix = "wxpay";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = sdf.format(new Date());
        // 6位随机数
        String randomStr = iRandom(6);
        return prefix + dateString + randomStr;
    }

    /**
     * 产生固定长度的随机字符串
     *
     * @param count
     * @return
     * @author gwold-stt
     * @date 2017年6月2日 下午1:31:27
     */
    private static String iRandom(int count) {
        String rangeStr = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
        String res = "";
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                res += (char) rangeStr.charAt(iRandom(0, 61));
            }
        }
        return res;
    }

    /**
     * 产生一个[m,n)之间的随机整数
     *
     * @param m
     * @param n
     * @return
     */
    public static int iRandom(int m, int n) { //
        Random random = new Random();
        int small = m > n ? n : m;
        int big = m <= n ? n : m;
        return small + random.nextInt(big - small);
    }

    /**
     * 获取访问者IP
     * <p>
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * <p>
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!!StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!!StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    // 获得随机字符串
    public static String getNonceStr() {
        Random random = new Random();
        return MD5.MD5Encode(String.valueOf(random.nextInt(10000)));
    }

    //时间戳
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    // 组装
    public static Map<String, Object> assemble(WxPaySendData wxPaySendData) {
        String appid = wxPaySendData.getAppid();
        String mch_id = wxPaySendData.getMch_id();
        String nonce_str = wxPaySendData.getNonce_str();
        String out_trade_no = wxPaySendData.getOut_trade_no();
        String total_fee = wxPaySendData.getTotal_fee();
        String trade_type = wxPaySendData.getTrade_type();
        String openid = wxPaySendData.getOpenid();
        String return_code = wxPaySendData.getReturn_code();
        String result_code = wxPaySendData.getResult_code();
        String bank_type = wxPaySendData.getBank_type();
        Integer cash_fee = wxPaySendData.getCash_fee();
        String fee_type = wxPaySendData.getFee_type();
        String is_subscribe = wxPaySendData.getIs_subscribe();
        String time_end = wxPaySendData.getTime_end();
        String transaction_id = wxPaySendData.getTransaction_id();

        //签名验证
        Map<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", appid);
        parameters.put("mch_id", mch_id);
        parameters.put("nonce_str", nonce_str);
        parameters.put("out_trade_no", out_trade_no);
        parameters.put("total_fee", total_fee);
        parameters.put("trade_type", trade_type);
        parameters.put("openid", openid);
        parameters.put("return_code", return_code);
        parameters.put("result_code", result_code);
        parameters.put("bank_type", bank_type);
        parameters.put("cash_fee", cash_fee);
        parameters.put("fee_type", fee_type);
        parameters.put("is_subscribe", is_subscribe);
        parameters.put("time_end", time_end);
        parameters.put("transaction_id", transaction_id);

        return parameters;
    }

}
