package com.yjy.test.game.dao;

import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.Room;
import org.springframework.stereotype.Repository;

/**
 * 房间的dao层管理
 *
 * @author wdy
 * @version ：2017年5月24日 下午6:24:46
 */
@Repository
public interface RoomDao extends BaseJpaRepository<Room, Long> {

}
