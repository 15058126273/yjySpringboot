package com.yjy.test.game.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串类型的工具类
 *
 * @author wdy
 * @version ：2016年8月28日 下午5:58:51
 */
public class StrUtils {

    public static Set<String> cDifferentRandoms(int n, int m) { // 产生n个只含有数字和字母长度为m（m<=52）的无重复随机字符串
        if (m > 52) {
            return null;
        } else {
            Set<String> set = new HashSet<String>();
            while (set.size() < n) {
                set.add(cRandom(m));
            }
            return set;
        }
    }

    public static String cRandom(int m) { // 产生1个长度为m只含有字母的随机字符串
        char[] chs = new char[m];
        for (int i = 0; i < m; i++) {
            chs[i] = cNumOrCharRandom();
        }
        return new String(chs);
    }

    public static char cNumOrCharRandom() { // 产生一个随机数字或字母
        String temp = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
        return (char) temp.charAt(iRandom(0, 61));
    }

    public static int iRandom(int m, int n) { // 产生一个[m,n)之间的随机整数
        Random random = new Random();
        int small = m > n ? n : m;
        int big = m <= n ? n : m;
        return small + random.nextInt(big - small);
    }

    //生成随机数字和字母,
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 生成指定长度的数字随机码
     *
     * @param length 长度
     * @return
     */
    public static String createNumCode(int length) {
        if (length < 1) length = 1;
        int i = (int) Math.pow(10, length);
        int code = iRandom(i / 10, i - 1);
        return String.valueOf(code);
    }

    /**
     * 是不是手机号
     */
    public static boolean isMobileNumber(String mobiles) {
        Pattern p = Pattern.compile("^1[34578]\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String emojiGun(String s) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        while (m.find()) {
            s = m.replaceAll("*");
        }
        return s;
    }

}
