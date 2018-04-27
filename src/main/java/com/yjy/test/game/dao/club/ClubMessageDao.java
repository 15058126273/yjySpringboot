package com.yjy.test.game.dao.club;

import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.club.ClubMessage;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 俱乐部信息
 *
 * @Author yjy
 * @Date 2018-04-26 10:43
 */
public interface ClubMessageDao extends BaseJpaRepository<ClubMessage, Long> {

}
