package com.yjy.test.game.service.impl;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.http.HttpServletRequest;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.dao.LoginRecordDao;
import com.yjy.test.game.entity.Admin;
import com.yjy.test.game.entity.LoginRecord;
import com.yjy.test.game.service.AdminService;
import com.yjy.test.game.service.LoginRecordService;
import com.yjy.test.game.util.concurrent.ConsumerLoginRecord;
import com.yjy.test.game.util.concurrent.Producer;
import com.yjy.test.game.web.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员登录记录
 *
 * @author wsc
 * @version 2016年3月30日
 */
@Service
@Transactional
public class LoginRecordServiceImpl extends BaseServiceImpl<LoginRecord, Integer> implements LoginRecordService {

    public static final Logger logger = LoggerFactory.getLogger(LoginRecordServiceImpl.class);


    @Autowired
    public void setAdminLoginRecordDao(LoginRecordDao dao) {
        super.setDao(dao);
    }

    public LoginRecordDao getAdminLoginRecordDao() {
        return (LoginRecordDao) super.getDao();
    }

    @Autowired
    private AdminService adminUserService;

    public LoginRecord save(int category, String ip, Date date, Integer userId) {

        LoginRecord log = new LoginRecord();
        log.setLoginIp(ip);
        log.setLoginDate(date);
        log.setUserId(userId);
        log.setContent(LoginRecord.LOGIN_SUCCESS_TITLE);
        log.setCategory(Integer.valueOf(category));
        visitLog(log);
        return log;
    }

    @Override
    public LoginRecord loginLog(HttpServletRequest request, Integer userId) {
        String ip = RequestUtils.getIpAddr(request);
        Date date = new Date();
        Integer category = Integer.valueOf(1);
        LoginRecord log = save(category, ip, date, userId);
        return log;
    }

    @Override
    public LoginRecord loginLogInsert(LoginRecord loginRecord) {
        LoginRecord log = null;
        try {
            if (null == loginRecord)
                return log;
            Integer adminId = loginRecord.getUserId();
            if (null == adminId)
                return log;

            Admin adminUser = adminUserService.findById(adminId);
            loginRecord.setName(adminUser.getUserName());

            log = save(loginRecord);
            Date date = loginRecord.getLoginDate();
            adminUser.setLastLoginTime(date);
            adminUserService.update(adminUser);

        } catch (Exception e) {
            logger.error("保存用户登录信息出错", e);
        }
        return log;
    }


    @SuppressWarnings("rawtypes")
    public static BlockingQueue visitLogQueue = new ArrayBlockingQueue(10000);

    /**
     * 访问次数+1
     *
     * @param loginRecord
     * @author wdy
     * @version ：2017年1月12日 下午8:46:35
     */
    @SuppressWarnings("rawtypes")
    protected void visitLog(LoginRecord loginRecord) {
        BlockingQueue queue = visitLogQueue;
        Producer producer = new Producer(queue, loginRecord);
        new Thread(producer).start();
        if (!ConsumerLoginRecord.running) {
            ConsumerLoginRecord consumer = new ConsumerLoginRecord(this, queue);
            new Thread(consumer).start();
            ConsumerLoginRecord.running = true;
        }
    }

}
