package com.servyou.test.demo.test;


import org.codehaus.groovy.util.StringUtil;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateUtils {
    /**
     * yyyy-MM-dd格式
     */
    public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm格式
     */
    public static final String DATE_FORMAT_TIME_R = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH格式
     */
    public static final String DATE_FORMAT_TIME_H = "yyyy-MM-dd HH";
    /**
     * HH格式
     */
    public static final String DATE_FORMAT_H = "HH";
    /**
     * yyyy-MM-dd HH:mm:ss格式
     */
    public static final String DATE_FORMAT_TIME_T = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyyMMddHHmmss格式
     */
    public static final String DB_TIME_PATTERN = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_YYYYMMDD_TWO = "yyyyMMdd";

    public static final String DATE_FORMAT_YYYYMM = "yyyymm";

    public static String[] getBegainAndEndTime(Date date, String mode) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date[] dates = getBegainAndEndDate(date, mode);

        return new String[]{format.format(dates[0]), format.format(dates[1])};
    }

    public static Date[] getBegainAndEndDate(Date date, String mode) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date begin = new Date();
        Date end = new Date();

        if (mode == null || mode.equalsIgnoreCase("日") || mode.equalsIgnoreCase("day")) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            begin = calendar.getTime();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            end = calendar.getTime();
        } else if (mode.equalsIgnoreCase("周") || mode.equalsIgnoreCase("week")) {
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            begin = calendar.getTime();
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            end = calendar.getTime();
        } else if (mode.equalsIgnoreCase("月") || mode.equalsIgnoreCase("month") || mode.equalsIgnoreCase("0")) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            begin = calendar.getTime();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            end = calendar.getTime();
        } else if (mode.equalsIgnoreCase("季") || mode.equalsIgnoreCase("season")) {
            int month = calendar.get(Calendar.MONTH) + 1;
            if (month % 3 == 0) {// 季度结束月 (3,6,9)
                calendar.set(Calendar.MONTH, month - 3);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                begin = calendar.getTime();
                calendar.set(Calendar.MONTH, month - 1);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                end = calendar.getTime();
            } else if (month % 3 == 1) {// 季度起始月 (1,4,7)
                calendar.set(Calendar.MONTH, month - 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                begin = calendar.getTime();
                calendar.set(Calendar.MONTH, month + 1);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                end = calendar.getTime();
            } else if (month % 3 == 2) {// 季度中间月 (2,5,8)
                calendar.set(Calendar.MONTH, month - 2);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                begin = calendar.getTime();
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                end = calendar.getTime();
            }
        } else if (mode.equalsIgnoreCase("半年") || mode.equalsIgnoreCase("halfyear")) {
            int month = calendar.get(Calendar.MONTH) + 1;
            if (month < 6) {
                // 上半年
                calendar.set(Calendar.MONTH, 0);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                begin = calendar.getTime();
                calendar.set(Calendar.MONTH, 4);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                end = calendar.getTime();
            } else {
                // 下半年
                calendar.set(Calendar.MONTH, 5);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                begin = calendar.getTime();
                calendar.set(Calendar.MONTH, 11);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                end = calendar.getTime();
            }
            end = calendar.getTime();
        } else if (mode.equalsIgnoreCase("年") || mode.equalsIgnoreCase("year")) {
            calendar.set(Calendar.DAY_OF_YEAR, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            begin = calendar.getTime();
            calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            end = calendar.getTime();
        }

        return new Date[]{begin, end};
    }

    /**
     * 得到当前月份的1日
     *
     * @return
     */
    public static Date getMonthBegain() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 得到当前日期月份的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getMonthEnd(int year, int month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
        return format.format(calendar.getTime());
    }

    /**
     * 日期增加或减少几小时
     *
     * @param oriDate
     * @param amount
     * @return
     */
    public static Date addHour(Date oriDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(oriDate);
        cal.add(Calendar.HOUR_OF_DAY, amount);
        return cal.getTime();
    }

    /**
     * 日期增加或减少几分钟
     *
     * @param oriDate
     * @param amount
     * @return
     */
    public static Date addMinute(Date oriDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(oriDate);
        cal.add(Calendar.MINUTE, amount);
        return cal.getTime();
    }

    /**
     * 日期增加或减少几秒钟
     *
     * @param oriDate
     * @param amount
     * @return
     */
    public static Date addSecond(Date oriDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(oriDate);
        cal.add(Calendar.SECOND, amount);
        return cal.getTime();
    }

    /**
     * 日期增加或减少几天
     *
     * @param oriDate
     * @param amount
     * @return
     */
    public static Date addDay(Date oriDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(oriDate);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    /**
     * 日期增加或减少几月
     *
     * @param oriDate
     * @param amount
     * @return
     */
    public static Date addMonth(Date oriDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(oriDate);
        cal.add(Calendar.MONTH, amount);
        return cal.getTime();
    }

    /**
     * 日期增加或减少几年
     *
     * @param oriDate
     * @param amount
     * @return
     */
    public static Date addYear(Date oriDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(oriDate);
        cal.add(Calendar.YEAR, amount);
        return cal.getTime();
    }

    /**
     * 格式化成年月日的形式
     *
     * @param date
     * @return
     */
    public static String formatYyyyMMdd(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);

    }

    /**
     * 格式化成24小时制的形式
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);

    }

    /**
     * 格式化成指定的形式
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);

    }

    /**
     * 本周开始
     *
     * @return
     */
    public static String getThisWeekOfFirstDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return format.format(calendar.getTime());
    }

    /**
     * 本季度开始
     *
     * @return
     */
    public static String getCurrentQuarterStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            return format.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换日期
     *
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date, String pattern) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.parse(date);

    }

    /**
     * 日期大小比较
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compare(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            throw new RuntimeException("两个日期型都为null，不能比较");
        } else if (date1 == null) {
            return -1;
        } else if (date2 == null) {
            return 1;
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);
        return calendar1.compareTo(calendar2);
    }

    /**
     * 两个String格式时间的比较
     *
     * @param stringTimeA
     * @param stringTimeB
     * @param str
     * @return
     */
    public static Integer compareWith(String stringTimeA, String stringTimeB, String str) {
        SimpleDateFormat format = new SimpleDateFormat(str);
        Date dateA = null;
        Date dateB = null;
        try {
            dateA = format.parse(stringTimeA);
            dateB = format.parse(stringTimeB);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateA.compareTo(dateB);
    }

    public static void main(String[] args) {
        System.out.println(round(0.111));
    }

    private static BigDecimal round(double data) {
        return BigDecimal.valueOf(data).setScale(4, BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 两个日期相减，返回两个时间的差值(小时为单位)，保留两位小数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static double subHh(Date date1, Date date2) {
        long mill = Math.abs(date1.getTime() - date2.getTime());
        return (double) (Math.round((double) mill / (1000 * 60 * 60) * 100) / 100.0);

    }

    /**
     * 两个日期相减，返回两个时间的差值(小时为单位)，保留1位小数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static double subHh1(Date date1, Date date2) {
        long mill = Math.abs(date1.getTime() - date2.getTime());
        return (double) (Math.round((double) mill / (1000 * 60 * 60) * 10) / 10.0);

    }

    /**
     * 是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean sameDay(Date date1, Date date2) {
        Date[] begainEnd1 = getBegainAndEndDate(date1, "day");
        Date[] begainEnd2 = getBegainAndEndDate(date2, "day");
        return compare(begainEnd1[0], begainEnd2[0]) == 0;
    }

    /**
     * 是否是同一个月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean sameMonth(Date date1, Date date2) {
        Date[] begainEnd1 = getBegainAndEndDate(date1, "month");
        Date[] begainEnd2 = getBegainAndEndDate(date2, "month");
        return compare(begainEnd1[0], begainEnd2[0]) == 0;
    }

    /**
     * 是否是同一年
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean sameYear(Date date1, Date date2) {
        Date[] begainEnd1 = getBegainAndEndDate(date1, "year");
        Date[] begainEnd2 = getBegainAndEndDate(date2, "year");
        return compare(begainEnd1[0], begainEnd2[0]) == 0;
    }

    /**
     * 得到date所在的天
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 得到date所在的月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到date所在的年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 根据月份获取月份所在季度，如果为0，表示月份异常（不在1-12范围内）
     *
     * @param month
     * @return
     */
    public static int getQuarter(int month) {
        if (month >= 1 && month <= 3) return 1;
        else if (month > 3 && month < 7) return 2;
        else if (month > 6 && month < 10) return 3;
        else if (month > 9 && month < 13) return 4;
        else return 0;
    }

    /**
     * 判断是否是双休日，按照中国当地的休息日来计算
     *
     * @param date
     * @return
     */
    public static boolean isWeek(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(format.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1 || dayOfWeek == 7) return true;
        return false;
    }

    /**
     * 毫秒格式化成:天,小时,分,秒,毫秒
     *
     * @param ms
     * @return
     */
    public static String milliSecondformatTime(Long ms) {
        if (null == ms) {
            return "";
        }
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;
        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
        StringBuffer sb = new StringBuffer();
        if (day > 0) sb.append(day + "天");
        if (hour > 0) sb.append(hour + "小时");
        if (minute > 0) sb.append(minute + "分");
        if (second > 0) sb.append(second + "秒");
        if (milliSecond > 0) sb.append(milliSecond + "毫秒");
        return sb.toString();
    }

    /**
     * 获取当前月份具有的天数
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return dayOfMonth;
    }

    /**
     * 获取当前日期属于周几
     *
     * @param date
     * @return
     */
    public static String getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeek = null;
        switch (i) {
            case 1:
                dayOfWeek = "星期天";
                break;
            case 2:
                dayOfWeek = "星期一";
                break;
            case 3:
                dayOfWeek = "星期二";
                break;
            case 4:
                dayOfWeek = "星期三";
                break;
            case 5:
                dayOfWeek = "星期四";
                break;
            case 6:
                dayOfWeek = "星期五";
                break;
            case 7:
                dayOfWeek = "星期六";
                break;
        }
        return dayOfWeek;
    }

    /**
     * 获取当前日期的上月
     *
     * @return
     */
    public static String getPreMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        return format(cal.getTime(), "yyyy-MM");
    }

    /**
     * 验证日期是否合法
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static boolean validateDate(String date, String formatStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 根据当前日期获取上周以及本周的日期
     *
     * @return
     */
    public static List<String> getWeeks() {
        Calendar firstCal = Calendar.getInstance();
        firstCal.setTime(new Date());
        int dayOfWeek = firstCal.get(Calendar.DAY_OF_WEEK);
        firstCal.add(Calendar.DAY_OF_YEAR, -(dayOfWeek + 5));
        List<String> dateList = new ArrayList<String>();
        for (int i = 0; i < 14; i++) {
            Calendar Cal = Calendar.getInstance();
            Cal.setTime(firstCal.getTime());
            Cal.add(Calendar.DAY_OF_YEAR, i);
            dateList.add(format(Cal.getTime(), "yyyy-MM-dd"));
        }
        return dateList;
    }

    public static Date parseDate(String date) {
        if (date == null || date.trim().length() < 1) {
            return null;
        }

        String strFormat = "";
        if (!StringUtils.isEmpty(strFormat)) {
            strFormat = DATE_FORMAT_YYYYMMDD;
            if (date.length() > 16) {
                strFormat = DATE_FORMAT_TIME_T;
            } else if (date.length() > 10) {
                strFormat = DATE_FORMAT_TIME_R;
            }
        }
        SimpleDateFormat df = new SimpleDateFormat(strFormat);
        Date d = null;
        try {
            d = df.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return d;

    }

}
