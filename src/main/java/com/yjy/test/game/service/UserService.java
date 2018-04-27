package com.yjy.test.game.service;

import com.yjy.test.base.BaseService;
import com.yjy.test.game.entity.User;
import com.yjy.test.game.web.WebException;

/**
 * 用户详细信息的service
 *
 * @author wdy
 * @version ：2017年5月11日 下午5:20:47
 */
public interface UserService extends BaseService<User, Long> {

    public User findByOpenId(String openId) throws WebException;
    /**
     * 根据手机号码查询用户
     * @author wdy
     * @version ：2017年5月25日 下午2:19:18
     * @param mobile
     * @return
     * @throws WebException
     */
    public User findByMobile(String mobile)throws WebException;
    /**
     * 根据code查找用户信息
     * @author wdy
     * @version ：2017年6月8日 下午4:14:22
     * @param code
     * @return
     * @throws WebException
     */
    public User findByCode(String code)throws WebException;
    /**
     * 根据id来回去，去除缓冲
     * @author wdy
     * @version ：2017年6月26日 下午5:38:24
     * @param id
     * @return
     * @throws WebException
     */
    public User findBeanById(Long id)throws WebException;

}
