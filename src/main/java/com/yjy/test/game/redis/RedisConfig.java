package com.yjy.test.game.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@Validated
public class RedisConfig {

    @NotNull
    public static String addr; //Redis服务器IP

    @NotNull
    public static int port; //Redis的端口号

    public static String auth; //访问密码

    @NotNull
    public static int maxActive = 10; // 可用连接实例的最大数目，默认值为8；如果赋值为-1，则表示不限制；
                                        // 如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    @NotNull
    public static int maxIdle = 200; //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。

    @NotNull
    public static int timeOut = 2000; //连接的超时时间

    @NotNull
    public static int maxWait = 10000; //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；

    @NotNull
    public static boolean testOnBorrow = true; //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；

    @NotNull
    public static boolean testOnReturn = true; //在return一个jedis实例时，是否提前进行validate操作.

    @Value("${redis.addr}")
    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Value("${redis.port}")
    public void setPort(int port) {
        this.port = port;
    }

    @Value("${redis.auth}")
    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Value("${redis.maxActive}")
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    @Value("${redis.maxIdle}")
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    @Value("${redis.timeOut}")
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    @Value("${redis.maxWait}")
    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    @Value("${redis.testOnBorrow}")
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    @Value("${redis.testOnReturn}")
    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public static void printAllConfig() {
        System.out.println("RedisConfig{" +
                "addr='" + addr + '\'' +
                ", port=" + port +
                ", auth='" + auth + '\'' +
                ", maxActive=" + maxActive +
                ", maxIdle=" + maxIdle +
                ", timeOut=" + timeOut +
                ", maxWait=" + maxWait +
                ", testOnBorrow=" + testOnBorrow +
                ", testOnReturn=" + testOnReturn +
                '}');
    }

}
