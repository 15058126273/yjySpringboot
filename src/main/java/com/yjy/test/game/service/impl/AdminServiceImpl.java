package com.yjy.test.game.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.dao.AdminDao;
import com.yjy.test.game.entity.Admin;
import com.yjy.test.game.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin, Integer> implements AdminService {

    @Autowired
    public void setAdminDao(AdminDao dao) {
        super.setDao(dao);
    }

    public AdminDao getAdminDao() {
        return (AdminDao) super.getDao();
    }

    @Override
    public Admin saveOne(Admin adminUser, HttpServletRequest request) throws Exception {
        adminUser = save(adminUser);
        return adminUser;
    }

    @Override
    public Admin updateOne(Admin adminUser, HttpServletRequest request) throws Exception {
        adminUser = (Admin) update(adminUser);
        return adminUser;
    }

}
