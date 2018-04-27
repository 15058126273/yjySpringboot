package com.yjy.test.game.service;

import com.yjy.test.base.BaseService;
import com.yjy.test.game.entity.RoomUser;
import com.yjy.test.game.web.WebException;
import com.yjy.test.util.hibernate.Pagination;

import java.util.Date;
import java.util.List;

/**
 * 房间用户的service信息管理
 *
 * @author wdy
 * @version ：2017年5月24日 下午6:30:39
 */
public interface RoomUserService extends BaseService<RoomUser, Long> {

    /**
     * 根据房间id和用户id获取当前用户参与信息
     *
     * @param roomId
     * @param userId
     * @return
     * @throws WebException
     * @author wdy
     * @version ：2017年6月8日 下午5:00:40
     */
    public RoomUser findByRoomUser(Long roomId, Long userId) throws WebException;

    /**
     * 根据房间id获取参与记录
     *
     * @param roomId
     * @return
     * @throws WebException
     * @author wdy
     * @version ：2017年6月8日 下午5:16:50
     */
    public List<RoomUser> findByRoomId(Long roomId, Integer isPlayer) throws WebException;

    /**
     * 查找战绩列表
     *
     * @param bean
     * @param start
     * @param end
     * @param pageNo
     * @param pageSize
     * @return
     * @throws WebException
     * @author wdy
     * @version ：2017年9月26日 下午5:54:26
     */
    public Pagination findRoomScoresBy(RoomUser bean, Date start, Date end, int pageNo, int pageSize) throws WebException;

    /**
     * 统计玩家的战绩
     *
     * @param bean
     * @param start
     * @param end
     * @return
     * @throws WebException
     * @author wdy
     * @version ：2017年9月27日 上午10:39:08
     */
    public List<RoomUser> findDayScores(RoomUser bean, Date start, Date end) throws WebException;

    /**
     * 获取玩家数量
     *
     * @param roomId 房间id
     * @return 玩家数量
     * @author yjy
     * Created on 2017年12月6日 下午4:50:26
     */
    int findPlayerCount(Long roomId);

}
