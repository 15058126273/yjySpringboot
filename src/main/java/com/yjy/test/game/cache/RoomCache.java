package com.yjy.test.game.cache;

import com.alibaba.druid.filter.logging.Log4jFilter;
import com.yjy.test.base.BaseClass;
import com.yjy.test.game.base.Constants;
import com.yjy.test.game.redis.RedisUtils;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
import org.hibernate.jpa.internal.util.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.logging.AutoConfigurationReportLoggingInitializer;
import org.springframework.boot.logging.DeferredLog;
import redis.clients.jedis.Jedis;

/**
 * 房间缓存管理
 * Created by yjy on 2017/4/28.
 */
public class RoomCache extends BaseClass {

    private static final Logger log = LoggerFactory.getLogger(RoomCache.class);
    
    /*********单例模式*******************************************/
    private RoomCache() {
    }

    private static final RoomCache instance = new RoomCache();

    public static RoomCache getInstance() {
        return instance;
    }

    /**
     * 缓存key
     */
    private static final String CACHE_KEY = Constants.PREFIX_REDIS + "roomMap";

    /**
     * 检测房间号是否存在
     *
     * @return roomNo 房间号
     */
    public boolean exist(String roomNo) {
        Jedis jedis = RedisUtils.getInstance().getJedis();
        boolean exist = jedis.hexists(CACHE_KEY, roomNo);
        jedis.close();
        return exist;
    }

    /**
     * 添加一个房间
     *
     * @param roomNo     房间号
     * @param serverAddr 服务地址
     */
    public void addRoom(String roomNo, String serverAddr) {
        if (roomNo != null) {
            Jedis jedis = RedisUtils.getInstance().getJedis();
            if (!jedis.hexists(CACHE_KEY, roomNo)) {
                jedis.hset(CACHE_KEY, roomNo, serverAddr);
            } else {
                log.error("addRoom canceled, cause room already exist");
            }
            jedis.close();
        } else {
            log.error("addRoom canceled, cause roomNo is null");
        }
    }

    /**
     * 获取房间所在逻辑服务器地址
     *
     * @param roomNo 房间号
     * @return serverAddr
     */
    public String getServerAddr(String roomNo) {
        if (roomNo != null) {
            return RedisUtils.getInstance().getHash(CACHE_KEY, roomNo);
        } else {
            log.error("getServerAddr canceled, cause roomNo is null");
        }
        return null;
    }

}
