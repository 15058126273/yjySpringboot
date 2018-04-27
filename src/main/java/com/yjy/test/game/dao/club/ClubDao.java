package com.yjy.test.game.dao.club;


import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.club.Club;
import org.springframework.stereotype.Repository;

/**
 * 俱乐部
 */
@Repository
public interface ClubDao extends BaseJpaRepository<Club, Long> {

}
