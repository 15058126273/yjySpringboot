package com.yjy.test.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.dao.RoomUserDao;
import com.yjy.test.game.entity.RoomUser;
import com.yjy.test.game.service.RoomUserService;
import com.yjy.test.game.web.WebException;
import com.yjy.test.util.hibernate.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RoomUserServiceImpl extends BaseServiceImpl<RoomUser, Long> implements
        RoomUserService {

    @Autowired
    public void setRoomUserDao(RoomUserDao dao) {
        super.setDao(dao);
    }

    public RoomUserDao getRoomUserDao() {
        return (RoomUserDao) super.getDao();
    }

    @Override
    public RoomUser findByRoomUser(Long roomId, Long userId) throws WebException {
        if (null == roomId || null == userId) {
            throw new WebException("提交参数为空");
        }
        RoomUser bean = null;
        RoomUser roomUser = new RoomUser();
        roomUser.setRoomId(roomId);
        roomUser.setUserId(userId);

        List<RoomUser> list = findList(roomUser);
        if (null != list && list.size() > 0) {
            bean = list.get(0);
        }
        return bean;
    }

    @Override
    public List<RoomUser> findByRoomId(Long roomId, Integer isPlayer) throws WebException {
        if (null == roomId) {
            throw new WebException("提交参数为空");
        }
        List<RoomUser> list = null;
        RoomUser roomUser = new RoomUser();
        roomUser.setRoomId(roomId);
        if (null != isPlayer) {
            roomUser.setIsPlayer(isPlayer);
        }
        list = findList(roomUser);
        return list;
    }

    @Override
    public Pagination findRoomScoresBy(RoomUser bean, Date start, Date end,
                                       int pageNo, int pageSize) throws WebException {
        Pagination p = null;
        String roomNo = null;
        String code = null;
        String nickName = null;
        if (null != bean) {
            roomNo = bean.getRoomNo();
            code = bean.getCode();
            nickName = bean.getNickName();
        }
        String hql = "from cg_room_user bean left outer join cg_user_detail user on bean.user_id=user.id where bean.is_player=1";

        if (StringUtils.isNotBlank(roomNo)) {
            hql += " and bean.room_no=:roomNo";
        }
        if (StringUtils.isNotBlank(nickName)) {
            hql += " and user.nick_name=:nickName";
        }
        if (StringUtils.isNotBlank(code)) {
            hql += " and user.code=:code";
        }
        if (null != start) {
            hql += " and bean.come_time>=:start";
        }
        if (null != end) {
            hql += " and bean.come_time<=:end";
        }
        String countSql = "select count(bean.id) " + hql;
        SQLQuery sqlQueryCount = em.createNativeQuery(countSql).unwrap(SQLQuery.class);

        hql = "select bean.id as id,bean.room_id as roomId,bean.room_no as roomNo,  bean.user_id as userId, bean.integral as integral, user.nick_name as nickName, user.code as code,bean.come_time as comeTime " + hql + " order by bean.room_id desc,bean.id desc";
        SQLQuery sqlQuery = em.createNativeQuery(hql).unwrap(SQLQuery.class);

        if (StringUtils.isNotBlank(roomNo)) {
            sqlQueryCount.setParameter("roomNo", roomNo);
            sqlQuery.setParameter("roomNo", roomNo);
        }
        if (StringUtils.isNotBlank(nickName)) {
            sqlQueryCount.setParameter("nickName", "%" + nickName + "%");
            sqlQuery.setParameter("nickName", "%" + nickName + "%");
        }
        if (StringUtils.isNotBlank(code)) {
            sqlQueryCount.setParameter("code", code);
            sqlQuery.setParameter("code", code);
        }
        if (null != start) {
            sqlQueryCount.setParameter("start", start);
            sqlQuery.setParameter("start", start);
        }
        if (null != end) {
            sqlQueryCount.setParameter("end", end);
            sqlQuery.setParameter("end", end);
        }

        int totalCount = Integer.parseInt(sqlQueryCount.uniqueResult().toString());
        p = new Pagination(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList<RoomUser>());
            return p;
        }
        sqlQuery.addScalar("id", StandardBasicTypes.LONG);
        sqlQuery.addScalar("roomId", StandardBasicTypes.LONG);
        sqlQuery.addScalar("roomNo", StandardBasicTypes.STRING);
        sqlQuery.addScalar("userId", StandardBasicTypes.LONG);
        sqlQuery.addScalar("integral", StandardBasicTypes.INTEGER);
        sqlQuery.addScalar("nickName", StandardBasicTypes.STRING);
        sqlQuery.addScalar("code", StandardBasicTypes.STRING);
        sqlQuery.addScalar("comeTime", StandardBasicTypes.TIMESTAMP);

        sqlQuery.setFirstResult(p.getFirstResult());
        sqlQuery.setMaxResults(p.getPageSize());
        sqlQuery.setResultTransformer(Transformers.aliasToBean(RoomUser.class));

        @SuppressWarnings("unchecked")
        List<RoomUser> list = sqlQuery.list();
        p.setList(list);
        return null;
    }

    @Override
    public List<RoomUser> findDayScores(RoomUser bean, Date start, Date end)
            throws WebException {
        List<RoomUser> list = null;
        Long userId = null;
        if(null != bean) {
            userId = bean.getUserId();
        }
        String sql = "select DATE_FORMAT(bean.come_time,'%Y-%m-%d') comeTime, SUM(bean.integral) integral from cg_room_user bean where bean.is_player=1";
        if(null != start) {
            sql += " and bean.come_time>=:start";
        }
        if(null != end) {
            sql += " and bean.come_time<=:end";
        }
        if(null != userId) {
            sql += " and bean.user_id=:userId";
        }

        sql += " group by comeTime order by comeTime asc;";
        SQLQuery sqlQuery = em.createNativeQuery(sql).unwrap(SQLQuery.class);
        sqlQuery.addScalar("integral",StandardBasicTypes.INTEGER);
        sqlQuery.addScalar("comeTime",StandardBasicTypes.DATE);
        if(null != start) {
            sqlQuery.setParameter("start", start);
        }
        if(null != end) {
            sqlQuery.setParameter("end", end);
        }
        if(null != userId) {
            sqlQuery.setParameter("userId", userId);
        }
        sqlQuery.setResultTransformer(Transformers.aliasToBean(RoomUser.class));
        list = sqlQuery.list();
        return list;
    }

    /**
     * 获取玩家数量
     *
     * @param roomId 房间id
     * @return 玩家数量
     * @author yjy
     * Created on 2017年12月6日 下午4:50:26
     */
    public int findPlayerCount(Long roomId) {
        int count = 0;
        String sql = "select count(id) from cg_room_user where current_role != 0 and room_id = :roomId";
        SQLQuery query = em.createNativeQuery(sql).unwrap(SQLQuery.class);
        query.setLong("roomId", roomId);
        Object res = query.uniqueResult();
        if (res != null) {
            count = Integer.parseInt(res.toString());
        }
        return count;
    }
}
