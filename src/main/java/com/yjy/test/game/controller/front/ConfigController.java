package com.yjy.test.game.controller.front;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cardgame.flower.common.entity.user.UserDetail;
import com.yjy.test.game.base.Config;
import com.yjy.test.game.base.Constants;
import com.yjy.test.game.cache.GateUserCache;
import com.yjy.test.game.cache.RoomCache;
import com.yjy.test.game.redis.RedisUtils;
import com.yjy.test.game.tencent.pojo.ShareConfig;
import com.yjy.test.game.tencent.pojo.WxConfig;
import com.yjy.test.game.util.APICodeUtils;
import com.yjy.test.game.util.FrontUtils;
import com.yjy.test.game.util.SerializeUtils;
import com.yjy.test.game.web.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 平台参数的配置获取
 *
 * @author wdy
 * @version ：2017年5月24日 下午4:42:43
 */
@Controller
@Component(value = "frontConfig")
@RequestMapping("/config")
public class ConfigController extends BaseFrontController {

    private static final Logger log = LoggerFactory.getLogger(ConfigController.class);

    public static final String WECHAT_SHARE_INFO = "game/wechat.jtk";//分享参数接口

    public static final String SERVICE_GATES = "_gateAddrs";//分发服务器对应的房间

    public static final String SERVICE_USER_DETAIL_MAP = "userMap";//玩家所在房间的key

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/room.jtk")
    public void room(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> configMap = (Map<String, String>) RedisUtils.getInstance().getObject(Constants.ALL_CONFIG_MAP);
            Map<String, String> roomMap = new HashMap<String, String>();

            //一个房间可以玩的圈数
            String roomGames = configMap.get(Constants.PRE_CONFIG + Constants.CONFIG_ROOM_GAMES);
            if (StringUtils.isBlank(roomGames))
                roomGames = "1";
            roomMap.put(Constants.CONFIG_ROOM_GAMES, roomGames);

            //可玩的圈数对应的钻石
            String roomCosts = configMap.get(Constants.PRE_CONFIG + Constants.CONFIG_ROOM_COSTS);
            if (StringUtils.isBlank(roomCosts))
                roomCosts = "1";
            roomMap.put(Constants.CONFIG_ROOM_COSTS, roomCosts);

            //房间的下注的限制
            String wagerRange = configMap.get(Constants.PRE_CONFIG + Constants.CONFIG_WAGER_RANGE);
            if (StringUtils.isBlank(wagerRange))
                wagerRange = "1/2";
            roomMap.put(Constants.CONFIG_WAGER_RANGE, wagerRange);

            //房间的基础下注的限制
            String baseWagerRange = configMap.get(Constants.PRE_CONFIG + Constants.CONFIG_BASE_WAGER_RANGE);
            if (StringUtils.isBlank(baseWagerRange))
                baseWagerRange = "1,2,3,4,5";
            roomMap.put(Constants.CONFIG_BASE_WAGER_RANGE, baseWagerRange);

            // 房间的下注底分对应倍数
            String wagerMultiple = configMap.get(Constants.PRE_CONFIG + Constants.CONFIG_MULTIPLE_RANGE);
            if (StringUtils.isBlank(wagerMultiple))
                wagerMultiple = "1,1,1,1,1";
            roomMap.put(Constants.CONFIG_COST_MULTIPLE, wagerMultiple);

            // 房间的底分对应倍数
            String baseWagerMultiple = configMap.get(Constants.PRE_CONFIG + Constants.CONFIG_BASE_COST_MULTIPLE);
            if (StringUtils.isBlank(baseWagerMultiple))
                baseWagerMultiple = "1,1,1,1,1";
            roomMap.put(Constants.CONFIG_BASE_COST_MULTIPLE, baseWagerMultiple);

            //房间的推注范围
            String pushWagerRange = configMap.get(Constants.PRE_CONFIG + Constants.CONFIG_PUSH_WAGER_RANGE);
            if (StringUtils.isBlank(pushWagerRange))
                pushWagerRange = "0,5,10,15";
            roomMap.put(Constants.CONFIG_PUSH_WAGER_RANGE, pushWagerRange);

            //房间的创建的开关
            String roomCreatePower = configMap.get(Constants.PRE_CONFIG + Constants.CONFIG_ROOM_CREATE_POWER);
            if (StringUtils.isBlank(roomCreatePower))
                roomCreatePower = "1";
            roomMap.put(Constants.CONFIG_ROOM_CREATE_POWER, roomCreatePower);

            //客服人员号码
            String customerService = configMap.get(Constants.PRE_CONFIG + Constants.CONFIG_CUSTOMER_SERVICE);
            if (StringUtils.isBlank(customerService))
                customerService = "1";
            roomMap.put(Constants.CONFIG_CUSTOMER_SERVICE, customerService);

            String jsoin = JSON.toJSONString(roomMap);
            ajaxSuccessToJson(response, jsoin);
            return;
        } catch (Exception e) {
            log.error("获取房间设置参数信息出错", e);
        }
        errorAjaxToJson(response, "获取房间参数出错", ErrorCode.ER000401);
        return;
    }

    /**
     * 获取微信分享参数
     *
     * @param visitUrl
     * @param request
     * @param response
     * @author wdy
     * @version ：2017年6月9日 下午4:36:24
     */
    @RequestMapping(value = "/wechat.jtk")
    public void wechat(String visitUrl, HttpServletRequest request, HttpServletResponse response) {
        try {
            String rootUrl = request.getScheme() + "://" + request.getServerName()//服务器地址
                    + request.getContextPath();    //项目名称
            ShareConfig shareConfig = new ShareConfig();

            String shareImg = FrontUtils.configParame(Constants.CONFIG_SHARE_IMG);
            if (StringUtils.isNotBlank(shareImg) && shareImg.indexOf("http") < 0)
                shareImg = rootUrl + shareImg;
            String shareLink = FrontUtils.configParame(Constants.CONFIG_SHARE_IMG);
            String shareTitle = FrontUtils.configParame(Constants.CONFIG_SHARE_TITLE);
            String shareDesc = FrontUtils.configParame(Constants.CONFIG_SHARE_DESC);
            shareConfig.setShareLink(shareLink);
            shareConfig.setShareTitle(shareTitle);
            shareConfig.setShareDesc(shareDesc);
            shareConfig.setShareImg(shareImg);

            String roomShareImg = FrontUtils.configParame(Constants.CONFIG_ROOM_SHARE_IMG);
            if (StringUtils.isNotBlank(roomShareImg) && roomShareImg.indexOf("http") < 0)
                roomShareImg = rootUrl + roomShareImg;
            String roomShareTitle = FrontUtils.configParame(Constants.CONFIG_ROOM_SHARE_TITLE);
            String roomShareDesc = FrontUtils.configParame(Constants.CONFIG_ROOM_SHARE_DESC);
            shareConfig.setRoomShareTitle(roomShareTitle);
            shareConfig.setRoomShareDesc(roomShareDesc);
            shareConfig.setRoomShareImg(roomShareImg);

            String resultShareImg = FrontUtils.configParame(Constants.CONFIG_RESULT_SHARE_IMG);
            if (StringUtils.isNotBlank(resultShareImg) && resultShareImg.indexOf("http") < 0)
                resultShareImg = rootUrl + resultShareImg;
            String resultShareTitle = FrontUtils.configParame(Constants.CONFIG_RESULT_SHARE_TITLE);
            String resultShareDesc = FrontUtils.configParame(Constants.CONFIG_RESULT_SHARE_DESC);
            shareConfig.setResultShareTitle(resultShareTitle);
            shareConfig.setResultShareDesc(resultShareDesc);
            shareConfig.setResultShareImg(resultShareImg);

            //获取分享授权信息
            String wechatUrl = Config.playerCenterUrl;
            wechatUrl += WECHAT_SHARE_INFO;
            WxConfig wxConfig = APICodeUtils.wechatShare(wechatUrl, visitUrl);
            wxConfig.setShareConfig(shareConfig);

            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(WxConfig.class,
                    "appId", "nonceStr", "timestamp", "signature", "shareConfig");
            String jsonString = JSON.toJSONString(wxConfig, filter, SerializerFeature.BrowserCompatible, SerializerFeature.WriteNullStringAsEmpty);
            ajaxSuccessToJson(response, jsonString);
            return;
        } catch (Exception e) {
            log.error("获取微信分享参数出错", e);
        }
        errorAjaxToJson(response, "获取微信分享参数出错", ErrorCode.ER000402);
        return;
    }

    /**
     * 分配服务器信息
     * 如果已有牌局号，直接找到对应的机器推送
     *
     * @param request
     * @param response
     * @author wdy
     * @version ：2017年8月22日 下午5:38:50
     */
    @RequestMapping(value = "/service.jtk")
    public void service(HttpServletRequest request, HttpServletResponse response) {
        String serviceAddr = null;
        try {
            serviceAddr = allocateService(request);
            if (StringUtils.isNotBlank(serviceAddr)) {
                String[] services = serviceAddr.split(":");
                String service = services[0];
                String port = services[1];
                String json = "{\"service\":\"" + service + "\",\"port\":" + port + "}";
                ajaxSuccessToJson(response, json);
                return;
            }
        } catch (Exception e) {
            log.error("分配服务器信息出错", e);
        }
        ajaxErrorToJson(response, null, "分配服务器信息出错", ErrorCode.ER000403);
    }

    /**
     * 检查玩家是否有在房间中
     *
     * @param request
     * @param response
     * @author wdy
     * @version ：2017年10月9日 上午11:43:11
     */
    @RequestMapping(value = "/checkRoom.jtk", method = {RequestMethod.GET, RequestMethod.POST})
    public void checkRoom(HttpServletRequest request, HttpServletResponse response) {
        Long userId = FrontUtils.getCurUserId(request);
        try {
            String roomNo = checkUserRoom(userId);
            String json = null;
            if (StringUtils.isBlank(roomNo)) {
                json = "{\"roomNo\":\"\"}";
            } else {
                json = "{\"roomNo\":\"" + roomNo + "\"}";
            }
            ajaxSuccessToJson(response, json);
            return;
        } catch (Exception e) {
            log.error("检查玩家是否在房间出错", e);
        }
        ajaxErrorToJson(response, null, "检查玩家所在房间信息出错", ErrorCode.ER000405);
    }

    /**
     * 根据分发服务器的数量选择服务器
     *
     * @return
     * @throws Exception
     * @author wdy
     * @version ：2017年9月6日 下午5:56:57
     */
    private String allocateService(HttpServletRequest request) throws Exception {
        String service = null;
        Map<String, String> maps = GateUserCache.getInstance().getAllGateUser();
        if (null == maps || maps.isEmpty()) {
            log.error("cache no service info");
            return service;
        }
        int max = 100000;
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isNotBlank(value)) {
                int num = Integer.valueOf(value).intValue();
                if (num < max) {
                    max = num;
                    service = key;
                }
            }

        }
        return service;
    }

    /**
     * 检查玩家是否在房间
     *
     * @param userId
     * @return
     * @author wdy
     * @version ：2017年10月9日 上午11:55:00
     */
    private String checkUserRoom(Long userId) {
        String roomNo = null;
        if (null == userId)
            return roomNo;
        UserDetail userDetail = null;
        byte[] userByte = RedisUtils.getInstance().getHash((Constants.PREFIX_REDIS + SERVICE_USER_DETAIL_MAP).getBytes(), SerializeUtils.serialize(userId));
        if (null != userByte && userByte.length > 0) {
            userDetail = (UserDetail) SerializeUtils.unserialize(userByte);
        }
        if (null != userDetail) {
            roomNo = userDetail.getRoomNo();
            if ("null".equals(roomNo))
                roomNo = "";
        }
        if (StringUtils.isNotBlank(roomNo)) {
            String service = RoomCache.getInstance().getServerAddr(roomNo);
            if (StringUtils.isBlank(service)) {
                roomNo = null;
            }
        }
        return roomNo;
    }

}
