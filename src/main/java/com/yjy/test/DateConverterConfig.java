package com.yjy.test;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Date参数格式化
 *
 * 尝试格式化java.util.Date类型的参数
 *
 * @Author yjy
 * @Date 2018-04-27 9:58
 */
@Component
public class DateConverterConfig implements Converter<String, java.util.Date> {

    private static final String[] formats = new String[] {
            "yyyy-MM-dd", // 0
            "yyyy-MM", // 1
            "yyyy-MM-dd HH:mm:ss", // 2
            "yyyy-MM-dd HH:mm", // 3
    };


    /**
     * 这里将参数格式化成 java.sql.Date 为了方便后面用来拼接sql
     * @param param 日期格式的字符串
     * @return java.sql.Date
     */
    @Override
    public java.sql.Date convert(String param) {
        if (StringUtils.isBlank(param)) {
            return null;
        }
        param = param.trim();
        if (param.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDate(param, formats[0]);
        }
        if (param.matches("^\\d{4}-\\d{1,2}$")) {
            return parseDate(param, formats[1]);
        }
        if (param.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(param, formats[2]);
        }
        if (param.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}$")) {
            return parseDate(param, formats[3]);
        }
        throw new IllegalArgumentException("Invalid date param '" + param + "'");
    }

    /**
     * 格式化日期
     * @param dateStr 日期字符串
     * @param format 格式
     * @return 日期
     */
    private java.sql.Date parseDate(String dateStr, String format) {
        java.sql.Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            java.util.Date dates = simpleDateFormat.parse(dateStr);
            date = new java.sql.Date(dates.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
