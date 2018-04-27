package com.yjy.test.game.service;


import com.yjy.test.base.BaseService;
import com.yjy.test.game.entity.LoginLog;

/**
 * 钻石分发的service层
 *
 * @author wdy
 * @version ：2017年5月11日 下午6:18:09
 */
public interface LoginLogService extends BaseService<LoginLog, Long> {

    /**
     * 保存新的登录记录
     *
     * @param loginLog
     * @return
     * @author wdy
     * @version ：2017年2月10日 上午10:30:00
     */
    public LoginLog loginLogInsert(LoginLog loginLog);

}
