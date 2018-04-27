package com.yjy.test.game.dao;

import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.LoginRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRecordDao extends BaseJpaRepository<LoginRecord, Integer> {

}
