package com.yjy.test.game.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;

/**
 * 生成订单号
 *
 * @author wdy
 * @version ：2017年2月15日 下午6:19:48
 */
public class OrderNoUtils implements FactoryBean<String> {

    private static long counter = 1;


    @Override
    public synchronized String getObject() throws Exception {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String sequ = new DecimalFormat("000000").format(counter++);
        return date + sequ;
    }

    @Override
    public Class<?> getObjectType() {
        return String.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public static void reset() {
        OrderNoUtils.counter = 1;
    }

    public static void reset(long start) {
        OrderNoUtils.counter = start;
    }

    public static void lastOrderNoToday(String orderNo) {
        if (StringUtils.isBlank(orderNo))
            return;
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String oldDate = orderNo.substring(0, 8);
        if (date.equals(oldDate)) {
            String orderStr = orderNo.substring(8);
            long orderNum = Long.valueOf(orderStr).longValue();
            reset(++orderNum);
        } else {
            reset();
        }
        return;
    }

}
