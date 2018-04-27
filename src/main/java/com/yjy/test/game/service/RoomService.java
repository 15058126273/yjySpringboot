package com.yjy.test.game.service;

import com.yjy.test.base.BaseService;
import com.yjy.test.game.base.Result;
import com.yjy.test.game.entity.Room;
import com.yjy.test.game.web.WebException;
import com.yjy.test.util.hibernate.Pagination;

import java.util.Date;
import java.util.List;

/**
 * 房间的service管理
 *
 * @author wdy
 * @version ：2017年5月24日 下午6:26:53
 */
public interface RoomService extends BaseService<Room, Long> {

    /**
     * 根据一定的条件查询房间
     * 其他条件放在bean中
     *
     * @param bean
     * @param start
     * @param end
     * @param pageNo
     * @param pageSize
     * @return
     * @author wdy
     * @version ：2017年7月21日 上午9:23:27
     */
    public Pagination findRoomBy(Room bean, Date start, Date end, int pageNo, int pageSize) throws WebException;

    /**
     * 统计创建房间数量
     *
     * @param bean
     * @param start
     * @param end
     * @return
     * @throws WebException
     * @author wdy
     * @version ：2017年7月24日 下午4:52:42
     */
    public List<Room> findDayRoom(Room bean, Date start, Date end) throws WebException;

    /**
     * 创建房间
     *
     * @param userId 用户id
     * @param room   房间信息
     * @return 创建结果
     * @author yjy
     * Created on 2017年12月6日 下午3:18:08
     */
    Result createRoom(Long userId, Room room);

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
    List<Room> findClubRoomList(Long clubId, Integer pageNo, Integer pageSize);

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
    List<Room> findClubRoomRecord(Long clubId, Long userId, Integer pageNo, Integer pageSize);

    /**
     * 获取俱乐部房间数
     *
     * @param clubId      俱乐部列表
     * @param onlyCurrent 是否仅进行中的房间
     * @return
     * @author yjy
     * Created on 2017年12月13日 下午3:24:10
     */
    int findCountByClub(Long clubId, boolean onlyCurrent);

}
