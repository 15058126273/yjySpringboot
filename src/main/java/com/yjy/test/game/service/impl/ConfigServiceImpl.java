package com.yjy.test.game.service.impl;

import java.util.List;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.dao.ConfigDao;
import com.yjy.test.game.entity.Config;
import com.yjy.test.game.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ConfigServiceImpl extends BaseServiceImpl<Config, Integer> implements ConfigService {

    @Autowired
    public void setConfigDao(ConfigDao dao) {
        super.setDao(dao);
    }

    public ConfigDao getConfigDao() {
        return (ConfigDao) super.getDao();
    }

    public Config findThisConfig() {
        List<Config> list = findAll();
        Config config = new Config();
        if (list != null && list.size() > 0) {
            config = list.get(0);
        }
        //config.setContextPath("/sellers");
        return config;
    }

}
