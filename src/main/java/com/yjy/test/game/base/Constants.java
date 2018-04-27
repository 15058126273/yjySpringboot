package com.yjy.test.game.base;

/**
 * 常量
 */
public class Constants {

    public static final String GAME_NAME = "hb_hotpot_club";

    // 分隔符
    public static final String SPLIT = "_";

    // Redis 名
    private static final String REDIS = "redis";

    // Redis key前缀
    public static final String PREFIX_REDIS = GAME_NAME + SPLIT + REDIS + SPLIT;

    public static String JSAPI_TICKET = "jsapi_ticket";

    public static long JSAPI_TIMEOUT = 3600000L;//设置缓存过期时间 3600秒  （微信端jsapi_ticket过期时间为7200秒）

    public static final String ADMIN_USER = "admin.user";//管理员

    public static final String FRONT_USER = "front.user";//前端代理用户

    public static String OPEN_ID_KEY = "openId";//微信的唯一标示

    public static final String LOCAL_EXCEPTION = "localException";//本地异常提醒

    public static final String API_URL = "apiUrl";//接口的请求地址

    public static final String VISIT_TOKEN = "visitToken";//访问的用户授权的token

    public static final String API_PLAYER_CENTER_URL = "api.playercenter.url";//用户中心的接口的请求地址

    public static final Integer GAME_PLATFORM = Integer.valueOf(3);//该游戏的代码

    /***************系统配置参数start*****************/

    public static String ALL_CONFIG_MAP = PREFIX_REDIS + "allConfigMap";//系统配置参加集合

    public static String PRE_CONFIG = "config_";

    public static final String CONFIG_ROOM_CREATE_POWER = "roomCreatePower";//房间开启的开关，可以关闭

    public static final String CONFIG_BROADCAST_URL = "broadcastUrl";//发送广播的地址

    public static final String CONFIG_CUSTOMER_SERVICE = "customerService";//客服号码

    public static final String CONFIG_READY_LAST_TIME = "readyLastTime";//自动准备时间

    public static final String CONFIG_VIE_LAST_TIME = "vieLastTime";//抢庄持续时间

    public static final String CONFIG_WAGER_LAST_TIME = "wagerLastTime";//下注持续时间

    public static final String CONFIG_SEND_LAST_TIME = "sendLastTime";//发牌持续时间

    public static final String CONFIG_OPEN_LAST_TIME = "openLastTime";//开一副牌持续时间

    public static final String CONFIG_MATCH_LAST_TIME = "matchLastTime";//配牌持续时间

    public static final String CONFIG_FIGURE_LAST_TIME = "figureLastTime";//结算持续时间

    public static final String CONFIG_DEAL_VIE_LAST_TIME = "dealVieLastTime";//处理抢庄间隔时间

    public static final String CONFIG_ROOM_GAMES = "roomGames";//一个房间可以玩的局数

    public static final String CONFIG_ROOM_COSTS = "roomCosts";//玩的圈数对应的钻石

    public static final String CONFIG_WAGER_RANGE = "wagerRange";//下注范围

    public static final String CONFIG_BASE_WAGER_RANGE = "baseWagerRange";//基础下注范围

    public static final String CONFIG_MULTIPLE_RANGE = "multipleRange";//下注倍数范围

    public static final String CONFIG_PUSH_WAGER_RANGE = "pushWagerRange"; //推注倍数范围

    public static final String CONFIG_BEFORE_HAND_LAST_TIME = "beforehandLastTime";//预发牌持续时间

    public static final String CONFIG_BANKER_MULTIPLE_RANGE = "bankerMultipleRange";//抢庄倍数范围

    public static final String CONFIG_COST_MULTIPLE = "costMultiple";//不同底分对应的开放钻石倍数

    public static final String CONFIG_BASE_COST_MULTIPLE = "baseCostMultiple";//不同底分对应的开放钻石倍数


    public static final String CONFIG_SHARE_TITLE = "shareTitle";//分享标题

    public static final String CONFIG_SHARE_IMG = "shareImg";//分享图片

    public static final String CONFIG_SHARE_LINK = "shareLink";//分享链接

    public static final String CONFIG_SHARE_DESC = "shareDesc";//分享描述

    public static final String CONFIG_ROOM_SHARE_TITLE = "roomShareTitle";//房间分享标题

    public static final String CONFIG_ROOM_SHARE_IMG = "roomShareImg";//房间分享图片

    public static final String CONFIG_ROOM_SHARE_DESC = "roomShareDesc";//房间分享描述

    public static final String CONFIG_RESULT_SHARE_TITLE = "resultShareTitle";//游戏结果分享标题

    public static final String CONFIG_RESULT_SHARE_IMG = "resultShareImg";//游戏结果分享图片

    public static final String CONFIG_RESULT_SHARE_DESC = "resultShareDesc";//游戏结果分享描

    /********************************/
}
