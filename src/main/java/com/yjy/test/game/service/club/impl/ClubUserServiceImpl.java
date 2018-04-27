package com.yjy.test.game.service.club.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.base.Result;
import com.yjy.test.game.dao.club.ClubUserDao;
import com.yjy.test.game.entity.club.Club;
import com.yjy.test.game.entity.club.ClubMessage;
import com.yjy.test.game.entity.club.ClubUser;
import com.yjy.test.game.service.club.ClubMessageService;
import com.yjy.test.game.service.club.ClubService;
import com.yjy.test.game.service.club.ClubUserService;
import com.yjy.test.game.web.ErrorCode;
import com.yjy.test.util.hibernate.SimplePage;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.yjy.test.game.entity.club.ClubUser.*;


@Service
@Transactional
public class ClubUserServiceImpl extends BaseServiceImpl<ClubUser, Long> implements ClubUserService {

    @Autowired
    private ClubService clubService;
    @Autowired
    private ClubMessageService clubMessageService;

    @Autowired
    public void setClubUserDao(ClubUserDao dao) {
        super.setDao(dao);
    }

    public ClubUserDao getClubUserDao() {
        return (ClubUserDao) super.getDao();
    }

    /**
     * 申请加入俱乐部
     *
     * @param club   俱乐部信息
     * @param userId 用户id
     * @param remark 备注
     * @author yjy
     * Created on 2017年12月5日 下午3:31:23
     */
    public Result applyJoin(Club club, Long userId, String remark) {
        // 尝试从成员中获取用户
        ClubUser clubUser = findByUserIdAndClubId(userId, club.getId());
        // if 用户存在
        if (clubUser != null) {
            // 获取成员当前状态
            int currentStatus = clubUser.getStatus().intValue();
            // if 被踢除状态 || 被拒绝的状态 || 主动离开状态
            if (currentStatus == STATUS_REMOVED || currentStatus == STATUS_REFUSED || currentStatus == STATUS_LEFT) {
                // if 俱乐部成员人数未满
                if (club.getUserCount() < club.getMaxPerson()) {
                    // 更新用户状态
                    updateStatus(clubUser, STATUS_CHECKING);
                    // 新建申请消息
                    clubMessageService.addMessage(userId, club.getUserId(), club.getId(), clubUser.getId(), ClubMessage.TYPE_APPLY, null, remark);
                    return new Result(true);
                } else {
                    return new Result(false, "俱乐部成员已满", ErrorCode.ER001015);
                }
            }
            // else if 审核中
            else if (currentStatus == STATUS_CHECKING) {
                return new Result(false, "审核中勿重复提交", ErrorCode.ER001003);
            }
            // else if 已加入
            else if (currentStatus == STATUS_JOINED) {
                return new Result(false, "已加入无需重新申请", ErrorCode.ER001005);
            }
            // 其他
            else {
                return new Result(false, "申请失败", ErrorCode.ER_SYSTEM);
            }
        }
        // else 不存在
        else {
            // 创建俱乐部用户
            clubUser = createClubUser(club, userId, ROLE_MEMBER, STATUS_CHECKING, remark);
            return new Result(true);
        }
    }

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
    public List<ClubUser> findMemberList(Long clubId, Integer pageNo, Integer pageSize) {
        int start = SimplePage.getStart(pageNo, pageSize), size = pageSize;
        List<ClubUser> list = new ArrayList<ClubUser>(size);
        String sql = "select cu.id as id, cu.club_id as clubId, cu.user_id as userId, cu.role as role, "
                + " cu.add_time as addTime, cu.update_time as updateTime, "
                + " ud.code as userCode, ud.nick_name as nickName, ud.head_img as headImg "
                + "from cg_club_user cu left join cg_user_detail ud on cu.user_id = ud.id "
                + "where cu.status = " + ClubUser.STATUS_JOINED + " and cu.club_id = :clubId limit :start, :size";
        SQLQuery query = em.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setLong("clubId", clubId);
        query.setInteger("start", start);
        query.setInteger("size", size);
        query.addScalar("id", StandardBasicTypes.LONG);
        query.addScalar("clubId",StandardBasicTypes.LONG);
        query.addScalar("userId",StandardBasicTypes.LONG);
        query.addScalar("role",StandardBasicTypes.INTEGER);
        query.addScalar("addTime",StandardBasicTypes.TIMESTAMP);
        query.addScalar("updateTime",StandardBasicTypes.TIMESTAMP);
        query.addScalar("userCode",StandardBasicTypes.STRING);
        query.addScalar("nickName",StandardBasicTypes.STRING);
        query.addScalar("headImg",StandardBasicTypes.STRING);
        query.setResultTransformer(Transformers.aliasToBean(ClubUser.class));
        if (query != null) {
            list.addAll(query.list());
        }
        return list;
    }

