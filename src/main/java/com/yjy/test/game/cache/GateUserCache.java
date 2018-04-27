package com.yjy.test.game.cache;

import java.util.Map;

import com.yjy.test.game.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.yjy.test.game.base.Constants.PREFIX_REDIS;

public class GateUserCache {

    private static final Logger log = LoggerFactory.getLogger(GateUserCache.class);

    /*******单例模式*********************************************************/
    private GateUserCache() {
    }

    private static GateUserCache instance = new GateUserCache();

    public static GateUserCache getInstance() {
        return instance;
    }

    // Redis > 分发服务对应其连接数集合
    private static final String REDIS_GATE_USER_MAP = PREFIX_REDIS + "gateUserMap";

    /**
     * 刷新分发服务连接数
     *
     * @param addr      分发服务地址
     * @param userCount 用户连接数
     */
    public void refreshUserCount(String addr, int userCount) {
        if (addr != null) {
            setUserCountToRedis(addr, userCount);
        } else {
            log.error("refreshUserCount canceled, cause addr is null");
        }
    }

    /**
     * 刷新分发服务连接数
     *
     * @param host      分发服务主机
     * @param port      分发服务端口
     * @param userCount 用户连接数
     */
    public void refreshUserCount(String host, int port, int userCount) {
        refreshUserCount(host + ":" + port, userCount);
    }

    /**
     * 获取指定分发服务的连接数
     *
     * @param addr 分发服务地址
     * @return userCount
     */
    public Integer getGateUserCount(String addr) {
        if (addr != null) {
            return getUserCountFromRedis(addr);
        } else {
            log.error("getGateUserCount canceled, cause addr is null");
        }
        return null;
    }

    /**
     * 获取指定分发服务的连接数
     *
     * @param host 分发服务主机
     * @param port 分发服务端口
     * @return userCount
     */
    public Integer getGateUserCount(String host, int port) {
        return getGateUserCount(host + ":" + port);
    }

    /**
     * 获取所有分发服务连接数
     *
     * @return map : addr > userCount
     */
    public Map<String, String> getAllGateUser() {
        Map<String, String> maps = RedisUtils.getInstance().getAllHash(REDIS_GATE_USER_MAP);
        return maps;
    }

    /**
     * 移除一个分发服务
     *
     * @param host 主机
     * @param port 端口
     */
    public void removeGate(String host, int port) {
        removeGate(host + ":" + port);
    }

    /**
     * 移除一个分发服务
     *
     * @param addr 地址
     */
    public void removeGate(String addr) {
        RedisUtils.getInstance().removeHash(REDIS_GATE_USER_MAP, addr);
    }

    /**
     * 将分发服务连接数存入Redis
     *
     * @param addr      分发服务地址
     * @param userCount 用户连接数
     */
    private void setUserCountToRedis(String addr, Integer userCount) {
        RedisUtils.getInstance().setHash(REDIS_GATE_USER_MAP, addr, userCount + "");
    }

    /**
     * 从Redis获取一个分发服务器连接数
     *
     * @param addr 分发服务地址
     */
    private Integer getUserCountFromRedis(String addr) {
        String countStr = RedisUtils.getInstance().getHash(REDIS_GATE_USER_MAP, addr);
        if (countStr != null) {
            return Integer.valueOf(countStr);
        }
        return null;
    }

}
