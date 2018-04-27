package com.yjy.test.game.service.club.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.dao.club.ClubMessageDao;
import com.yjy.test.game.entity.club.ClubMessage;
import com.yjy.test.game.entity.club.ClubUser;
import com.yjy.test.game.service.club.ClubMessageService;
import com.yjy.test.game.service.club.ClubUserService;
import com.yjy.test.util.hibernate.SimplePage;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.yjy.test.game.entity.club.ClubUser.STATUS_CHECKING;
import static com.yjy.test.game.entity.club.ClubUser.STATUS_JOINED;
import static com.yjy.test.game.entity.club.ClubUser.STATUS_REFUSED;


@Service
@Transactional
public class ClubMessageServiceImpl extends BaseServiceImpl<ClubMessage, Long> implements ClubMessageService {

    @Autowired
    private ClubUserService clubUserService;

    @Autowired
    public void setClubMessageDao(ClubMessageDao dao) {
        super.setDao(dao);
    }

    public ClubMessageDao getClubMessageDao() {
        return (ClubMessageDao) super.getDao();
    }

    /**
     * 新建一个消息
     *
     * @param sendId     发送人id
     * @param receiveId  接收人id
     * @param clubId     俱乐部id
     * @param clubUserId 成员id
     * @param type       消息类型
     * @param result     审核结果
     * @param remark     备注
     * @author yjy
     * Created on 2017年12月6日 上午11:16:57
     */
    public void addMessage(Long sendId, Long receiveId, Long clubId, Long clubUserId, Integer type, Integer result, String remark) {
        ClubMessage clubMessage = new ClubMessage(sendId, receiveId, clubId, clubUserId, type);
        clubMessage.setRemark(remark);
        clubMessage.setResult(result);
        clubMessage.setAddTime(new Date());
        clubMessage.setUpdateTime(clubMessage.getAddTime());
        save(clubMessage);
    }

    /**
     * 获取我的消息列表
     *
     * @param userId   用户id
     * @param status   消息状态
     * @param pageNo   页号
     * @param pageSize 条数
     * @return 消息列表
     * @author yjy
     * Created on 2017年12月6日 上午11:50:40
     */
    public List<ClubMessage> findMyList(Long userId, Integer status, Integer pageNo, Integer pageSize) {
        List<ClubMessage> list = new ArrayList<>();
        Optional<List<ClubMessage>> listOptional = findMyListFromDb(userId, status, SimplePage.getStart(pageNo, pageSize), pageSize);
        if (listOptional.isPresent() && !(list = listOptional.get()).isEmpty()) {
            for (ClubMessage clubMessage : list) {
                // if 非申请消息 && 未读
                if (!clubMessage.getType().equals(ClubMessage.TYPE_APPLY)
                        && clubMessage.getStatus().equals(ClubMessage.STATUS_NEW)) {
                    clubMessage = findById(clubMessage.getId());
                    // 更新为已读
                    clubMessage.setStatus(ClubMessage.STATUS_OLD);
                    clubMessage.setUpdateTime(new Date());
                    update(clubMessage);
                }
            }
        }
        return list;
    }

