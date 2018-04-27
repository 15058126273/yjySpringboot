package com.yjy.test.base;

import org.apache.commons.lang3.StringUtils;

public abstract class BaseClass {

    protected static final int YES = 1;
    protected static final int NO = 0;

    /**
     * 验证字符串
     * @param s 字符串
     * @return 是否为空
     */
    protected static boolean isBlank(String s) {
        return StringUtils.isBlank(s);
    }

    /**
     * 验证字符串
     * @param s 字符串
     * @return 是否不为空
     */
    protected static boolean notBlank(String s) {
        return StringUtils.isNotBlank(s);
    }

    /**
     * 验证字符串
     * @param s 字符串
     * @return 是否数字
     */
    protected static boolean isNumber(String s) {
        return StringUtils.isNumeric(s);
    }

}
