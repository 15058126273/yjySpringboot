package com.yjy.test.game.service.impl;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.dao.LoginLogDao;
import com.yjy.test.game.entity.LoginLog;
import com.yjy.test.game.service.LoginLogService;
import com.yjy.test.game.util.FrontUtils;
import com.yjy.test.game.web.WebException;
import nl.bitwalker.useragentutils.UserAgent;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLog, Long>
        implements LoginLogService {

    public static final Logger logger = LoggerFactory.getLogger(LoginLogServiceImpl.class);

    @Autowired
    public void setLoginLogDao(LoginLogDao dao) {
        super.setDao(dao);
    }

    public LoginLogDao getLoginLogDao() {
        return (LoginLogDao) super.getDao();
    }

    @Override
    public LoginLog loginLogInsert(LoginLog loginLog) throws WebException {
        LoginLog log = null;
        try {
            if (null == loginLog)
                return log;
            String agent = loginLog.getAgent();//客户端信息
            if (StringUtils.isNotBlank(agent)) {
                String customerModel = FrontUtils.getCustomerModel(agent);//手机型号
                loginLog.setCustomerModel(customerModel);

                UserAgent userAgent = UserAgent.parseUserAgentString(agent);
                if (userAgent != null) {
                    String browserName = userAgent.getBrowser().getName();//浏览器类型
                    String operatingSystem = String.valueOf(userAgent.getOperatingSystem().getName());//操作系统类型
                    String browserVersion = String.valueOf(userAgent.getBrowserVersion());//浏览器版本
                    boolean isMobileDevice = userAgent.getOperatingSystem().isMobileDevice();//是否是移动设备
                    loginLog.setBrowserName(browserName);
                    loginLog.setBrowserVersion(browserVersion);
                    loginLog.setIsMobile(isMobileDevice);
                    loginLog.setOperatingSystem(operatingSystem);
                }
            }
            //登录结果是否成功
            boolean success = loginLog.getSuccess();
            if (success) {
                loginLog.setCategory(LoginLog.CATEGORY_SUCCESS);
            } else {
                loginLog.setCategory(LoginLog.CATEGORY_FAILURE);
            }
            log = save(loginLog);
        } catch (Exception e) {
            logger.error("保存前台用户登录信息出错", e);
            throw new WebException("保存前台用户登录信息出错");
        }
        return log;
    }

}
