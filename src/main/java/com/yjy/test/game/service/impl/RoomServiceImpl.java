package com.yjy.test.game.service.impl;

import java.util.*;

import com.yjy.test.base.BaseServiceImpl;
import com.yjy.test.game.base.Config;
import com.yjy.test.game.base.Constants;
import com.yjy.test.game.base.Result;
import com.yjy.test.game.cache.RoomCache;
import com.yjy.test.game.dao.RoomDao;
import com.yjy.test.game.entity.Room;
import com.yjy.test.game.entity.club.Club;
import com.yjy.test.game.redis.RedisUtils;
import com.yjy.test.game.service.RoomService;
import com.yjy.test.game.service.RoomUserService;
import com.yjy.test.game.service.club.ClubService;
import com.yjy.test.game.util.HttpUtil;
import com.yjy.test.game.util.MD5;
import com.yjy.test.game.util.SerializeUtils;
import com.yjy.test.game.web.ErrorCode;
import com.yjy.test.game.web.WebException;
import com.yjy.test.util.hibernate.Finder;
import com.yjy.test.util.hibernate.Pagination;
import com.yjy.test.util.hibernate.SimplePage;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSONObject;

@Service
@Transactional
public class RoomServiceImpl extends BaseServiceImpl<Room, Long> implements
        RoomService {

    private static final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Autowired
    private ClubService clubService;
    @Autowired
    private RoomUserService roomUserService;

    @Autowired
    public void setRoomDao(RoomDao dao) {
        super.setDao(dao);
    }

    public RoomDao getRoomDao() {
        return (RoomDao) super.getDao();
    }

    @Override
    public Pagination findRoomBy(Room bean, Date start, Date end, int pageNo,
                                 int pageSize) {
        Pagination p = null;
        String roomNo = null;
        Integer status = null;
        Integer closeType = null;
        if(null != bean) {
            roomNo = bean.getRoomNo();
            status = bean.getStatus();
            closeType = bean.getCloseType();
        }
        String hql = "select bean from Room bean where 1=1";
        if(notBlank(roomNo)) {
            hql += " and bean.roomNo=:roomNo";
        }
        if(null != status) {
            hql += " and bean.status=:status";
        }
        if(null != closeType) {
            if(Integer.valueOf(-1).equals(closeType) ) {
                hql += " and bean.closeType is null";
            }else {
                hql += " and bean.closeType=:closeType";
            }
        }
        if(null != start) {
            hql += " and bean.addTime>=:start";
        }
        if(null != end) {
            hql += " and bean.addTime<=:end";
        }
        hql +=" order by bean.id desc";
        Finder finder = Finder.create(hql);
        if(StringUtils.isNotBlank(roomNo)) {
            finder.setParam("roomNo", roomNo);
        }
        if(null != status) {
            finder.setParam("status", status);
        }
        if(null != closeType && !(Integer.valueOf(-1).equals(closeType) ) ) {
            finder.setParam("closeType", closeType);
        }
        if(null != start) {
            finder.setParam("start", start);
        }
        if(null != end) {
            finder.setParam("end", end);
        }
        p = find(finder,pageNo, pageSize);
        return p;
    }

    @SuppressWarnings("unchecked")
    public List<Room> findDayRoom(Room bean, Date start, Date end) {
        List<Room> list = null;

        String sql = "select DATE_FORMAT(bean.add_time,'%Y-%m-%d') addTime, count(bean.id) num from cg_room bean where 1=1";
        if(null != start) {
            sql += " and bean.add_time>=:start";
        }
        if(null != end) {
            sql += " and bean.add_time<=:end";
        }

        sql += " group by addTime;";
        SQLQuery sqlQuery = em.createNativeQuery(sql).unwrap(SQLQuery.class);
        sqlQuery.addScalar("num", StandardBasicTypes.INTEGER);
        sqlQuery.addScalar("addTime",StandardBasicTypes.DATE);
        if(null != start) {
            sqlQuery.setParameter("start", start);
        }
        if(null != end) {
            sqlQuery.setParameter("end", end);
        }
        sqlQuery.setResultTransformer(Transformers.aliasToBean(Room.class));
        list = sqlQuery.list();
        return list;
    }

    /**
     * 获取俱乐部房间列表
     *
     * @param clubId   俱乐部id
     * @param pageNo   页号
     * @param pageSize 条数
     * @return 房间列表
     * @author yjy
     * Created on 2017年12月6日 下午4:21:41
     */
    public List<Room> findClubRoomList(Long clubId, Integer pageNo, Integer pageSize) {
        int start = SimplePage.getStart(pageNo, pageSize), size = pageSize;
        List<Room> list = new ArrayList<>(size);
        String sql = "select r.id as id, r.base_integral as baseIntegral, r.user_id as userId, r.room_no as roomNo, "
                + "r.status as status, r.game_num as gameNum, r.game_status as gameStatus, "
                + "r.game_mode as gameMode, r.start_mode as startMode, r.jing_start as jingStart, "
                + "r.score_mode as scoreMode, r.double_pao as doublePao, "
                + "r.add_time as addTime, ud.head_img as headImg, ud.code as code, ud.nick_name as nickName "
                + "from cg_room r left join cg_user_detail ud on r.user_id = ud.id where r.club_id = :clubId "
                + " and r.status != " + Room.STATUS_CLOSE;
        sql += " limit :start, :size ";
        SQLQuery query = em.createNativeQuery(sql).unwrap(SQLQuery.class);
        if (query != null) {
            query.setCacheable(false);
            query.setLong("clubId", clubId);
            query.setLong("start", start);
            query.setLong("size", size);
            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("userId", StandardBasicTypes.LONG);
            query.addScalar("roomNo", StandardBasicTypes.STRING);
            query.addScalar("baseIntegral", StandardBasicTypes.INTEGER);
            query.addScalar("gameNum", StandardBasicTypes.INTEGER);
            query.addScalar("gameStatus", StandardBasicTypes.INTEGER);
            query.addScalar("gameMode", StandardBasicTypes.INTEGER);
            query.addScalar("scoreMode", StandardBasicTypes.INTEGER);
            query.addScalar("doublePao", StandardBasicTypes.INTEGER);
            query.addScalar("startMode", StandardBasicTypes.INTEGER);
            query.addScalar("jingStart", StandardBasicTypes.INTEGER);
            query.addScalar("status", StandardBasicTypes.INTEGER);
            query.addScalar("addTime", StandardBasicTypes.TIMESTAMP);
            query.addScalar("headImg", StandardBasicTypes.STRING);
            query.addScalar("code", StandardBasicTypes.STRING);
            query.addScalar("nickName", StandardBasicTypes.STRING);
            query.setResultTransformer(Transformers.aliasToBean(Room.class));
            list.addAll(query.list());
        }
        if (!list.isEmpty()) {
            for (Room room : list) {
                room.setMaxPerson(3);
                // 获取房间当前玩家人数
                room.setPlayerNum(roomUserService.findPlayerCount(room.getId()));
            }
        }
        return list;
    }

    /**
     * 获取俱乐部房间记录
     *
     * @param clubId   俱乐部id
     * @param userId   用户id
     * @param pageNo   页号
     * @param pageSize 条数
     * @return 房间列表
     * @author yjy
     * Created on 2017年12月6日 下午4:21:41
     */
    public List<Room> findClubRoomRecord(Long clubId, Long userId, Integer pageNo, Integer pageSize) {
        List<Room> list = null;
        Club club = clubService.findById(clubId);
        if (club != null) {
            if (club.getUserId().equals(userId)) {
                userId = null;
            }
            list = findClubRoomRecordFromDb(clubId, userId, SimplePage.getStart(pageNo, pageSize), pageSize);
            if (!list.isEmpty()) {
                for (Room room : list) {
                    room.setMaxPerson(3);
                    // 获取房间当前玩家人数
                    room.setPlayerNum(roomUserService.findPlayerCount(room.getId()));
                }
            }
        }
        return list;
    }

    /**
     * 获取俱乐部房间数
     *
     * @param clubId      俱乐部列表
     * @param onlyCurrent 是否仅进行中的房间
     * @return
     * @author yjy
     * Created on 2017年12月13日 下午3:24:10
     */
    public int findCountByClub(Long clubId, boolean onlyCurrent) {
        String sql = "select count(*) from cg_room where club_id = :clubId ";
        if (onlyCurrent) {
            sql += " and status != " + Room.STATUS_CLOSE;
        }
        SQLQuery query = em.createNativeQuery(sql).unwrap(SQLQuery.class);
        if (query != null) {
            query.setLong("clubId", clubId);
            return Integer.valueOf(query.uniqueResult().toString());
        }
        return 0;
    }

    /**
     * 创建房间
     *
     * @param userId 用户id
     * @param room   房间信息
     * @return 创建结果
     * @author yjy
     * Created on 2017年12月6日 下午3:18:08
     */
    @SuppressWarnings("rawtypes")
    public Result createRoom(Long userId, Room room) {
        String info;
        ErrorCode code;
        Map configMap = getRoomConfig();
        // 验证参数
        Result re = verifyParam(room, configMap);
        if (re.isSuccess()) {
            Club club = clubService.findById(room.getClubId());
            // if 俱乐部存在 && 状态正常
            if (club != null && club.getStatus().equals(Club.STATUS_NORMAL)) {
                // if 群主本人
                if (club.getUserId().equals(userId)) {
                    // 获取用户余额
                    Integer money = userMoney(userId);
                    // 验证余额
                    int cost = figureCost(room.getGameNum(), room.getBaseIntegral(), configMap);
                    if (money != null && money >= cost) {
                        // 初始化房间信息
                        room = new Room(room.getClubId(), userId, room.getGameNum(), room.getBaseIntegral(),
                                room.getGameMode(), room.getScoreMode(), room.getDoublePao(), room.getStartMode(), room.getJingStart());
                        room.setRoomNo(createRoomNo());
                        room.setAddTime(new Date());
                        room.setCreateCost(cost);
                        room.setUpdateTime(new Date());
                        // 保存房间至数据库
                        room = save(room);
                        // 将房间加入缓存
                        RoomCache.getInstance().addRoom(room.getRoomNo(), "");
                        return new Result(true);
                    } else {
                        info = "余额不足";
                        code = ErrorCode.ER001013;
                    }
                } else {
                    info = "非群主不可创建房间";
                    code = ErrorCode.ER001014;
                }
            } else {
                info = "俱乐部不存在";
                code = ErrorCode.ER001006;
            }
        } else {
            info = re.getInfo();
            code = re.getCode();
        }
        return new Result(false, info, code);
    }

    /**
     * 获取用户钻石信息
     *
     * @param userId 用户id
     * @return money
     */
    private Integer userMoney(Long userId) {
        Integer money = null;
        try {
            Map<String, String> parames = new HashMap<String, String>();
            parames.put("apiUrl", "user.balance");
            parames.put("userId", userId.toString());
            parames = generateSign(parames);
            String host = Config.playerCenterUrl;
            String res = HttpUtil.getInstance().doPost(host + "scoket/balance.jtk", parames);
            if (res != null) {
                JSONObject json = JSONObject.parseObject(res);
                if (Integer.valueOf(1).equals(json.getInteger("status"))) {
                    JSONObject data = json.getJSONObject("data");
                    money = data.getInteger("money");
                } else {
                    log.error("userMoney failed, res: {}", res);
                }
            } else {
                log.error("userMoney failed, cause res is null");
            }
        } catch (Exception e) {
            log.error("userMoney throw an error", e);
        }
        return money;
    }

    /**
     * 根据参数 生成一个 密钥参数
     *
     * @param parames 原参数
     * @return parames
     */
    private static Map<String, String> generateSign(Map<String, String> parames) {
        String SIGN_KEY = "xxxxxxxxxxxxxxxxxx";
        Map<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
        if (null != parames) {
            Set<String> keySet = parames.keySet();
            Iterator<String> iter = keySet.iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                String value = parames.get(key);
                map.put(key, value);
            }
        } else {
            parames = new HashMap<String, String>();
        }

        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key).append("=").append(map.get(key));
        }
        sb.append(SIGN_KEY);
        String sign = MD5.MD5Encode(sb.toString()).toLowerCase();
        parames.put("sign", sign);
        return parames;
    }

    /**
     * 验证创建房间的参数
     *
     * @param room 房间信息
     * @param map  配置信息
     * @return result
     */
    @SuppressWarnings("rawtypes")
    private Result verifyParam(Room room, Map map) {
        String info;
        Long clubId = room.getClubId();
        Integer gameNum = room.getGameNum(),
                gameMode = room.getGameMode(),
                scoreMode = room.getScoreMode(),
                doublePao = room.getDoublePao(),
                jingStart = room.getJingStart();
        if (clubId != null && gameNum != null && gameMode != null && scoreMode != null
                && isYesOrNo(doublePao) && isYesOrNo(jingStart)) {
            String roomGames = Room.ROOMGAMES;
            if (map != null) {
                Object roomGamesObj = map.get("config_" + Constants.CONFIG_ROOM_GAMES);
                if (roomGamesObj != null)
                    roomGames = roomGamesObj.toString();
            }
            if (inRange(roomGames, gameNum.toString())) {
                if (gameMode.equals(Room.GAME_MODE_FIVE) || gameMode.equals(Room.GAME_MODE_THREE)) {
                    if (scoreMode.equals(Room.SCORE_MODE_NORMAL) || scoreMode.equals(Room.SCORE_MODE_ADD5)) {
                        return new Result(true);
                    } else
                        info = "玩法模式选择错误:" + scoreMode;
                } else
                    info = "游戏模式选择错误:" + gameMode;
            } else
                info = "游戏局数不在范围：" + roomGames;
        } else
            info = "参数不完整";
        return new Result(false, info, ErrorCode.ER_NOT_ALL_SUBMIT);
    }

    /**
     * 获取房间配置
     *
     * @return map
     */
    @SuppressWarnings("rawtypes")
    private Map getRoomConfig() {
        Jedis jedis = RedisUtils.getInstance().getJedis();
        byte[] configByte = jedis.get(Constants.ALL_CONFIG_MAP.getBytes());
        //RedisUtils.getInstance().closeJedis(jedis);
        if (configByte != null) {
            return (Map) SerializeUtils.unserialize(configByte);
        }
        return null;
    }

    /**
     * 根据房间的局数获取 创建房间所需的钻石数
     *
     * @param gameNum      圈数
     * @param baseIntegral 下注范围
     * @param configMap    配置
     * @return cost
     */
    @SuppressWarnings("rawtypes")
    private int figureCost(int gameNum, Integer baseIntegral, Map configMap) {
        int cost = 0;
        if (configMap != null) {
            // 计算消耗
            Object roomGames = configMap.get("config_roomGames");
            Object roomCosts = configMap.get("config_roomCosts");
            if (roomGames != null && roomCosts != null) {
                String[] turns = roomGames.toString().split(",");
                for (int i = 0; i < turns.length; ++i) {
                    if ((gameNum + "").equals(turns[i])) {
                        cost = Integer.valueOf(roomCosts.toString().split(",")[i]);
                        break;
                    }
                }
            }
//        	Object baseWagerRange = configMap.get("config_" + Constants.CONFIG_BASE_WAGER_RANGE);
//        	Object baseCostMultiple = configMap.get("config_" + Constants.CONFIG_BASE_COST_MULTIPLE);
//            if (baseWagerRange != null && baseCostMultiple != null) {
//                String[] baseWagers = baseWagerRange.toString().split(",");
//                for (int i = 0; i < baseWagers.length; i++) {
//                    if ((baseWager + "").equals(baseWagers[i])) {
//                        float multiple = Float.parseFloat(baseCostMultiple.toString().split(",")[i].trim());
//                        cost = (int)(cost * multiple);
//                        break;
//                    }
//                }
//            }
        }
        return cost;
    }

    /**
     * 生成一个房间号
     *
     * @return roomNo
     */
    private String createRoomNo() {
        String roomNo = generateRoomNo();
        while (RoomCache.getInstance().exist(roomNo)) {
            roomNo = generateRoomNo();
        }
        return roomNo;
    }

    /**
     * 随机生成一个房间号
     *
     * @return volatileRoomNo
     */
    private String generateRoomNo() {
        String roomNo = String.valueOf(RandomUtils.nextInt(1, 1000000));
        while (roomNo.length() < 6) {
            roomNo = "0" + roomNo;
        }
        return roomNo;
    }

    /**
     * 查看值是否在范围中
     *
     * @param range 范围
     * @param x     值
     * @return res
     */
    private static boolean inRange(String range, String x) {
        if (range != null) {
            String[] ranges = range.split(",");
            for (String r : ranges) {
                if (r.trim().equals(x)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测元素是否在数组内
     *
     * @param arr 数组
     * @param i   元素
     * @return 是否
     * @author yjy
     * Created on 2017年12月12日 下午12:14:14
     */
    private static boolean inArray(Integer[] arr, Integer i) {
        if (i == null) return false;
        for (Integer a : arr) {
            if (a.equals(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断参数是否是 0|1
     *
     * @param yesOrNo 参数
     * @return 是否
     * @author yjy
     * Created on 2017年12月14日 上午10:11:10
     */
    private static boolean isYesOrNo(Integer yesOrNo) {
        if (yesOrNo != null) {
            return yesOrNo == 1 || yesOrNo == 0;
        }
        return false;
    }

    /**
     * 获取俱乐部房间记录
     * @author yjy
     * Created on 2017年12月6日 下午4:21:41
     * @param clubId 俱乐部id
     * @param userId 用户id
     * @param start 起始下标
     * @param size 条数
     * @return 房间列表
     */
    public List<Room> findClubRoomRecordFromDb(Long clubId, Long userId, int start, int size) {
        List<Room> list = new ArrayList<Room>(size);
        String sql = "select distinct r.id as id, r.base_integral as baseIntegral, r.user_id as userId, r.room_no as roomNo, "
                + "r.status as status, r.game_num as gameNum, r.game_status as gameStatus, "
                + "r.game_mode as gameMode, r.start_mode as startMode, r.jing_start as jingStart, "
                + "r.score_mode as scoreMode, r.double_pao as doublePao, "
                + " r.add_time as addTime, ud.head_img as headImg, ud.code as code, ud.nick_name as nickName "
                + " from cg_room r left join cg_room_user ru on r.id = ru.room_id, "
                + " cg_user_detail ud "
                + " where r.user_id = ud.id "
                + " and r.club_id = :clubId "
                + " and r.status = " + Room.STATUS_CLOSE + " ";
        if (userId != null) {
            sql += " and ru.user_id = :userId ";
        }
        sql += " order by r.id desc ";
        sql += " limit :start, :size ";
        SQLQuery query = em.createNativeQuery(sql).unwrap(SQLQuery.class);
        if (query != null) {
            query.setLong("clubId", clubId);
            query.setLong("start", start);
            query.setLong("size", size);
            if (userId != null) {
                query.setLong("userId", userId);
            }
            query.addScalar("id", StandardBasicTypes.LONG);
            query.addScalar("userId", StandardBasicTypes.LONG);
            query.addScalar("roomNo", StandardBasicTypes.STRING);
            query.addScalar("baseIntegral", StandardBasicTypes.INTEGER);
            query.addScalar("gameNum", StandardBasicTypes.INTEGER);
            query.addScalar("gameMode", StandardBasicTypes.INTEGER);
            query.addScalar("scoreMode", StandardBasicTypes.INTEGER);
            query.addScalar("doublePao", StandardBasicTypes.INTEGER);
            query.addScalar("startMode", StandardBasicTypes.INTEGER);
            query.addScalar("jingStart", StandardBasicTypes.INTEGER);
            query.addScalar("status", StandardBasicTypes.INTEGER);
            query.addScalar("addTime", StandardBasicTypes.TIMESTAMP);
            query.addScalar("headImg", StandardBasicTypes.STRING);
            query.addScalar("code", StandardBasicTypes.STRING);
            query.addScalar("nickName", StandardBasicTypes.STRING);
            query.setResultTransformer(Transformers.aliasToBean(Room.class));
            list.addAll(query.list());
        }
        return list;
    }


}
