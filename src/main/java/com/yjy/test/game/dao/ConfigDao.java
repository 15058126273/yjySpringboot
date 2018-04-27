package com.yjy.test.game.dao;

import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.Config;
import org.springframework.stereotype.Repository;

/**
 * 系统参数的配置的dao
 *
 * @author wdy
 * @version ：2017年1月10日 下午3:14:56
 */
@Repository
public interface ConfigDao extends BaseJpaRepository<Config, Integer> {

}
