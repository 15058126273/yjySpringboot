package com.yjy.test.game.dao;

import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends BaseJpaRepository<Admin, Integer> {

}
