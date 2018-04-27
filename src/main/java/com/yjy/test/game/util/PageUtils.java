package com.yjy.test.game.util;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

public class PageUtils {

    /**
     * @param jsonParam json格式字符串{}
     * @param pageNo    起始页码
     * @param pageSize  每页显示数量
     * @return
     */
    public static String pagination(String jsonParam, Integer pageNo, Integer pageSize) {
        int begin = (pageNo.intValue() - 1) * pageSize.intValue();
        String str = "{";
        String jsonP = jsonParam.substring(1, jsonParam.length() - 1);
        str += jsonP;
        if (StringUtils.isNotBlank(jsonP)) {
            str += ",";
        }
        str += "'pagebegin'" + ":" + begin;

        str += "," + "'pagesize'" + ":" + pageSize;
        str += "}";
        return str;
    }

    /**
     * @param entity   实体对象
     * @param pageNo   起始页码
     * @param pageSize 每页显示数量
     * @return
     */
    public static String pagination(Object entity, Integer pageNo, Integer pageSize) {
        int begin = pageNo.intValue() * pageSize.intValue();
        String str = null;
        if (null != entity) {
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(entity.getClass());
            String jsonP = JSON.toJSONString(entity, filter, SerializerFeature.BrowserCompatible);
            str = "{";
            str += jsonP.substring(1, jsonP.length() - 1);
            if (null != pageNo) {
                str += "," + "'pagebegin'" + ":" + begin;
            }
            if (null != pageSize) {
                str += "," + "'pagesize'" + ":" + pageSize;
            }
            str += "}";
        }
        return str;
    }

}
