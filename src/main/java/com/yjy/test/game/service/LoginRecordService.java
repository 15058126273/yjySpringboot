package com.yjy.test.game.service;

import com.yjy.test.base.BaseService;
import com.yjy.test.game.entity.LoginRecord;

import javax.servlet.http.HttpServletRequest;


public interface LoginRecordService extends BaseService<LoginRecord, Integer> {

    public LoginRecord loginLog(HttpServletRequest request, Integer id);

    /**
     * 保存新的登录记录
     *
     * @param loginRecord
     * @return
     * @author wdy
     * @version ：2017年2月10日 上午10:30:00
     */
    public LoginRecord loginLogInsert(LoginRecord loginRecord);

}
