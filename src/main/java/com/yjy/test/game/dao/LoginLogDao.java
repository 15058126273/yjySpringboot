package com.yjy.test.game.dao;

import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.LoginLog;
import org.springframework.stereotype.Repository;

/**
 * 分发钻石的dao层
 * @author wdy
 * @version ：2017年5月11日 下午6:16:38
 */
@Repository
public interface LoginLogDao extends BaseJpaRepository<LoginLog, Long> {

}
