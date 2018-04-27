package com.yjy.test.game.dao;

import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-24 17:29
 */
@Repository
public interface UserDao extends BaseJpaRepository<User, Long> {


}