    private Optional<List<ClubMessage>> findMyListFromDb(Long userId, Integer status, int start, int size) {
        List<ClubMessage> list = new ArrayList<>(size);
        String sql = "select cm.id as id, cm.send_id as sendId, cm.type as type, cm.status as status, "
                + "cm.result as result, cm.remark as remark, cm.add_time as addTime, c.name as clubName, "
                + "ud.nick_name as nickName, ud.head_img as headImg, ud.code as userCode "
                + "from cg_club_message cm left join cg_user_detail ud on cm.send_id = ud.id, "
                + "cg_club c "
                + "where "
                + "c.id = cm.club_id "
                + "and cm.is_delete = 0 "
                + "and cm.receive_id = :userId "
                + "and (cm.status = 1 or (cm.status = 2 and datediff(cm.update_time, now()) >= -7)) ";
        if (status != null) {
            sql += " and cm.status = :status ";
        }
        sql += " order by cm.update_time desc ";
        sql += " limit :start, :size";
        SQLQuery query = em.createNativeQuery(sql).unwrap(SQLQuery.class);
        if (query != null) {
            query.setLong("userId", userId);
            query.setInteger("start", start);
            query.setInteger("size", size);
            if (status != null) {
                query.setInteger("status", status);
            }
            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("sendId", StandardBasicTypes.LONG);
            query.addScalar("type", StandardBasicTypes.INTEGER);
            query.addScalar("status", StandardBasicTypes.INTEGER);
            query.addScalar("result", StandardBasicTypes.INTEGER);
            query.addScalar("remark", StandardBasicTypes.STRING);
            query.addScalar("addTime", StandardBasicTypes.TIMESTAMP);
            query.addScalar("clubName", StandardBasicTypes.STRING);
            query.addScalar("nickName", StandardBasicTypes.STRING);
            query.addScalar("headImg", StandardBasicTypes.STRING);
            query.addScalar("userCode", StandardBasicTypes.STRING);
            query.setResultTransformer(Transformers.aliasToBean(ClubMessage.class));
            list.addAll(query.list());
        }
        return Optional.of(list);
    }

    /**
     * 群主审核消息
     *
     * @param clubMessage 申请消息
     * @param result      结果
     * @author yjy
     * Created on 2017年12月6日 下午1:47:42
     */
    public ClubMessage check(ClubMessage clubMessage, Integer result) {
        Long clubUserId = clubMessage.getClubUserId();
        ClubUser clubUser = clubUserService.findById(clubUserId);
        if (clubUser != null && clubUser.getStatus().equals(STATUS_CHECKING)) {
            // 更新消息状态
            clubMessage.setResult(result);
            clubMessage.setStatus(ClubMessage.STATUS_OLD);
            clubMessage.setUpdateTime(new Date());
            update(clubMessage);
            // 更新成员状态
            clubUserService.updateStatus(clubUser, result.equals(ClubMessage.RESULT_AGREE) ? STATUS_JOINED : STATUS_REFUSED);
            // 向申请玩家发送审核结果消息
            addMessage(clubMessage.getReceiveId(), clubMessage.getSendId(), clubMessage.getClubId(), clubUserId, ClubMessage.TYPE_REPLY, result, null);
        }
        return findMessageById(clubMessage.getId());
    }

    /**
     * 获取指定消息
     * @author yjy
     * Created on 2017年12月6日 上午11:50:40
     * @param id 消息id
     * @return 消息
     */
    private ClubMessage findMessageById(Long id) {
        String sql = "select cm.id as id, cm.send_id as sendId, cm.type as type, cm.status as status, "
                + "cm.result as result, cm.remark as remark, cm.add_time as addTime, c.name as clubName, "
                + "ud.nick_name as nickName, ud.head_img as headImg, ud.code as userCode "
                + "from cg_club_message cm left join cg_user_detail ud on cm.receive_id = ud.id, "
                + "cg_club c where c.id = cm.club_id and cm.id = :id ";
        SQLQuery query = em.createNativeQuery(sql).unwrap(SQLQuery.class);
        if (query != null) {
            query.setLong("id", id);
            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("sendId", StandardBasicTypes.LONG);
            query.addScalar("type", StandardBasicTypes.INTEGER);
            query.addScalar("status", StandardBasicTypes.INTEGER);
            query.addScalar("result", StandardBasicTypes.INTEGER);
            query.addScalar("remark", StandardBasicTypes.STRING);
            query.addScalar("addTime", StandardBasicTypes.TIMESTAMP);
            query.addScalar("clubName", StandardBasicTypes.STRING);
            query.addScalar("nickName", StandardBasicTypes.STRING);
            query.addScalar("headImg", StandardBasicTypes.STRING);
            query.addScalar("userCode", StandardBasicTypes.STRING);
            query.setResultTransformer(Transformers.aliasToBean(ClubMessage.class));
            return (ClubMessage) query.uniqueResult();
        }
        return null;
    }
}