    /**
     * 移除成员
     *
     * @param clubUser 成员
     * @param operId   操作人id
     * @author yjy
     * Created on 2017年12月5日 下午5:21:02
     */
    public void removePlayer(ClubUser clubUser, Long operId) {
        // 更新成员状态
        updateStatus(clubUser, STATUS_REMOVED);
        // 新建玩家被踢消息
        clubMessageService.addMessage(operId, clubUser.getUserId(), clubUser.getClubId(), clubUser.getId(), ClubMessage.TYPE_REMOVE, null, null);
    }

    /**
     * 退出俱乐部
     *
     * @param clubUser 成员
     * @author yjy
     * Created on 2017年12月5日 下午5:30:01
     */
    public void quitClub(ClubUser clubUser) {
        // 更新成员状态
        updateStatus(clubUser, STATUS_LEFT);
        // 获取俱乐部信息
        Club club = clubService.findById(clubUser.getClubId());
        // 新建玩家退出消息
        clubMessageService.addMessage(clubUser.getUserId(), club.getUserId(), club.getId(), clubUser.getId(), ClubMessage.TYPE_QUIT, null, null);
    }

    /**
     * 更新俱乐部成员状态
     *
     * @param clubUser 俱乐部成员
     * @param status   状态
     * @author yjy
     * Created on 2017年12月5日 下午4:13:55
     */
    public void updateStatus(ClubUser clubUser, Integer status) {
        // 当前状态与更新状态不同
        if (!clubUser.getStatus().equals(status)) {
            // 更新俱乐部当前成员数
            int changeNum = 0;
            if (status == STATUS_JOINED) {
                changeNum = 1;
            } else if (clubUser.getStatus() == STATUS_JOINED) {
                changeNum = -1;
            }
            if (changeNum != 0) {
                clubService.changeUserCount(clubUser.getClubId(), changeNum);
            }
            // 更新成员状态
            clubUser.setStatus(status);
            clubUser.setUpdateTime(new Date());
            update(clubUser);
        }
    }

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
    public ClubUser createClubUser(Club club, Long userId, Integer role, Integer status, String remark) {
        ClubUser clubUser = new ClubUser(club.getId(), userId, role);
        clubUser.setStatus(status);
        clubUser.setAddTime(new Date());
        clubUser.setUpdateTime(clubUser.getAddTime());
        clubUser = save(clubUser);
        // if 待审核
        if (status == STATUS_CHECKING) {
            // 新建申请消息
            clubMessageService.addMessage(userId, club.getUserId(), clubUser.getClubId(), clubUser.getId(), ClubMessage.TYPE_APPLY, null, remark);
        } else {
            // 更新俱乐部当前成员数
            clubService.changeUserCount(club.getId(), 1);
        }
        return clubUser;
    }

    /**
     * 获取俱乐部成员
     *
     * @param userId 用户id
     * @param clubId 俱乐部id
     * @return 成员信息
     * @author yjy
     * Created on 2017年12月5日 下午3:17:04
     */
    public ClubUser findByUserIdAndClubId(Long userId, Long clubId) {
        ClubUser clubUser = new ClubUser();
        clubUser.setUserId(userId);
        clubUser.setClubId(clubId);
        List<ClubUser> list = findList(clubUser);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

}
