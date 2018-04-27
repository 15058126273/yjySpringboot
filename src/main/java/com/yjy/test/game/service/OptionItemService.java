package com.yjy.test.game.service;

import com.yjy.test.base.BaseService;
import com.yjy.test.game.entity.OptionItem;

import java.util.List;

/**
 * 下拉信息的service
 *
 * @author wdy
 * @version ：2017年1月5日 下午7:28:44
 */
public interface OptionItemService extends BaseService<OptionItem, Integer> {

    /**
     * 根据字段获取列表
     *
     * @param field
     * @param isAll
     * @return
     * @author wdy
     * @version ：2015年4月15日 下午6:02:02
     */
    public List<OptionItem> listByField(String field, Boolean isAll);

    /**
     * 获取所有的字段的名称
     *
     * @return
     * @author wdy
     * @version ：2015年4月15日 下午6:01:38
     */
    public List<String> getAllFieldName();

}
