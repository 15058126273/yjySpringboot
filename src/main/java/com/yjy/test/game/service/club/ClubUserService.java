package com.yjy.test.game.service.club;

import com.yjy.test.base.BaseService;
import com.yjy.test.game.base.Result;
import com.yjy.test.game.entity.club.Club;
import com.yjy.test.game.entity.club.ClubUser;

import java.util.List;

/**
 * 俱乐部成员管理
 *
 * @author yjy
 * Created on 2017年12月5日 上午10:43:15
 */
public interface ClubUserService extends BaseService<ClubUser, Long> {

    /**
     * 新建一个俱乐部成员
     *
     * @param club   俱乐部
     * @param userId 用户id
     * @param role   角色
     * @param status 状态
     * @param remark 备注
     * @author yjy
     * Created on 2017年12月5日 下午2:56:56
     */
    ClubUser createClubUser(Club club, Long userId, Integer role, Integer status, String remark);

    /**
     * 获取俱乐部成员列表
     *
     * @param clubId   俱乐部id
     * @param pageNo   页号
     * @param pageSize 条数
     * @return
     * @author yjy
     * Created on 2017年12月6日 上午9:27:12
     */
    List<ClubUser> findMemberList(Long clubId, Integer pageNo, Integer pageSize);

    /**
     * 申请加入俱乐部
     *
     * @param club   俱乐部信息
     * @param userId 用户id
     * @param remark 备注
     * @author yjy
     * Created on 2017年12月5日 下午3:31:23
     */
    Result applyJoin(Club club, Long userId, String remark);

    /**
     * 获取俱乐部成员
     *
     * @param userId 用户id
     * @param clubId 俱乐部id
     * @return 成员信息
     * @author yjy
     * Created on 2017年12月5日 下午3:17:04
     */
    ClubUser findByUserIdAndClubId(Long userId, Long clubId);

    /**
     * 移除成员
     *
     * @param clubUser 成员
     * @param operId   操作人id
     * @author yjy
     * Created on 2017年12月5日 下午5:21:02
     */
    void removePlayer(ClubUser clubUser, Long operId);

    /**
     * 退出俱乐部
     *
     * @param clubUser 成员
     * @author yjy
     * Created on 2017年12月5日 下午5:30:01
     */
    void quitClub(ClubUser clubUser);

    /**
     * 更新俱乐部成员状态
     *
     * @param clubUser 俱乐部成员
     * @param status   状态
     * @author yjy
     * Created on 2017年12月5日 下午4:13:55
     */
    void updateStatus(ClubUser clubUser, Integer status);

}
