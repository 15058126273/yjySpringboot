package com.yjy.test.game.dao;

import com.yjy.test.base.BaseJpaRepository;
import com.yjy.test.game.entity.OptionItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 下拉选项的信息dao
 *
 * @author wdy
 * @version ：2017年1月5日 下午6:54:12
 */
@Repository
public interface OptionItemDao extends BaseJpaRepository<OptionItem, Integer> {

}
