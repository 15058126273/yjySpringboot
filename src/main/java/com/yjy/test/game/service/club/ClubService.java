package com.yjy.test.game.service.club;

import com.yjy.test.base.BaseService;
import com.yjy.test.game.entity.club.Club;

import java.util.List;

/**
 * 俱乐部管理
 *
 * @author yjy
 * Created on 2017年12月5日 上午10:43:15
 */
public interface ClubService extends BaseService<Club, Long> {

    /**
     * 创建俱乐部
     *
     * @param userId    用户id
     * @param headImg   头像
     * @param name      俱乐部名称
     * @param address   所在地
     * @param canJoin   是否允许加入
     * @param condition 申请条件
     * @param longitude 经度
     * @param latitude  纬度
     * @param introduce 简介
     * @return 俱乐部信息
     * @author yjy
     * Created on 2017年12月5日 下午1:25:47
     */
    Club createClub(Long userId, String headImg, String name, String address, Integer canJoin, Integer condition, Double longitude,
                    Double latitude, String introduce);

    /**
     * 获取推荐俱乐部列表
     *
     * @param userId    用户id
     * @param longitude 经度
     * @param latitude  纬度
     * @param pageSize  条数
     * @return 俱乐部列表
     * @author yjy
     * Created on 2017年12月5日 下午2:06:31
     */
    List<Club> findRecommend(Long userId, Double longitude, Double latitude, Integer pageSize);

    /**
     * 搜索俱乐部
     *
     * @param code 编号
     * @return 俱乐部
     * @author yjy
     * Created on 2017年12月6日 上午10:14:56
     */
    Club findSearch(String code);

    /**
     * 获取我的俱乐部列表
     *
     * @param userId   用户id
     * @param pageNo   页号
     * @param pageSize 条数
     * @return 俱乐部列表
     * @author yjy
     * Created on 2017年12月5日 下午2:06:31
     */
    List<Club> findMyList(Long userId, Integer pageNo, Integer pageSize);

    /**
     * 更新当前成员数
     *
     * @param id        俱乐部id
     * @param changeNum 变动数
     * @author yjy
     * Created on 2017年12月5日 下午4:21:37
     */
    void changeUserCount(Long id, Integer changeNum);

    /**
     * 解散俱乐部
     *
     * @param club 俱乐部
     * @author yjy
     * Created on 2017年12月15日 上午10:44:21
     */
    void dissolve(Club club);

    /**
     * 获取用户创建的俱乐部数量
     *
     * @param userId 用户id
     * @return 数量
     * @author yjy
     * Created on 2018年1月25日 下午3:03:27
     */
    int findCreateCount(Long userId);

}
