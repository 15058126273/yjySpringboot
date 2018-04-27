package com.yjy.test.game.util;

import java.util.List;

import com.yjy.test.util.hibernate.Pagination;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 分页工具
 *
 * @author wdy
 * @version ：2017年7月24日 下午6:49:51
 */
public class PaginationUtils {

    @SuppressWarnings({"unchecked", "static-access", "rawtypes"})
    public static Pagination jsonToPage(String json, Class bean) {
        Pagination p = null;
        if (StringUtils.isBlank(json))
            return p;

        List<?> list = null;
        JSONObject object = JSONObject.parseObject(json);
        JSONArray jsonList = object.getJSONArray("list");
        String listStr = jsonList.toJSONString(jsonList);
        list = JSON.parseArray(listStr, bean);

        p = JSON.parseObject(json, Pagination.class);
        p.setList(list);
        return p;
    }
}
