package com.yjy.test.game.service.impl;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.dao.FeedbackDao;
import com.yjy.test.game.entity.Feedback;
import com.yjy.test.game.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback, Integer> implements FeedbackService {

    @Autowired
    public void setFeedbackDao(FeedbackDao dao) {
        super.setDao(dao);
    }

    public FeedbackDao getFeedbackDao() {
        return (FeedbackDao) super.getDao();
    }
}
