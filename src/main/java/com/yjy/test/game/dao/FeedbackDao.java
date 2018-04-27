package com.yjy.test.game.dao;

import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.Feedback;
import org.springframework.stereotype.Repository;

/**
 * 信息反馈的dao
 * @author wdy
 * @version ：2017年5月22日 上午11:30:08
 */
@Repository
public interface FeedbackDao extends BaseJpaRepository<Feedback, Integer> {

}
