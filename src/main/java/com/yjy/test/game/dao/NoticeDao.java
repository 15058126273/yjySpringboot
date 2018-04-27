package com.yjy.test.game.dao;

import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.Notice;
import org.springframework.stereotype.Repository;

/**
 * 系统公告的dao
 *
 * @author wdy
 * @version ：2017年5月22日 上午11:30:08
 */
@Repository
public interface NoticeDao extends BaseJpaRepository<Notice, Integer> {

}
