package com.yjy.test.game.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yjy.test.game.base.Constants;
import com.yjy.test.game.tencent.pojo.WxConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 消费码接口管理
 *
 * @author wdy
 * @version ：2017年1月18日 上午11:24:33
 */
public class APICodeUtils {

    private static final Logger log = LoggerFactory.getLogger(APICodeUtils.class);

    /**
     * 回调标记充值是否成功
     *
     * @return
     * @author wdy
     * @version ：2017年6月2日 上午10:12:57
     */
    public static boolean callbackSucc(String url, String orderNo, Long logId, Integer num, Integer result) {
        boolean succ = false;
        if (null == logId || null == num || null == result || (StringUtils.isBlank(orderNo) && Integer.valueOf(1).equals(result)))
            return succ;

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("orderNo", orderNo);
        parames.put("logId", logId);
        parames.put("num", num);
        parames.put("result", result);
        try {
            JSONObject response = HttpSendUtils.sendPost(url, parames);
            log.error("callback reponse: " + response.toJSONString());
            if (null != response) {
                String status = response.getString("status");
                if ("1".equals(status)) {
                    succ = true;
                }
            }
        } catch (Exception e) {
            log.error("回调存值接口发生错误", e);
        }
        return succ;
    }

    /**
     * 发送广播信息
     *
     * @param url
     * @param message
     * @return
     * @author wdy
     * @version ：2017年6月19日 下午5:18:52
     */
    public static boolean sendBroadcast(String url, String message) {
        boolean succ = false;
        if (null == url || StringUtils.isBlank(message))
            return succ;

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("from_inner", "1");
        parames.put("type", "broadcast");
        parames.put("message", message);
        String signature = MD5.MD5Encode("game_broad" + message);
        parames.put("signature", signature);
        try {
            JSONObject response = HttpSendUtils.sendPost(url, parames);
            log.error("sendBroadcast reponse: " + response.toJSONString());
            if (null != response) {
                String status = response.getString("status");
                if ("1".equals(status)) {
                    succ = true;
                }
            }
        } catch (Exception e) {
            log.error("发送广播信息发生错误", e);
        }
        return succ;
    }

    public static WxConfig wechatShare(String url, String visitUrl) {
        WxConfig wxConfig = null;
        if (null == url || StringUtils.isBlank(visitUrl))
            return wxConfig;

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("visitUrl", visitUrl);
        try {
            JSONObject response = HttpSendUtils.sendPost(url, parames);
            log.error("wechatShare reponse: " + response.toJSONString());
            if (null != response) {
                String status = response.getString("status");
                if ("1".equals(status)) {
                    String data = response.getString("data");
                    if (StringUtils.isNotBlank(data)) {
                        wxConfig = JSON.parseObject(data, WxConfig.class);
                        return wxConfig;
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取微信分享校验信息发生错误", e);
        }
        return wxConfig;
    }

    /**
     * 返回json然后处理成对象
     *
     * @param url
     * @param start
     * @param end
     * @return
     * @author wdy
     * @version ：2017年7月24日 下午5:50:15
     */
    public static String loginLogs(String url, Date start, Date end, Integer pageNo) {
        String json = null;
        if (null == url)
            return json;

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put(Constants.API_URL, "loginLog.list");
        parames.put("platform", Constants.GAME_PLATFORM);
        parames.put("start", start);
        parames.put("end", end);
        parames.put("pageNo", pageNo);

        try {
            JSONObject response = HttpSendUtils.sendPost(url, parames);
            log.error("wechatShare reponse: " + response.toJSONString());
            if (null != response) {
                String status = response.getString("status");
                if ("1".equals(status)) {
                    String data = response.getString("data");
                    if (StringUtils.isNotBlank(data)) {
                        return data;
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取微信分享校验信息发生错误", e);
        }
        return json;
    }

    /**
     * 获取消费列表
     *
     * @param url
     * @param start
     * @param end
     * @param pageNo
     * @return
     * @author wdy
     * @version ：2017年7月28日 下午5:34:59
     */
    public static String consumeLogs(String url, Long userId, Date start, Date end, Integer pageNo) {
        String json = null;
        if (null == url)
            return json;

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put(Constants.API_URL, "consumeLog.list");
        parames.put("platform", Constants.GAME_PLATFORM);
        parames.put("userId", userId);
        parames.put("start", start);
        parames.put("end", end);
        parames.put("pageNo", pageNo);

        try {
            JSONObject response = HttpSendUtils.sendPost(url, parames);
            log.error("consumeLog.list reponse: " + response.toJSONString());
            if (null != response) {
                String status = response.getString("status");
                if ("1".equals(status)) {
                    String data = response.getString("data");
                    if (StringUtils.isNotBlank(data)) {
                        return data;
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取玩家消费列表信息发生错误", e);
        }
        return json;
    }

    /**
     * 玩家消费统计信息
     *
     * @param url
     * @param start
     * @param end
     * @return
     * @author wdy
     * @version ：2017年7月28日 下午5:36:25
     */
    public static String consumeStatis(String url, Date start, Date end) {
        String json = null;
        if (null == url)
            return json;

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put(Constants.API_URL, "consumeLog.statis");
        parames.put("platform", Constants.GAME_PLATFORM);
        parames.put("start", start);
        parames.put("end", end);

        JSONObject response = null;
        try {
            response = HttpSendUtils.sendPost(url, parames);
            if (null != response) {
                String status = response.getString("status");
                if ("1".equals(status)) {
                    String data = response.getString("data");
                    if (StringUtils.isNotBlank(data)) {
                        return data;
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取玩家消费统计信息发生错误", e);
        }
        log.error("consumeLog.statis reponse: " + response.toJSONString());
        return json;
    }

    /**
     * 获取游戏当前玩家数和房间数
     *
     * @param url
     * @return
     * @author wdy
     * @version ：2017年9月13日 下午5:50:29
     */
    public static String gameCurrent(String url) {
        String succ = null;
        if (null == url)
            return succ;

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("from_inner", "1");
        parames.put("type", "onlineCount");
        try {
            JSONObject response = HttpSendUtils.sendPost(url, parames);
            log.error("gameCurrent reponse: " + response.toJSONString());
            if (null != response) {
                String status = response.getString("status");
                if ("1".equals(status)) {
                    succ = response.getString("data");
                }
            }
        } catch (Exception e) {
            log.error("获取当前游戏信息发生错误", e);
        }
        return succ;
    }

}
