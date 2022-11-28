package com.netwisd.base.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by pacbj-dongchenglong on 2018年9月25日,0025.
 */
public final class DateUtil extends org.apache.commons.lang3.time.DateUtils {

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
            "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * <li>格式"yyyy-MM-dd"
     */
    public static String ISO_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * <li>格式"yyyy-MM-dd HH:mm:ss"
     */
    public static String ISO_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate(ISO_DATE_FORMAT);
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 将String的日期转换格式为yyyy-MM-dd
     *
     * @return
     */
    public static String changeDate(String times) {
        if (null != times) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = null;
            try {
                parse = simpleDateFormat.parse(times);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return simpleDateFormat.format(parse);
        }
        return times;
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd）
     *
     * @param pattern 可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, ISO_DATE_FORMAT);
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, ISO_TIME_FORMAT);
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), ISO_TIME_FORMAT);
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当天字符串 格式（HH）
     */
    public static String getHour() {
        return formatDate(new Date(), "HH");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
     * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return long
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return long
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return long
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return String
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * <li>由"yyyy-MM-dd HH:mm:ss"格式的字符串转换为Timestamp类型的值返回。
     *
     * @param time 转换的时间字符串
     * @return Timestamp
     */
    public static Timestamp stringToTimestampTime(String time) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(ISO_TIME_FORMAT);
        Date dTime = sf.parse(time);
        Long dTimeLong = dTime.getTime();
        Timestamp returnTime = new Timestamp(dTimeLong);
        return returnTime;
    }

    /**
     * <li>由"yyyy-MM-dd"格式的字符串转换为Timestamp类型的值返回。
     *
     * @param time
     */
    public static Timestamp stringToTampTime(String time) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(ISO_DATE_FORMAT);
        Date dTime = sf.parse(time);
        Long dTimeLong = dTime.getTime();
        Timestamp returnTime = new Timestamp(dTimeLong);
        return returnTime;
    }

    /**
     * <li>由"yyyy-MM-dd HH:mm:ss"格式的字符串转换为Date类型的值返回。
     *
     * @param time 转换的时间字符串
     * @return Date
     */
    public static Date stringToDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat(ISO_TIME_FORMAT);
        try {
            Date date = format.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <li>由"yyyy-MM-dd HH:mm:ss"格式的字符串转换为Date类型的值返回。
     *
     * @param time 转换的时间字符串
     * @return Date
     */
    public static Date stringToDate(String time,String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = format.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <li>由Timestamp类型的值转换为"yyyy-MM-dd HH:mm:ss"格式的字符串返回。
     *
     * @param time 需要格式化的时间
     * @return String
     */
    public static String timestampToStringTime(Timestamp time) {
        DateFormat df = new SimpleDateFormat(ISO_TIME_FORMAT);
        return df.format(time);
    }

    /**
     * <li>由Timestamp类型的值转换为"yyyy-MM-dd"格式的字符串返回。
     *
     * @param time
     */
    public static String tampTimeToString(Timestamp time) {
        DateFormat df = new SimpleDateFormat(ISO_DATE_FORMAT);
        return df.format(time);
    }

    /**
     * <li>获得 Timestamp类型的当前时间
     *
     * @return timestamp
     */
    public static Timestamp currentTimestamp() {
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(ISO_TIME_FORMAT);
        Timestamp timestamp = Timestamp.valueOf(sf.format(now));
        return timestamp;
    }

    /**
     * String转date 转string
     *
     * @param str
     * @return
     */
    public static String stringtoDatetoString(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_DATE_FORMAT);
        Date date = simpleDateFormat.parse(str);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DATE, 1);
        return simpleDateFormat.format(instance.getTime());
    }

    /**
     * 获得 Date类型的当前时间
     *
     * @return date
     */
    public static Date currentDate() {
        return new Date();
    }

    /**
     * 将时间戳转化成日期类型
     *
     * @param timestamp
     * @return
     */
    public static Date timestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    /**
     * 将long转化成日期类型
     *
     * @return
     */
    public static Date longToDate(long time) {
        return new Date(time);
    }

    /**
     * 将long转化成时间戳
     *
     * @return
     */
    public static Timestamp longToTimestamp(long time) {
        Date now = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(ISO_TIME_FORMAT);
        Timestamp timestamp = Timestamp.valueOf(sf.format(now));
        return timestamp;
    }

    /**
     * 当前时间向前或向后推移day_num天 day_num为正表示在当前天的基础上加day_num天
     * day_num为负表示在当前天的基础上减day_num天
     *
     * @param day_num int
     * @return Date
     */
    public static java.sql.Date backAndForthDate(long day_num) {
        long backAndForth = day_num * 86400000;
        return new java.sql.Date(System.currentTimeMillis() + backAndForth);
    }

    /**
     * 获得昨天的日期
     *
     * @return Date
     */
    public static java.sql.Date getYesterdaySqlDate() {
        return new java.sql.Date(System.currentTimeMillis() - 86400000);
    }

    /**
     * @return 某月某日
     * @param"yyyy-MM-dd HH:mm:ss"
     */
    public static String getMonDay(String str, boolean isHour) {
        str = str.replaceAll(" ", "-").replaceAll(":", "-");
        String strs[] = str.split("-");
        String reultDate = "";
        if (isHour) {
            reultDate = strs[1] + "月" + strs[2] + "日" + strs[3] + "点";
        } else {
            reultDate = strs[1] + "月" + strs[2] + "日";
        }
        return reultDate;
    }

    /**
     * @return 某年某月某日
     * @param"yyyy-MM-dd HH:mm:ss"
     */
    public static String getYearMonDay(String str, boolean isHour) {
        str = str.replaceAll(" ", "-").replaceAll(":", "-");
        String strs[] = str.split("-");
        String reultDate = "";
        if (isHour) {
            reultDate = strs[0] + "年" + strs[1] + "月" + strs[2] + "日" + strs[3] + "点";
        } else {
            reultDate = strs[0] + "年" + strs[1] + "月" + strs[2] + "日";
        }
        return reultDate;
    }

    /**
     * 两个日期比较
     *
     * @return int
     * @param"yyyy-MM-dd HH:mm:ss"，@param"yyyy-MM-dd HH:mm:ss"
     */
    public static int compareDate(String date1, String date2) {
        DateFormat dft = new SimpleDateFormat(ISO_TIME_FORMAT);
        Date dt1;
        Date dt2;
        int result = 3;
        try {
            dt1 = dft.parse(date1);
            dt2 = dft.parse(date2);
            result = Long.compare(dt1.getTime(), dt2.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 由Timestamp类型值转换为"yyyy-MM-dd"格式的String返回
     *
     * @param time
     * @return
     */
    public static String timestampConvertDateString(Timestamp time) {
        SimpleDateFormat sdf = new SimpleDateFormat(ISO_DATE_FORMAT);
        return sdf.format(new Date(time.getTime()));
    }

    /**
     * 由Timestamp类型值转换为"yyyy-MM-dd"格式的String返回
     *
     * @param time
     * @return
     */
    public static String timestampConvertTimeString(Timestamp time) {
        SimpleDateFormat sdf = new SimpleDateFormat(ISO_TIME_FORMAT);
        return sdf.format(new Date(time.getTime()));
    }

    public static String timestampToString(Timestamp time) {
        SimpleDateFormat sdf = new SimpleDateFormat(ISO_DATE_FORMAT);
        return sdf.format(new Date(time.getTime()));
    }

    public static String getConvertTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(ISO_TIME_FORMAT);
        return format.format(date);
    }

    public static String getConvertTimeSFM(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(ISO_DATE_FORMAT);
        return format.format(date);
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(ISO_TIME_FORMAT);
        return sdf.format(new Date());
    }

    public static String timestampToString(Timestamp time, String formate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(new Date(time.getTime()));
    }

    /**
     * 由Timestamp类型值转换为"yyyy-MM-dd HH:mm"格式的String返回
     *
     * @param time
     * @return String
     */
    public static String timestampToStringHHmm(Timestamp time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(time.getTime()));
    }

    /**
     * 由"yyyy-MM-dd"格式的字符串转换为Timestamp类型的值返回。
     *
     * @param time
     * @return Timestamp
     */
    public static Timestamp stringToTimestamp(String time) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(ISO_DATE_FORMAT);
        Date dTime = sf.parse(time);
        Long dTimeLong = dTime.getTime();
        return new Timestamp(dTimeLong);

    }

    /**
     * 由"yyyy-MM-dd"格式的字符串转换为Timestamp类型的值返回。
     * 例：传入2010-9-1 返回2010-9-1 23:59:59
     *
     * @param time
     * @return Timestamp
     */
    public static Timestamp stringToLastTimestamp(String time)
            throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(ISO_DATE_FORMAT);
        Date dTime = sf.parse(time);
        Long dTimeLong = dTime.getTime();
        dTimeLong = dTimeLong + 24 * 60 * 60 * 1000 - 1;
        return new Timestamp(dTimeLong);

    }

    /**
     * 由格式的字符串转换为Timestamp类型的值返回。
     *
     * @param time
     * @param format
     * @return Timestamp
     */
    public static Timestamp stringToTimestamp(String time, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        try {
            Date dTime = sf.parse(time);
            Long dTimeLong = dTime.getTime();
            return new Timestamp(dTimeLong);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得 yyyy-MM-dd HH:mm:ss 格式的日期字符
     *
     * @return String
     */
    public static String getFullTime() {
        SimpleDateFormat format = new SimpleDateFormat(ISO_TIME_FORMAT);
        return format.format(new Date(getCurrentTimeMillis()));
    }

    /**
     * 返回 month月 前/后 的 yyyy-MM-dd HH:mm:ss 格式的日期字符
     * 为负数，则month个月前
     * 整数，month个月后
     * 大概值，每月按30天计算
     *
     * @param month
     * @return String
     */
    public static String getFullTime(long month) {
        SimpleDateFormat format = new SimpleDateFormat(ISO_TIME_FORMAT);
        long lt = getCurrentTimeMillis() + (month * 30 * 24 * 3600 * 1000);
        return format.format(new Date(lt));
    }


    /**
     * 获得 yyyy-MM-dd HH:mm:ss 格式的日期字符
     *
     * @return String
     */
    public static String getFullTime2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date(getCurrentTimeMillis()));
    }

    /**
     * jdk1.8日期API
     *
     * @return 当前毫秒值
     */
    private static long getCurrentTimeMillis() {
        Clock clock = Clock.systemDefaultZone();
        return clock.millis();
    }

    private static long getMillis() {
        Clock clock = Clock.systemDefaultZone();
        return clock.millis();
    }

    /**
     * 根据yyyy-MM-dd HH:mm:ss 格式的日期字符获得java.util.date日期格式
     *
     * @param strDate String
     * @return Date
     */
    public static Date strToDate(String strDate) {
        int year;
        int month;
        int day;
        int hour;
        int minute;
        int second;

        if (strDate == null || strDate.trim().length() != 19) {
            return null;
        }

        year = Integer.parseInt(strDate.substring(0, 4));
        month = Integer.parseInt(strDate.substring(5, 7));
        day = Integer.parseInt(strDate.substring(8, 10));

        hour = Integer.parseInt(strDate.substring(11, 13));
        minute = Integer.parseInt(strDate.substring(14, 16));
        second = Integer.parseInt(strDate.substring(17, 19));

        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day, hour, minute, second);
        return c.getTime();
    }

    /**
     * 将java.util.Date格式的日期转化为yyyy年MM月dd日的字符
     *
     * @param date Date
     * @return String
     */
    public static String utilDate2Cn(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    /**
     * 将java.sql.Date格式的日期转化为yyyy年MM月dd日的字符
     *
     * @param date Date
     * @return String
     */
    public static String sqlDate2Cn(java.sql.Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    /**
     * 将java.sql.Timestamp格式的日期转化为yyyy年MM月dd日的字符
     *
     * @param date Timestamp
     * @return String
     */
    public static String timestampDate2Cn(Timestamp date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    /**
     * 将java.sql.Timestamp格式的日期转化为HH:mm的字符
     *
     * @param date Timestamp
     * @return String
     */
    public static String timestampDate3Cn(Timestamp date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    /**
     * 将java.sql.Timestamp格式的日期转化为yyyy-MM-dd HH:mm:ss的字符
     *
     * @param date Timestamp
     * @return String
     */
    public static String timestampDate4Cn(Timestamp date) {
        SimpleDateFormat format = new SimpleDateFormat(ISO_TIME_FORMAT);
        return format.format(date);
    }

    /**
     * 获得 yyyy-MM-dd HH:mm:ss 格式的日期
     *
     * @return Timestamp
     */
    public static Timestamp getTimestamp() {
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat(ISO_TIME_FORMAT);
            Calendar calendar = Calendar.getInstance();
            String mystrdate = myFormat.format(calendar.getTime());
            return Timestamp.valueOf(mystrdate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获得 yyyy-MM-dd HH:mm:ss 格式的日期
     *
     * @param datestr String
     * @return Timestamp
     */
    public static Timestamp getTimestamp(String datestr) {
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat(
                    ISO_TIME_FORMAT);
            String mystrdate = myFormat.format(myFormat.parse(datestr));
            return Timestamp.valueOf(mystrdate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获得 yyyy-MM-dd 格式的日期
     *
     * @param datestr String
     * @return Timestamp
     */
    public static java.sql.Date getDate2(String datestr) {
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat(ISO_DATE_FORMAT);
            String mystrdate = myFormat.format(myFormat.parse(datestr));
            return java.sql.Date.valueOf(mystrdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得 yyyyMMdd 格式的日期字符
     *
     * @return String
     */
    public static String getDate2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date(getCurrentTimeMillis()));
    }

    /**
     * 获得 yyMMddHHmm 格式的日期字符
     *
     * @return String
     */
    public static String getDate4() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmm");
        return format.format(new Date(getCurrentTimeMillis()));
    }

    /**
     * 形成yymmdd格式的日期
     *
     * @param dateStr String
     * @return String
     */
    public static String formatDate(String dateStr) {
        if (dateStr != null && dateStr.trim().length() > 0) {
            StringBuilder formatDate = new StringBuilder();
            // 年
            String newYear = dateStr.substring(2, 4);
            // 月
            String newMonth = dateStr.substring(5, 7);
            // 日
            String newDay = dateStr.substring(8, 10);
            formatDate.append(newYear).append(newMonth).append(newDay);
            return formatDate.toString();
        } else {
            return dateStr;
        }
    }

    /**
     * 获得 dd-MM-yy 格式的日期字符
     *
     * @return String
     */
    public static String getDate3() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        return format.format(new Date(getCurrentTimeMillis()));
    }

    /**
     * 获得 yyyy年MM月dd日 格式的日期字符
     *
     * @return String
     */
    public static String getCNDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(new Date(getCurrentTimeMillis()));
    }

    /**
     * 返回java.sql.Date类型的日期
     *
     * @return Date
     */
    public static java.sql.Date getSqlDate() {
        return new java.sql.Date(getCurrentTimeMillis());
    }

    /**
     * 获得明天的日期
     *
     * @return Date
     */
    public static java.sql.Date getNextSqlDate() {
        // 86400000
        return new java.sql.Date(getCurrentTimeMillis() + 86400000);
    }

    /**
     * 返回java.util.date日期格式
     *
     * @return Date
     */
    public static Date getUtilDate() {
        return strToDate(getFullTime());
    }

    /**
     * 由java.util.Date到java.sql.Date的类型转换
     *
     * @param date Date
     * @return Date
     */
    public static java.sql.Date getSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 一个日期上加天数, 默认日期格式yyyy-MM-dd
     *
     * @param dateStr
     * @param dd
     * @return String
     */
    public static String addDay(String dateStr, int dd) {
        DateFormat df = new SimpleDateFormat(ISO_DATE_FORMAT);
        return addDay(dateStr, dd, df);
    }

    /**
     * 一个日期上加天数
     *
     * @param dateStr
     * @param dd
     * @param df
     * @return String
     */
    public static String addDay(String dateStr, int dd, DateFormat df) {
        Date date = new Date();
        try {
            if (StringUtils.isNotBlank(dateStr)) {
                date = df.parse(dateStr);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DATE, dd);

        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (month.length() == 1) {
            month = "0" + month;
        }

        String day = String.valueOf(calendar.get(Calendar.DATE));
        if (day.length() == 1) {
            day = "0" + day;
        }

        return String.valueOf(calendar.get(Calendar.YEAR)) + "-"
                + month + "-" + day;
    }

    /**
     * 一个日期上加月份
     *
     * @param month 月份数
     * @return Date
     */
    public static Date dateAdd(int month) {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 一个日期上加月份, 默认日期格式yyyy-MM-dd
     *
     * @param dateStr
     * @param dd
     * @return String
     */
    public static String addMonth(String dateStr, int dd) {
        DateFormat df = new SimpleDateFormat(ISO_DATE_FORMAT);
        return addMonth(dateStr, dd, df);

    }

    /**
     * 一个日期上加月份
     *
     * @param dateStr
     * @param dd
     * @param df
     * @return String
     */
    public static String addMonth(String dateStr, int dd, DateFormat df) {
        Date date = new Date();
        try {
            date = df.parse(dateStr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.MONTH, dd);

        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (month.length() == 1) {
            month = "0" + month;
        }

        String day = String.valueOf(calendar.get(Calendar.DATE));
        if (day.length() == 1) {
            day = "0" + day;
        }

        return String.valueOf(calendar.get(Calendar.YEAR)) + "-"
                + month + "-" + day;
    }

    /**
     * 计算两个日期相差的秒数 time_1为小 time_2为大
     *
     * @param time_1 String
     * @param time_2 String
     * @return long
     */
    private static long getDiffSecond(String time_1, String time_2) {
        java.sql.Date time1_long = java.sql.Date.valueOf(time_1);
        java.sql.Date time2_long = java.sql.Date.valueOf(time_2);
        return (time2_long.getTime() - time1_long.getTime()) / 1000;
    }

    /**
     * 计算两个日期相差的天数，返回int
     *
     * @param time_1 String
     * @param time_2 String
     * @return int
     */
    public static int getDiffDay(String time_1, String time_2) {
        return (int) ((getDiffSecond(time_1, time_2)) / (24 * 3600));
    }

    /**
     * 获得月份
     *
     * @return String
     */
    public static String getCurMonth() {
        SimpleDateFormat format = new SimpleDateFormat(ISO_DATE_FORMAT);
        String back = format.format(new Date(getCurrentTimeMillis()));
        return back.substring(5, 7);
    }

    /**
     * 获得年度
     *
     * @return String
     */
    public static String getCurYear() {
        SimpleDateFormat format = new SimpleDateFormat(ISO_DATE_FORMAT);
        String back = format.format(new Date(getCurrentTimeMillis()));
        return back.substring(0, 4);
    }

    /**
     * 获得一周中的第几天
     *
     * @return String
     */
    public static String getWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
    }

    /**
     * 获得星期几
     *
     * @return String
     */
    public static String getWeekDayStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        HashMap<Integer, String> hm = new HashMap<>(7);
        hm.put(0, "星期日");
        hm.put(1, "星期一");
        hm.put(2, "星期二");
        hm.put(3, "星期三");
        hm.put(4, "星期四");
        hm.put(5, "星期五");
        hm.put(6, "星期六");
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return hm.get(day);
    }

    /**
     * 获得周几
     *
     * @return String
     */
    public static String getWeekDayStr2(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        HashMap<Integer, String> hm = new HashMap<>(7);
        hm.put(0, "周日");
        hm.put(1, "周一");
        hm.put(2, "周二");
        hm.put(3, "周三");
        hm.put(4, "周四");
        hm.put(5, "周五");
        hm.put(6, "周六");
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return hm.get(day);
    }

    /**
     * 返回两个时间相差的小时数，精确的（毫秒换算转换）
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static double getHoursOfTwoTime(Timestamp startTime, Timestamp endTime) {
        if (startTime == null || endTime == null) {
            return 0;
        }
        long lvalue = endTime.getTime() - startTime.getTime();
        double dvalue = lvalue / 1000 / 60 / 60;
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(dvalue));
    }

    /**
     * 在当前时间的基础上加分钟
     *
     * @param date   时间
     * @param minute 分钟数
     * @return Date
     */
    public static Date addDateMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 获取两个时间段的相差天数
     *
     * @param date1
     * @param date2
     * @return int
     */
    public static int differentDaysByMills(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 获得格式化后的昨天
     *
     * @return String
     */
    public static synchronized String getFormatYesterday(SimpleDateFormat format) {
        Calendar dateBegin = Calendar.getInstance();
        dateBegin.add(Calendar.DAY_OF_MONTH, -1);
        return format.format(dateBegin.getTime());
    }

    /**
     * 获得去年的当前时间
     *
     * @return String
     */
    public static Date oldYear() {
        Calendar dateBegin = Calendar.getInstance();
        dateBegin.add(Calendar.YEAR, -1);
        return dateBegin.getTime();

    }

    /**
     * 获取当前年
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }


    /**
     * 根据时间获取月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取季度
     *
     * @param date
     * @return
     */
    public static int getQuarter(Date date) {
        int month = getMonth(date);
        if (month >= 1 && month <= 3) {
            return 1;
        } else if (month >= 4 && month <= 6) {
            return 2;
        } else if (month >= 7 && month <= 9) {
            return 3;
        } else {
            return 4;
        }
    }

    /*
     * 日期字符串按指定格式转LocalDateTime
     * @attention:
     * @date: 2021/7/28 15:05
     * @param: dateStr
     * @param: format
     * @return: java.time.LocalDateTime
     */
    @NotNull
    public static LocalDateTime toLocalDateTime(String dateStr, @NotNull String format) {
        DateTimeFormatter formatter;
        if (StringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd";
        }

        if (format.length() > 11) {// 包含时间
            formatter = DateTimeFormatter.ofPattern(format);
        } else {// 只有日期
            formatter = new DateTimeFormatterBuilder()
                    .appendPattern(format + "[[HH][:mm][:ss]]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                    .toFormatter();
        }

        return LocalDateTime.parse(dateStr, formatter);
    }

    public static void main(String[] args) {
        System.out.println(getMonth(new Date()));

    }


}
