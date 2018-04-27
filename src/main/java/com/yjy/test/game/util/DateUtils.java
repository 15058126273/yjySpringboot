package com.yjy.test.game.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Tom
 */
public class DateUtils {
    private StringBuffer buffer = new StringBuffer();
    private static String ZERO = "0";
    private static DateUtils date;
    public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat format1 = new SimpleDateFormat(
            "yyyyMMdd HH:mm:ss");
    public static SimpleDateFormat format2 = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    public static SimpleDateFormat format3 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat format5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String getNowString() {
        Calendar calendar = getCalendar();
        buffer.delete(0, buffer.capacity());
        buffer.append(getYear(calendar));

        if (getMonth(calendar) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(getMonth(calendar));

        if (getDate(calendar) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(getDate(calendar));
        if (getHour(calendar) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(getHour(calendar));
        if (getMinute(calendar) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(getMinute(calendar));
        if (getSecond(calendar) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(getSecond(calendar));
        return buffer.toString();
    }

    private static int getDateField(Date date, int field) {
        Calendar c = getCalendar();
        c.setTime(date);
        return c.get(field);
    }

    public static int getYearsBetweenDate(Date begin, Date end) {
        int bYear = getDateField(begin, Calendar.YEAR);
        int eYear = getDateField(end, Calendar.YEAR);
        return eYear - bYear;
    }

    public static int getMonthsBetweenDate(Date begin, Date end) {
        int bMonth = getDateField(begin, Calendar.MONTH);
        int eMonth = getDateField(end, Calendar.MONTH);
        return eMonth - bMonth;
    }

    public static int getWeeksBetweenDate(Date begin, Date end) {
        int bWeek = getDateField(begin, Calendar.WEEK_OF_YEAR);
        int eWeek = getDateField(end, Calendar.WEEK_OF_YEAR);
        return eWeek - bWeek;
    }

    public static int getDaysBetweenDate(Date begin, Date end) throws Exception {
        String beginStr = format.format(begin);
        begin = format.parse(beginStr);
        String endStr = format.format(end);
        end = format.parse(endStr);
        return (int) ((end.getTime() - begin.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 今天是否在这个日期里面
     */
    public static boolean todayIsInDate(Date startDate, Date endDate) throws Exception {
        boolean flag = true;
        if (null != startDate) {
            String startStr = format4.format(startDate);
            startStr += " 00:00:00";
            startDate = format5.parse(startStr);
            if (new Date().getTime() < startDate.getTime()) {
                flag = false;
            }
        }
        if (null != endDate) {
            String endStr = format4.format(endDate);
            endStr += " 23:59:59";
            startDate = format5.parse(endStr);
            if (new Date().getTime() > endDate.getTime()) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 获取date年后的amount年的第一天的开始时间
     *
     * @param amount 可正、可负
     * @return
     */
    public static Date getSpecficYearStart(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, amount);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return getStartDate(cal.getTime());
    }

    /**
     * 获取date年后的amount年的最后一天的终止时间
     *
     * @param amount 可正、可负
     * @return
     */
    public static Date getSpecficYearEnd(Date date, int amount) {
        Date temp = getStartDate(getSpecficYearStart(date, amount + 1));
        Calendar cal = Calendar.getInstance();
        cal.setTime(temp);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return getFinallyDate(cal.getTime());
    }

    /**
     * 获取date月后的amount月的第一天的开始时间
     *
     * @param amount 可正、可负
     * @return
     */
    public static Date getSpecficMonthStart(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, amount);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return getStartDate(cal.getTime());
    }

    /**
     * 获取当前自然月后的amount月的最后一天的终止时间
     *
     * @param amount 可正、可负
     * @return
     */
    public static Date getSpecficMonthEnd(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getSpecficMonthStart(date, amount + 1));
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return getFinallyDate(cal.getTime());
    }

    /**
     * 获取date周后的第amount周的开始时间（这里星期一为一周的开始）
     *
     * @param amount 可正、可负
     * @return
     */
    public static Date getSpecficWeekStart(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
        cal.add(Calendar.WEEK_OF_MONTH, amount);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getStartDate(cal.getTime());
    }

    /**
     * 获取date周后的第amount周的最后时间（这里星期日为一周的最后一天）
     *
     * @param amount 可正、可负
     * @return
     */
    public static Date getSpecficWeekEnd(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
        cal.add(Calendar.WEEK_OF_MONTH, amount);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return getFinallyDate(cal.getTime());
    }

    /**
     * 获取当前时间的前几天或后几天
     * 负数 往前推
     *
     * @param num
     * @return
     */
    public static Date getDateByDay(Date date, Integer num) {
        if (null == date)
            date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.DAY_OF_MONTH, num);
        return gc.getTime();
    }

    /**
     * 获取当前时间的前几天或后几天
     * 负数 往前推
     *
     * @param num
     * @return
     */
    public static Date getDateByMinute(Date date, Integer num) {
        if (null == date)
            date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.MINUTE, num);
        return gc.getTime();
    }

    public static Date getSpecficDateStart(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, amount);
        return getStartDate(cal.getTime());
    }

    /**
     * 得到指定日期的一天的的最后时刻23:59:59
     *
     * @param date
     * @return
     */
    public static Date getFinallyDate(Date date) {
        String temp = format.format(date);
        temp += " 23:59:59";

        try {
            return format1.parse(temp);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 得到指定日期的一天的开始时刻00:00:00
     *
     * @param date
     * @return
     */
    public static Date getStartDate(Date date) {
        String temp = format.format(date);
        temp += " 00:00:00";

        try {
            return format1.parse(temp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param date        指定比较日期
     * @param compareDate
     * @return
     */
    public static boolean isInDate(Date date, Date compareDate) {
        if (compareDate.after(getStartDate(date))
                && compareDate.before(getFinallyDate(date))) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取两个时间的差值秒
     *
     * @param d1
     * @param d2
     * @return
     */
    public static Integer getSecondBetweenDate(Date d1, Date d2) {
        Long second = (d2.getTime() - d1.getTime()) / 1000;
        return second.intValue();
    }

    private int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    private int getMonth(Calendar calendar) {
        return calendar.get(Calendar.MONDAY) + 1;
    }

    private int getDate(Calendar calendar) {
        return calendar.get(Calendar.DATE);
    }

    private int getHour(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    private int getMinute(Calendar calendar) {
        return calendar.get(Calendar.MINUTE);
    }

    private int getSecond(Calendar calendar) {
        return calendar.get(Calendar.SECOND);
    }

    private static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public static DateUtils getDateInstance() {
        if (date == null) {
            date = new DateUtils();
        }
        return date;
    }

    //实现当前时间执行的cron表达式
    public static String cronExpression(Date time) {
        String cron = null;
        int year = getDateField(time, Calendar.YEAR);
        int month = getDateField(time, Calendar.MONTH) + 1;
        int day = getDateField(time, Calendar.DAY_OF_MONTH);
        int hour = getDateField(time, Calendar.HOUR_OF_DAY);
        int minute = getDateField(time, Calendar.MINUTE);
        int second = getDateField(time, Calendar.SECOND);
        cron = second + " " + minute + " " + hour + " " + day + " " + month + " ? " + year;
        return cron;

    }

    public static String getNowString(Date date) {
        String temp = format2.format(date);
        return temp;
    }

    public static String getDateString(Date date) {
        String temp = format3.format(date);
        return temp;
    }

    public static Date strToDate(String str) {
        Date date = null;
        try {
            date = format3.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 该日期是否为昨天
     *
     * @param date
     * @return
     * @author wdy
     * @version ：2016年8月28日 下午5:50:43
     */
    public static Boolean isYesterday(Date date) {
        Date yesterday = org.apache.commons.lang3.time.DateUtils.addDays(new Date(), -1);
        String yesStr = format.format(yesterday);
        String dateStr = format.format(date);
        if (StringUtils.isNotBlank(yesStr) && StringUtils.isNotBlank(dateStr) && yesStr.equals(dateStr)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是某个月的第一天
     *
     * @param date
     * @return
     */
    public static Boolean isFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (1 == day) {
            return true;
        }
        return false;
    }

    public static String toShortStr(Date date) throws Exception {
        String str = format.format(date);
        return str;
    }

    public static Date toShortDate(Date date) throws Exception {
        String str = format.format(date);
        return format.parse(str);
    }

    public static Boolean isToday(Date date) throws Exception {
        String shortStr = DateUtils.toShortStr(new Date());
        String inDateStr = DateUtils.toShortStr(date);
        if (shortStr.equals(inDateStr)) {
            return true;
        }
        return false;
    }

    public static Date dateYearMonth(String dateYearMonth) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.parse(dateYearMonth);
    }

    public static Boolean isThisMonth(Date date) throws Exception {
        int month = getDateField(date, Calendar.MONTH);
        int thisMonth = getDateField(new Date(), Calendar.MONTH);
        if (month == thisMonth) {
            return true;
        }
        return false;
    }

    public static String dateToYearAndMonth(Date date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    public static Date monthToCurYearDate(Integer month) throws Exception {
        if (null != month && month.intValue() >= 1 && month.intValue() <= 12) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            String dateStr = "";
            if (month.intValue() >= 1 && month.intValue() <= 9) {
                dateStr = year + "0" + month.toString() + "01";
            } else {
                dateStr = year + month.toString() + "01";
            }
            return format.parse(dateStr);
        }
        return new Date();
    }

    @SuppressWarnings("deprecation")
    public static String shortDateStrToChineseYearMonth(String shortStr) throws Exception {
        Date parse = format.parse(shortStr);
        return (parse.getMonth() + 1) + "月" + parse.getDate() + "日";
    }

    @SuppressWarnings("deprecation")
    public static String dateToChineseYearMonth(Date date) throws Exception {
        return (date.getMonth() + 1) + "月" + date.getDate() + "日";
    }

    public static void main(String[] args) throws Exception {
        System.out.println(DateUtils.dateToChineseYearMonth(new Date()));
    }
}
