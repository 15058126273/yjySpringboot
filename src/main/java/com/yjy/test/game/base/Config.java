package com.yjy.test.game.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-25 17:49
 */
@Component
@PropertySource(value = "classpath:/config/game/config.properties")
public class Config {

    public static String playerCenterUrl;

    @Value("${api.playercenter.url}")
    public void setPlayerCenterUrl(String playerCenterUrl) {
        this.playerCenterUrl = playerCenterUrl;
    }

}
