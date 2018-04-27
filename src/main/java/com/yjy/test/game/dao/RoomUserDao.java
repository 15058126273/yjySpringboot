package com.yjy.test.game.dao;

import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.RoomUser;
import org.springframework.stereotype.Repository;

/**
 * 房间用户的信息dao
 *
 * @author wdy
 * @version ：2017年5月24日 下午6:29:00
 */
@Repository
public interface RoomUserDao extends BaseJpaRepository<RoomUser, Long> {

}
