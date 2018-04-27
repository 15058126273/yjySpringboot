package com.yjy.test.game.service.club.impl;

import java.util.*;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.base.Constants;
import com.yjy.test.game.dao.club.ClubDao;
import com.yjy.test.game.entity.club.Club;
import com.yjy.test.game.entity.club.ClubUser;
import com.yjy.test.game.redis.RedisUtils;
import com.yjy.test.game.service.RoomService;
import com.yjy.test.game.service.club.ClubService;
import com.yjy.test.game.service.club.ClubUserService;
import com.yjy.test.util.hibernate.SimplePage;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClubServiceImpl extends BaseServiceImpl<Club, Long> implements ClubService {

    @Autowired
    private ClubUserService clubUserService;
    @Autowired
    private RoomService roomService;

    @Autowired
    public void setClubDao(ClubDao dao) {
        super.setDao(dao);
    }

    public ClubDao getClubDao() {
        return (ClubDao) super.getDao();
    }

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
    public Club createClub(Long userId, String headImg, String name, String address, Integer canJoin, Integer condition,
                           Double longitude, Double latitude, String introduce) {
        // 保存基础信息
        Club club = new Club(userId, headImg, name, address, canJoin, condition, longitude, latitude);
        // 生成一个俱乐部编号
        club.setCode(createClubCode());
        club.setIntroduce(introduce);
        club.setAddTime(new Date());
        club.setUpdateTime(club.getAddTime());
        club = save(club);
        // 更新俱乐部编号
        clubUserService.createClubUser(club, userId, ClubUser.ROLE_HOST, ClubUser.STATUS_JOINED, null);
        return club;
    }

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
    public List<Club> findRecommend(Long userId, Double longitude, Double latitude, Integer pageSize) {
        Integer minPerson = 0;

        Map<String, String> configMap = (Map<String, String>) RedisUtils.getInstance().getObject(Constants.ALL_CONFIG_MAP);
        String person = configMap.get(Constants.PRE_CONFIG + "recommendClubPerson");
        if (person != null) {
            minPerson = Integer.valueOf(person);
        }
        // 根据经纬度 获取 附近的俱乐部
        List<Club> list = new ArrayList<Club>();
        String sql = "select c.id as id, c.user_id as userId, c.name as name, c.code as code, c.address as address, "
                + " c.head_img as headImg, c.can_join as canJoin, c.cond as `condition`, c.recommend as recommend, "
                + " c.status as status, c.user_count as userCount, c.introduce as introduce, c.max_person as maxPerson "
                + " from cg_club c "
                + " where c.status = 0 and c.user_count >= :minPerson and c.id not in (select club_id from cg_club_user where user_id = :userId and status = 1) ";
        if (longitude != null && latitude != null) {
            sql += " order by ((3959 * acos(cos(radians(:latitude)) * cos(radians(c.latitude)) * cos(radians(c.longitude) - radians(:longitude)) + sin( radians(:latitude)) * sin(radians(c.latitude)))) * 1609.344) ";
        }
        sql += " limit 0, :size";
        SQLQuery query = em.createNativeQuery(sql).unwrap(SQLQuery.class);
        if (query != null) {
            query.setLong("userId", userId);
            query.setInteger("minPerson", minPerson);
            if (longitude != null && latitude != null) {
                query.setParameter("longitude", longitude);
                query.setParameter("latitude", latitude);
            }
            query.setParameter("size", pageSize);
            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("userId", StandardBasicTypes.LONG);
            query.addScalar("name", StandardBasicTypes.STRING);
            query.addScalar("code", StandardBasicTypes.STRING);
            query.addScalar("address", StandardBasicTypes.STRING);
            query.addScalar("headImg", StandardBasicTypes.STRING);
            query.addScalar("canJoin", StandardBasicTypes.INTEGER);
            query.addScalar("condition", StandardBasicTypes.INTEGER);
            query.addScalar("recommend", StandardBasicTypes.INTEGER);
            query.addScalar("status", StandardBasicTypes.INTEGER);
            query.addScalar("userCount", StandardBasicTypes.INTEGER);
            query.addScalar("maxPerson", StandardBasicTypes.INTEGER);
            query.addScalar("introduce", StandardBasicTypes.STRING);
            query.setResultTransformer(Transformers.aliasToBean(Club.class));
            list = query.list();
        }
        return list;
    }

    /**
     * 搜索俱乐部
     *
     * @param code 编号
     * @return 俱乐部
     * @author yjy
     * Created on 2017年12月6日 上午10:14:56
     */
    public Club findSearch(String code) {
        // 获取列表 (状态正常 )
        Club club = new Club();
        club.setStatus(Club.STATUS_NORMAL);
        club.setCode(code);
        List<Club> list = findList(club);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

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
    public List<Club> findMyList(Long userId, Integer pageNo, Integer pageSize) {
        int start = SimplePage.getStart(pageNo, pageSize), size = pageSize;
        List<Club> list = new ArrayList<Club>(size);
        String sql = "select distinct c.id as id, c.user_id as userId, c.name as name, c.code as code, c.address as address, "
                + "c.head_img as headImg, c.can_join as canJoin, c.cond as `condition`, c.recommend as recommend, "
                + "c.status as status, c.user_count as userCount, c.introduce as introduce, c.max_person as maxPerson "
                + " from cg_club c left join cg_club_user cu on c.id = cu.club_id "
                + " where c.status = 0 "
                + " and cu.user_id = :userId "
                + " and cu.status = " + ClubUser.STATUS_JOINED
                + " limit :start, :size";
        SQLQuery query = em.createNativeQuery(sql).unwrap(SQLQuery.class);
        if (query != null) {
            query.setParameter("userId", userId);
            query.setParameter("start", start);
            query.setParameter("size", size);
            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("userId", StandardBasicTypes.LONG);
            query.addScalar("name", StandardBasicTypes.STRING);
            query.addScalar("code", StandardBasicTypes.STRING);
            query.addScalar("address", StandardBasicTypes.STRING);
            query.addScalar("headImg", StandardBasicTypes.STRING);
            query.addScalar("canJoin", StandardBasicTypes.INTEGER);
            query.addScalar("condition", StandardBasicTypes.INTEGER);
            query.addScalar("recommend", StandardBasicTypes.INTEGER);
            query.addScalar("status", StandardBasicTypes.INTEGER);
            query.addScalar("userCount", StandardBasicTypes.INTEGER);
            query.addScalar("maxPerson", StandardBasicTypes.INTEGER);
            query.addScalar("introduce", StandardBasicTypes.STRING);
            query.setResultTransformer(Transformers.aliasToBean(Club.class));
            list.addAll(query.list());
        }
        if (!list.isEmpty()) {
            for (Club club : list) {
                club.setRoomCount(roomService.findCountByClub(club.getId(), true));
            }
        }
        return list;
    }

    /**
     * 更新当前成员数
     *
     * @param id        俱乐部id
     * @param changeNum 变动数
     * @author yjy
     * Created on 2017年12月5日 下午4:21:37
     */
    public void changeUserCount(Long id, Integer changeNum) {
        String sql = "update cg_club "
                + " set user_count = (case when (user_count + :changeNum) > 0 then (user_count + :changeNum) else 0 end)"
                + " where id = :id";
        Query query = em.createNativeQuery(sql).unwrap(Query.class);
        query.setInteger("changeNum", changeNum);
        query.setLong("id", id);
        query.executeUpdate();
    }

    /**
     * 解散俱乐部
     *
     * @param club 俱乐部
     * @author yjy
     * Created on 2017年12月15日 上午10:44:21
     */
    public void dissolve(Club club) {
        club.setStatus(Club.STATUS_DELETED);
        club.setUpdateTime(new Date());
        update(club);
    }

    /**
     * 获取用户创建的俱乐部数量
     *
     * @param userId 用户id
     * @return 数量
     * @author yjy
     * Created on 2018年1月25日 下午3:03:27
     */
    public int findCreateCount(Long userId) {
        Club club = new Club();
        club.setUserId(userId);
        club.setStatus(Club.STATUS_NORMAL);
        return (int)findCount(club);
    }

    /**
     * 根据俱乐部id生成一个未存在的随机俱乐部编号
     *
     * @return 编号
     * @author yjy
     * Created on 2017年12月5日 下午1:30:24
     */
    private String createClubCode() {
        // 获取当前俱乐部总数
        long count = findAllCount();
        String countStr = Long.toString(count);
        int len = countStr.length();
        // if 长度已经超过6位数
        if (len >= 6) {
            // 直接使用id作为编号
            return countStr;
        }
        // 首先随机生成一个非0开头的六位数
        Random random = new Random();
        String ranNum = String.valueOf(random.nextInt(900000) + 100000);
        // 用countStr覆盖随机字符串的后几位
        return ranNum.substring(0, ranNum.length() - len) + countStr;
    }


}
