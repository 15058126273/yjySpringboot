package com.yjy.test.game.service;


import com.yjy.test.base.BaseService;
import com.yjy.test.game.entity.Config;

public interface ConfigService extends BaseService<Config, Integer> {

    public Config findThisConfig();
}
