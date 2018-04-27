package com.yjy.test.game.service;

import com.yjy.test.base.BaseService;
import com.yjy.test.game.entity.Admin;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理员信息服务层
 *
 * @author wdy
 * @version ：2017年1月5日 下午7:21:22
 */
public interface AdminService extends BaseService<Admin, Integer> {

    public Admin saveOne(Admin adminUser, HttpServletRequest request) throws Exception;

    public Admin updateOne(Admin adminUser, HttpServletRequest request) throws Exception;
}
