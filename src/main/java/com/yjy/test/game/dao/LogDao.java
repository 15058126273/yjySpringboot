package com.yjy.test.game.dao;

import java.util.Date;
import java.util.List;

import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.Log;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao extends BaseJpaRepository<Log, Integer> {

}
