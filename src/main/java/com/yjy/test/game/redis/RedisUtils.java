package com.yjy.test.game.redis;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.yjy.test.game.util.SerializeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 工具类的使用
 *
 * @author wdy
 * @version ：2017年5月18日 上午10:40:01
 */
@Component
public class RedisUtils implements Serializable {

    private static final long serialVersionUID = -1149678082569464779L;

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);

    private RedisUtils() {}
    private static final RedisUtils instance = new RedisUtils();
    public static RedisUtils getInstance() { return instance; }

    public JedisPool jedisPool;//非切片连接池

    private static Map<String, JedisPool> maps = new HashMap<>();

    /**
     * 初始化Redis连接池
     */
    public Jedis getCurJedis() {
        Jedis jedis = null;
        //连接池方式
//        int count = 0;
//        while (jedis == null && count < 5) {
//            try {
//                JedisPool pool = getPool();
//                jedis = pool.getResource();
//            } catch (Exception e) {
//                log.error("get redis master failed!", e);
//            } finally {
//            	closeJedis(jedis);
//            }
//            count++;
//        }
        jedis = new Jedis(RedisConfig.addr, RedisConfig.port, RedisConfig.timeOut);
        if (StringUtils.isNotBlank(RedisConfig.auth)) {
            jedis.auth(RedisConfig.auth);
        }
        return jedis;
    }

    private JedisPool getPool() {
        String key = RedisConfig.addr + ":" + RedisConfig.port;
        JedisPool pool = null;
        //这里为了提供大多数情况下线程池Map里面已经有对应ip的线程池直接返回，提高效率
        if (maps.containsKey(key)) {
            pool = maps.get(key);
            return pool;
        }
        //这里的同步代码块防止多个线程同时产生多个相同的ip线程池
        synchronized (RedisUtils.class) {
            if (!maps.containsKey(key)) {
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(RedisConfig.maxActive);
                config.setMaxIdle(RedisConfig.maxIdle);
                config.setMaxWaitMillis(RedisConfig.maxWait);
                config.setTestOnBorrow(RedisConfig.testOnBorrow);
                config.setTestOnReturn(RedisConfig.testOnReturn);
                try {
                    /**
                     * 如果你遇到 java.net.SocketTimeoutException: Read timed out
                     * exception的异常信息 请尝试在构造JedisPool的时候设置自己的超时值.
                     * JedisPool默认的超时时间是2秒(单位毫秒)
                     */
                    if (StringUtils.isNotBlank(RedisConfig.auth)) {
                        pool = new JedisPool(config, RedisConfig.addr, RedisConfig.port, 2000, RedisConfig.auth);
                    } else {
                        pool = new JedisPool(config, RedisConfig.addr, RedisConfig.port);
                    }
                    maps.put(key, pool);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pool = maps.get(key);
            }
        }
        return pool;
    }

    /**
     * 释放redis实例到连接池.
     *
     * @param jedis redis实例
     */
    public void closeJedis(Jedis jedis) {
        if (jedis != null) {
            getPool().returnResource(jedis);
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public Jedis getJedis() {
        Jedis resource = null;
        int count = 0;
        while (resource == null && count < 5) {
            try {
                if (jedisPool != null) {
                    resource = jedisPool.getResource();
                } else {
                    initPool();
                    resource = jedisPool.getResource();
                }
            } catch (Exception e) {
                log.error("获取redis实例对象出错", e);
            } finally {
//                returnResource(resource);
            }
            count++;
        }
        return resource;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 关闭连接
     *
     * @param jedis
     * @author wdy
     * @version ：2017年9月21日 下午2:12:31
     */
    public void closeConntion(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 初始化连接池
     *
     * @author wdy
     * @version ：2017年6月7日 下午5:14:00
     */
    public synchronized void initPool() {
        try {
            System.out.println("又出现了一次");
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(RedisConfig.maxActive);
            config.setMaxIdle(RedisConfig.maxIdle);
            config.setMaxWaitMillis(RedisConfig.maxWait);
            config.setTestOnBorrow(RedisConfig.testOnBorrow);
            if (StringUtils.isNotBlank(RedisConfig.auth)) {
                jedisPool = new JedisPool(config, RedisConfig.addr, RedisConfig.port, 30000, RedisConfig.auth);
            } else {
                jedisPool = new JedisPool(config, RedisConfig.addr, RedisConfig.port);
            }
        } catch (Exception e) {
            log.error("获取redis连接对象出错", e);
        }
    }

    public void setString(String key, String value) {
//    	RedisUtils.getJedis().set(key, value);
        getCurJedis().set(key, value);
    }

    /**
     * expx expx的值只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒
     * nxxx的值只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，则只有当key已经存在时才进行set
     *
     * @param key
     * @param value
     * @param exprize
     * @author wdy
     * @version ：2017年7月12日 上午10:26:25
     */
    public void setString(String key, String value, long exprize) {
        Jedis jedis = getCurJedis();
        jedis.set(key, value, "NX", "EX", exprize);
        closeConntion(jedis);
    }

    public String getString(String key) {
//    	String str = RedisUtils.getJedis().get(key);
        Jedis jedis = getCurJedis();
        String str = jedis.get(key);
        closeConntion(jedis);
        return str;
    }

    public void removeString(String key) {
        log.error("delete key:" + key);
        Jedis jedis = getCurJedis();
        jedis.del(key);
        closeConntion(jedis);
    }

    public void setObject(String key, Object object) {
        Jedis jedis = getCurJedis();
        jedis.set(key.getBytes(), SerializeUtils.serialize(object));
        closeConntion(jedis);
    }

    public Object getObject(String key) {
        Jedis jedis = getCurJedis();
        Object object = SerializeUtils.unserialize(jedis.get(key.getBytes()));
        closeConntion(jedis);
        return object;
    }

    public void removeObject(String key) {
        Jedis jedis = getCurJedis();
        jedis.del(key.getBytes());
        closeConntion(jedis);
    }

    public void setHash(String key, String field, String value) {
        Jedis jedis = getCurJedis();
        jedis.hset(key, field, value);
        closeConntion(jedis);
    }

    public String getHash(String key, String field) {
        Jedis jedis = getCurJedis();
        String value = jedis.hget(key, field);
        closeConntion(jedis);
        return value;
    }

    public Map<String, String> getAllHash(String key) {
        Jedis jedis = getCurJedis();
        Map<String, String> value = jedis.hgetAll(key);
        closeConntion(jedis);
        return value;
    }

    public Map<byte[], byte[]> getAllHash(byte[] key) {
        Jedis jedis = getCurJedis();
        Map<byte[], byte[]> value = jedis.hgetAll(key);
        closeConntion(jedis);
        return value;
    }

    public byte[] getHash(byte[] key, byte[] field) {
        Jedis jedis = getCurJedis();
        byte[] value = jedis.hget(key, field);
        closeConntion(jedis);
        return value;
    }

    public void removeHash(String key, String field) {
        Jedis jedis = getCurJedis();
        jedis.hdel(key, field);
        closeConntion(jedis);
    }

}
