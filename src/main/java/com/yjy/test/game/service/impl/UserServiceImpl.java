package com.yjy.test.game.service.impl;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.dao.UserDao;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.service.UserService;
import com.yjy.test.game.web.WebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    @Autowired
    public void setDao(UserDao userDao) {
        super.setDao(userDao);
    }

    public UserDao getDao() {
        return (UserDao) super.getDao();
    }

    @Override
    public User findByOpenId(String openId) throws WebException {
        return findByProperty("openId", openId);
    }

    @Override
    public User findByMobile(String mobile) throws WebException {
        return findByProperty("mobile", mobile);
    }

    @Override
    public User findByCode(String code) throws WebException {
        return findByProperty("code", code);
    }

    @Override
    public User findBeanById(Long id) throws WebException {
        return findById(id);
    }

}
