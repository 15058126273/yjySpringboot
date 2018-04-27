package com.yjy.test.game.service;

import com.yjy.test.base.BaseService;
import com.yjy.test.game.entity.Log;
import com.yjy.test.util.hibernate.Pagination;

import java.util.Date;


public interface LogService extends BaseService<Log, Integer> {

    Pagination findList(Date startDate, Date endDate, String itemTitle,
                        String operator, Integer pageNo, Integer pageSize, Integer type,
                        Integer itemType) throws Exception;

}
