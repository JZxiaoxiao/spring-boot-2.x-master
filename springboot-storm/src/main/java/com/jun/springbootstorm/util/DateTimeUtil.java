package com.jun.springbootstorm.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public final class DateTimeUtil {

    private DateTimeUtil() {
    }

    /**
     * 获取当前日期和时间的中文显示
     *
     * @return
     */
    public static String nowCn() {
        return new SimpleDateFormat("yyyy年MM月dd日 HH时:mm分:ss秒").format(new Date());
    }

    /**
     * 获取当前日期和时间的英文显示
     *
     * @return
     */
    public static String nowEn() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String nowEns() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * 获取当前日期的中文显示
     *
     * @return
     */
    public static String nowDateCn() {
        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
    }

    /**
     * 获取当前日期的英文显示
     *
     * @return
     */
    public static String nowDateEn() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String yesterDateEn() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    public static String yesterDateEn2() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    }

    /**
     * 获取当前日期的yyyyMMdd显示
     *
     * @return
     */
    public static String nowDateyyyyMMdd() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    /**
     * 获取当前日期的yyyyMM显示
     *
     * @return
     */
    public static String nowDateyyyyMM() {
        return new SimpleDateFormat("yyyyMM").format(new Date());
    }

    /**
     * 获取当前时间的中文显示
     *
     * @return
     */
    public static String nowTimeCn() {
        return new SimpleDateFormat("HH时:mm分:ss秒").format(new Date());
    }

    /**
     * 获取当前时间的英文显示
     *
     * @return
     */
    public static String nowTimeEn() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    /**
     * 获取当前时间的英文显示
     *
     * @return
     */
    public static String getNowDate() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    public static int getDates() {
        Timestamp currTimestamp = getSysDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currTimestamp);
        return calendar.get(5);
    }

    public static Timestamp getSysDate() {
        return new Timestamp(System.currentTimeMillis());
    }
    /**
     * 获取某年某月第一天日期
     */
    public static String getCurrMonthFirst(int year, int month, String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    /**
     * 获取某年某月最后一天日期
     */
    public static String getCurrMonthLast(int year, int month, String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    /**
     * 获取当前月向前X个月的list
     *
     * @param count 向前推的月份数量
     */
    public static List<Map<String, Object>> getPerMonth(int count) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < count; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -i);
            Map<String, Object> map = new HashMap<String, Object>();
            int year = cal.get(Calendar.YEAR);
            int month = (cal.get(Calendar.MONTH) + 1);
            map.put("formatStr", year + "年" + month + "月");
            map.put("year", year);
            map.put("month", month);
            map.put("yyyyMM", String.valueOf(year) + (month < 10 ? "0" + month : month));
            list.add(map);
        }
        return list;
    }

    /**
     * 获取当前月向前X个月的list
     *
     * @param count 向前推的月份数量
     */
    public static List<Map<String, Object>> getMonthFromPerMonth(int count) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 1; i < count + 1; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -i);
            Map<String, Object> map = new HashMap<String, Object>();
            int year = cal.get(Calendar.YEAR);
            int month = (cal.get(Calendar.MONTH) + 1);
            map.put("formatStr", year + "年" + month + "月");
            map.put("year", year);
            map.put("month", month);
            map.put("yyyyMM", String.valueOf(year) + (month < 10 ? "0" + month : month));
            list.add(map);
        }
        return list;
    }

    /**
     * 获取当前毫秒数
     */
    public static long getNowDateTime() {
        return (new Date()).getTime();
    }

    public static String timstamp2String(Long timestamp,String pattern) {
        if (timestamp == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);// 定义格式，不显示毫秒
        String str = df.format(timestamp);
        return str;
    }

    public static String timstamp2String(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 定义格式，不显示毫秒
        String str = df.format(timestamp);
        return str;
    }

    public static String timstampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 定义格式，不显示秒
        String str = df.format(timestamp);
        if (str != null && str.length() > 16) {
            str = str.substring(0, 16);
        }
        return str;
    }

    public static String timstamp3String(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 定义格式，不显示毫秒
        String str = df.format(timestamp);
        if (str != null && str.length() > 10) {
            str = str.substring(0, 10);
        }
        return str;
    }

    public static Timestamp stringToTimstamp(String timestamp) {
        if (timestamp == null || timestamp.length() < 10) {
            return null;
        }
        return Timestamp.valueOf(timestamp.trim() + " 00:00:00");
    }

    public static Timestamp stringToTimstamp(int type, String timestamp) {
        String timeStr = null;
        if (type == 1) {
            timeStr = " 23:59:59";
        } else {
            timeStr = " 00:00:00";
        }
        if (timestamp == null || timestamp.length() < 10) {
            return null;
        }
        return Timestamp.valueOf(timestamp.trim() + timeStr);
    }

    public static Timestamp string2Timstamp(String timestamp){
        return Timestamp.valueOf(timestamp);
    }

    public static long getNextMonthStartTime() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        if (month == 11) {
            year += 1;
            month = 0;
        } else {
            month += 1;
        }
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return cal.getTime().getTime();
    }

    public static String timstamp2Dt(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 定义格式，不显示毫秒
        return df.format(timestamp);
    }

    /**
     * 格式化日期串
     */
    public static String getFormatDate(Date date, String formatStr) {
        if (formatStr == null || null == date) {
            return "";
        } else {
            return new SimpleDateFormat(formatStr).format(date);
        }
    }

    /**
     * 当前月份
     */
    public static String getCurrentDateMonth_() {
        return getFormatDate(new Date(), "yyyy-MM");
    }

    /**
     * 当前月份
     */
    public static String getCurrentDateMonth() {
        return getFormatDate(new Date(), "yyyyMM");
    }

    /**
     * 获取当月的首日和末日 例如：
     */
    public static String getMothFday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
        // 当前月的第一天
        cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
        Date beginTime = cal.getTime();
        return datef.format(beginTime);
    }

    public static String getMothLday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
        // 当前月的最后一天
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        Date endTime = cal.getTime();
        return datef.format(endTime);
    }

    /**
     * 传入时间戳 返回YYYYMM
     */
    public static String timstamp4String(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");// 定义格式，不显示毫秒
        String str = df.format(timestamp);
        if (str != null && str.length() > 10) {
            str = str.substring(0, 10);
        }
        return str;
    }

    public static String timstamp2StringYMD(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 定义格式，不显示毫秒
        return df.format(timestamp);
    }

    /**
     * 计算月份差
     *
     * @param start
     * @param end
     * @return
     */
    public static int getMonth(Timestamp start, Timestamp end) {
        if (start != null && end != null) {
            @SuppressWarnings("deprecation")
            int endMonth = end.getYear() * 12 + end.getMonth();
            @SuppressWarnings("deprecation")
            int startMonth = start.getYear() * 12 + start.getMonth();
            return endMonth - startMonth;
        } else {
            return 0;
        }
    }

    /**
     * 获取本月第一秒
     *
     * @param sysDate
     * @return
     * @author zhouyy5
     */
    public static Timestamp getTimeThisMonth27FirstSec(Timestamp sysDate, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.SECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取UTC时间
     */
    public static String getDateUTC() {
        Date l_datetime = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return formatter.format(l_datetime);
    }

    /**
     * 时间加上时长
     *
     * @param startTime
     * @param duration
     * @return
     * @author zhouyy5
     * @ApiDocMethod
     */
    public static String getEndTime(String startTime, String duration) {
        String time = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date d = df.parse(startTime);
            time = df.format(new Date(d.getTime() + Long.valueOf(duration) * 1000));

        } catch (ParseException e) {
            System.out.println("上网记录查询：结算结束时间出错" + e.getMessage());
        }
        return time;
    }

    /**
     * 时间格式转换
     *
     * @param str
     * @return date
     */
    public static String strToDate(String str) {
        Date date = null;
        String strs = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            date = format.parse(str);
            strs = format1.format(date);

        } catch (ParseException e) {
            System.out.println("上网记录查询：时间转换错误" + e.getMessage());
        }
        return strs;
    }

    /**
     * 时间格式转换
     *
     * @param str
     * @return date
     */
    public static Date stringToDate(String str) {
        Date date = null;
        try {
            //SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format1.parse(str);
        } catch (ParseException e) {
            System.out.println("上网记录查询：时间转换错误" + e.getMessage());
        }
        return date;
    }

    /**
     * 获取上几个月第一秒
     *
     * @param sysDate
     * @return
     * @author hougang
     */
    public static Timestamp getTimePreMonthFirstSec(Timestamp sysDate, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(Calendar.MONTH, -month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.SECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取上几个月最后一秒
     *
     * @param sysDate
     * @return
     * @author hougang
     */
    public static Timestamp getTimePreMonthLastSec(Timestamp sysDate, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(Calendar.MONTH, 1 - month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.SECOND, -1);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取当前账期的开始时间
     *
     * @param billDay
     * @return
     */
    public static Timestamp getBillActiveTime(int billDay) {
        int theDay = getDates();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, billDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (theDay < billDay) {
            //小于月结日则为上月
            calendar.add(Calendar.MONTH, -1);
        }
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取当前时间的账期结束时间
     *
     * @param billDay
     * @return
     */
    public static Timestamp getBillInactiveTime(int billDay) {
        int theDay = getDates();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, billDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (theDay >= billDay) {
            //大于等于月结日则为下月
            calendar.add(Calendar.MONTH, 1);
        }
        //小于月结日则为当月,则无需处理
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取指定账期的开始时间
     *
     * @param billDay
     * @return
     */
    public static Long getBillActiveTime(int billDay, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int theDay = calendar.get(5);
        calendar.set(Calendar.DAY_OF_MONTH, billDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (theDay < billDay) {
            //小于月结日则为上月
            calendar.add(Calendar.MONTH, -1);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定时间的账期结束时间
     *
     * @param billDay
     * @return
     */
    public static Long getBillInactiveTime(int billDay, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int theDay = calendar.get(5);
        calendar.set(Calendar.DAY_OF_MONTH, billDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (theDay >= billDay) {
            //大于等于月结日则为下月
            calendar.add(Calendar.MONTH, 1);
        }
        //小于月结日则为当月,则无需处理
        return calendar.getTimeInMillis();
    }


    public static Timestamp getOffsetMonthsTime(Timestamp oldTime,
                                                int offsetDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldTime);
        calendar.add(Calendar.MONTH, offsetDays);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取指定月份的偏移月数所在的月
     *
     * @param date        yyyyMM
     * @param offsetMonth （正数：以后，负数：以前）
     * @return
     * @author zhouyy5
     */
    public static String getOffsetMonth(String date, int offsetMonth, String pattern) {
        SimpleDateFormat timeformat = new SimpleDateFormat(pattern);

        Calendar cal = new GregorianCalendar();
        try {
            cal.setTime(timeformat.parse(date));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        cal.add(Calendar.MONTH, offsetMonth);
        return timeformat.format(cal.getTime());
    }

    /**
     * 获取指定月份的偏移月数所在的月
     *
     * @param date        yyyyMM
     * @param offsetMonth （正数：以后，负数：以前）
     * @return
     * @author zhouyy5
     */
    public static Timestamp getOffsetMonth(Timestamp date, int offsetMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, offsetMonth);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取系统年月
     */
    public static String getCurYM(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);// 设置日期格式
        Calendar calender = Calendar.getInstance();
        return df.format(calender.getTime());
    }

    /**
     * 获取下月第一秒
     *
     * @param sysDate
     * @return
     * @author zhouyy5
     */
    public static Timestamp getTimeMonthFirstSec(Timestamp sysDate, int month, int offMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(Calendar.MONTH, offMonth);
        calendar.set(Calendar.DAY_OF_MONTH, month);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.SECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }


    public static Timestamp getTimeThisMonthLastSec(Timestamp sysDate, int month, int offMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(Calendar.MONTH, offMonth);
        calendar.set(Calendar.DAY_OF_MONTH, month);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.SECOND, -1);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取指定时间的偏移月份后的时间
     *
     * @param sysDate
     * @param offsetDays
     * @return
     * @author yangpy
     */
    public static Timestamp getOffsetHourTime(Timestamp sysDate, int offsetHours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(Calendar.HOUR, offsetHours);
        return new Timestamp(calendar.getTimeInMillis());
    }


    public static Timestamp getTimestamp(String time, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        format.setLenient(false);
        Timestamp ts = null;
        try {
            ts = new Timestamp(format.parse(time).getTime());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return ts;
    }

    /**
     * 获取某一小时的最后一秒
     *
     * @param sysDate
     * @return
     * @author zhouyy5
     */
    public static Timestamp getTheHourLastSecond(Timestamp sysDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        //calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取系统年月
     */
    public static String getLtCurYM(String pattern, int offMonth) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);// 设置日期格式
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.MONTH, offMonth);
        return df.format(calender.getTime());
    }

    /**
     * 获取今天是本月第几天
     *
     * @return
     * @author zhouyy5
     */
    public static int getDay() {
        Timestamp currTimestamp = getSysDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currTimestamp);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取今天的小时
     *
     * @return
     * @author zhouyy5
     */
    public static int getHour() {
        Timestamp currTimestamp = getSysDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currTimestamp);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取今天小时中的分
     *
     * @return
     * @author zhouyy5
     */
    public static int getMinute() {
        Timestamp currTimestamp = getSysDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currTimestamp);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取这个月中的具体月
     *
     * @return
     * @author zhouyy5
     */
    public static int getMonth() {
        Timestamp currTimestamp = getSysDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currTimestamp);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 计算两个日期的时间差(月)
     *
     * @param formatTime1
     * @param formatTime2
     * @return
     * @author zhangxd7
     */
    public static int getTimeDifferenceMonth(Timestamp formatTime1, Timestamp formatTime2) {

        Calendar calendarTime1 = Calendar.getInstance();
        calendarTime1.setTime(formatTime1);
        int yearTime1 = calendarTime1.get(Calendar.YEAR);
        int monthTime1 = calendarTime1.get(Calendar.MONTH);
        int dayTime1 = calendarTime1.get(Calendar.DAY_OF_MONTH);

        Calendar calendarTime2 = Calendar.getInstance();
        calendarTime2.setTime(formatTime2);
        int yearTime2 = calendarTime2.get(Calendar.YEAR);
        int monthTime2 = calendarTime2.get(Calendar.MONTH);
        int dayTime2 = calendarTime2.get(Calendar.DAY_OF_MONTH);

        int y = yearTime2 - yearTime1;// 年差
        int m = monthTime2 - monthTime1;// 月差
        int d = dayTime2 - dayTime1;// 天差

        if (d > 0) {
            // 如果天数差大于零
            return (y * 12 + m + 1);
        } else {
            return (y * 12 + m);
        }
    }

    /**
     * 计算两个日期的时间差(天)
     *
     * @param startTime
     * @param endTime
     * @return
     * @author zhangxd7
     */
    public static int getTimeDifferenceDay(Timestamp startTime, Timestamp endTime) {
        Calendar calendarTime1 = Calendar.getInstance();
        calendarTime1.setTime(startTime);
        Calendar calendarTime2 = Calendar.getInstance();
        calendarTime2.setTime(endTime);
        int day1 = calendarTime1.get(Calendar.DAY_OF_YEAR);
        int day2 = calendarTime2.get(Calendar.DAY_OF_YEAR);
        int yearTime1 = calendarTime1.get(Calendar.YEAR);
        int yearTime2 = calendarTime2.get(Calendar.YEAR);
        if (Math.abs(yearTime1 - yearTime2) > 0) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Math.min(yearTime1, yearTime2));
            return cal.getActualMaximum(Calendar.DAY_OF_YEAR) - day1 + day2;
        } else {
            return Math.abs(day2 - day1);
        }
    }

    public static void main(String[] args) {
        System.out.println(getCurrentDateMonth());
        ParsePosition pos = new ParsePosition(0);
        Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse("201905280000",pos);
        //Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(startDate+startTime,pos);
        Long billTime = DateTimeUtil.getBillActiveTime(27,date);
        Timestamp billTimestamp = new Timestamp(billTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(billTimestamp);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if(!String.valueOf(day).equals("1")){
            //不为1号则取下月
            calendar.set(Calendar.DAY_OF_MONTH,1);
            calendar.add(Calendar.MONTH, 1);
        }
        String result = new SimpleDateFormat("yyyyMM").format(calendar.getTime());
        System.out.println(result);
    }
}
