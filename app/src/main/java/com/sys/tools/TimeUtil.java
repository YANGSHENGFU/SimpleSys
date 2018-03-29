package com.sys.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by LY on 2018/3/29.
 */

public class TimeUtil {

    // 时间格式模板
    /**
     * yyyy-MM-dd
     */
    public static final String TIME_FORMAT_ONE = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String TIME_FORMAT_TWO = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH:mmZ
     */
    public static final String TIME_FORMAT_THREE = "yyyy-MM-dd HH:mmZ";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String TIME_FORMAT_FOUR = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm:ss.SSSZ
     */
    public static final String TIME_FORMAT_FIVE = "yyyy-MM-dd HH:mm:ss.SSSZ";
    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    public static final String TIME_FORMAT_SIX = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    /**
     * HH:mm:ss
     */
    public static final String TIME_FORMAT_SEVEN = "HH:mm:ss";
    /**
     * HH:mm:ss.SS
     */
    public static final String TIME_FORMAT_EIGHT = "HH:mm:ss.SS";
    /**
     * yyyy.MM.dd
     */
    public static final String TIME_FORMAT_9 = "yyyy.MM.dd";
    /**
     * MM月dd日
     */
//    public static final String TIME_FORMAT_10 = "MM月dd日";
    public static final String TIME_FORMAT_10 = "MM/dd";
    public static final String TIME_FORMAT_11 = "MM-dd HH:mm";
    public static final String TIME_FORMAT_12 = "yyMM";
    /**
     * yyyy年MM月dd日
     */
//    public static final String TIME_FORMAT_13 = "yyyy年MM月dd日";
    public static final String TIME_FORMAT_13 = "yyyy/MM/dd";
    /**
     * HH:mm
     */
    public static final String TIME_FORMAT_14 = "HH:mm";
    public static final String TIME_FORMAT_15 = "MM/dd";
    //    public static final String TIME_FORMAT_16 = "yy-MM-dd";
//    public static final String TIME_FORMAT_15 = "yyyy/MM/dd";
    public static final String TIME_FORMAT_16 = "yyyy/MM/dd";

    public static final String TIME_FORMAT_17 = "MM/dd HH:mm";
    public static final String TIME_FORMAT_18 = "yy/MM/dd HH:mm";
    public static final String TIME_FORMAT_19 = "yyyy年MM月";
    public static final String TIME_FORMAT_20 = "MM月";


    /**
     * 根据时间格式获得当前时间
     */
    public static String getCurrentTime(String formater) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(formater, Locale.SIMPLIFIED_CHINESE);
        return dateFormat.format(date);
    }
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    /**
     * 格式化时间
     */
    public static String formatTime(long time, String format) {
        simpleDateFormat.applyPattern(format);
        return simpleDateFormat.format(new Date(time));
    }

    /**
     * 判断是否是合法的时间
     */
    public static boolean isValidDate(String dateString, String format) {
        return parseTime(dateString, format) > -1;
    }

    /**
     * 日期转换
     */
    public static long parseTime(String dateString, String format) {
        if (dateString == null || dateString.length() == 0) {
            return -1;
        }
        try {
            return new SimpleDateFormat(format).parse(dateString).getTime();
        } catch (Exception e) {

        }
        return -1;
    }

    public static int getDaysBetween(String date1, String date2, String format) {
        return getDaysBetween(parseTime(date1, format), parseTime(date2, format));
    }

    public static int getDaysBetween(long date1, long date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(date1);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);

        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(date2);
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);

        return (int) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / (24 * 3600000));
    }

    /**
     * 判断两个时间是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isOneDay(long date1, long date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(date1);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);

        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(date2);
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);

        return c1.equals(c2);
    }

    /**
     * 只适用于聊天列表
     */
    public static String timeShow(long date) {
        Calendar instance = Calendar.getInstance();
        Calendar time = (Calendar) instance.clone();
        instance.setTimeInMillis(System.currentTimeMillis());
        time.setTimeInMillis(date);
        int iWeek = instance.get(Calendar.DAY_OF_YEAR);
        int tWeek = time.get(Calendar.DAY_OF_YEAR);
        int iDay = instance.get(Calendar.DAY_OF_WEEK);
        int tDay = time.get(Calendar.DAY_OF_WEEK);

        int iHour = instance.get(Calendar.HOUR_OF_DAY);
        int tHour = time.get(Calendar.HOUR_OF_DAY);


        int iYear = instance.get(Calendar.YEAR);
        int tYear = time.get(Calendar.YEAR);

        if (iYear != tYear) {
            // 超过一年
            return formatTime(date, TIME_FORMAT_18);
        }

        if (iWeek - 7 >= tWeek) {
            // 超过一周
            return formatTime(date, TIME_FORMAT_17);
        }

        //  是否一天
        if (iDay != tDay) {
            // 是否昨天
            if (iDay - tDay == 1) {
                return "昨天 " + formatTime(date, TIME_FORMAT_14);
            } else {
                switch (tDay) {
                    case Calendar.MONDAY:
                        return "星期一 " + formatTime(date, TIME_FORMAT_14);
                    case Calendar.TUESDAY:
                        return "星期二 " + formatTime(date, TIME_FORMAT_14);
                    case Calendar.WEDNESDAY:
                        return "星期三 " + formatTime(date, TIME_FORMAT_14);
                    case Calendar.THURSDAY:
                        return "星期四 " + formatTime(date, TIME_FORMAT_14);
                    case Calendar.FRIDAY:
                        return "星期五 " + formatTime(date, TIME_FORMAT_14);
                    case Calendar.SATURDAY:
                        return "星期六 " + formatTime(date, TIME_FORMAT_14);
                    default:
                        return "星期日 " + formatTime(date, TIME_FORMAT_14);
                }

            }
        }

        if (iHour != tHour) {
            return formatTime(date, TIME_FORMAT_14);
        }

        int iMin = instance.get(Calendar.MINUTE);
        int tMin = time.get(Calendar.MINUTE);
        if (iMin == tMin) {
            return "刚刚";
        } else {
            return formatTime(date, TIME_FORMAT_14);
        }
    }
    static Calendar instance = Calendar.getInstance();
    static Calendar time = Calendar.getInstance();
    /**
     * 只适用于最近会话列表
     * 或 呼叫记录
     *
     * @param date
     * @return
     */
    public static String formatTime(long date) {

//        Calendar instance = Calendar.getInstance();
//        Calendar time = (Calendar) instance.clone();
        instance.setTimeInMillis(System.currentTimeMillis());
        time.setTimeInMillis(date);

        int iYear = instance.get(Calendar.YEAR);
        int tYear = time.get(Calendar.YEAR);

        if (iYear != tYear) {
            // 超过一年
            return formatTime(date, TIME_FORMAT_16);
        }

        int iWeek = instance.get(Calendar.DAY_OF_YEAR);
        int tWeek = time.get(Calendar.DAY_OF_YEAR);

        if (iWeek - 7 >= tWeek) {
            // 超过一周
            return formatTime(date, TIME_FORMAT_15);
        }

        int iDay = instance.get(Calendar.DAY_OF_WEEK);
        int tDay = time.get(Calendar.DAY_OF_WEEK);

        //  是否一天
        if (iDay != tDay) {
            // 是否昨天
            if (iDay - tDay == 1 || iDay - tDay == -6) {
                //return "昨天 "  +  formatTime(date, TIME_FORMAT_14);
                return "昨天";
            } else {
                switch (tDay) {
                    case Calendar.MONDAY:
                        return "星期一";
                    case Calendar.TUESDAY:
                        return "星期二";
                    case Calendar.WEDNESDAY:
                        return "星期三";
                    case Calendar.THURSDAY:
                        return "星期四";
                    case Calendar.FRIDAY:
                        return "星期五";
                    case Calendar.SATURDAY:
                        return "星期六";
                    default:
                        return "星期日";
                }
            }
        }

        int iHour = instance.get(Calendar.HOUR_OF_DAY);
        int tHour = time.get(Calendar.HOUR_OF_DAY);

        if (iHour != tHour) {
            return formatTime(date, TIME_FORMAT_14);
        }

        int iMin = instance.get(Calendar.MINUTE);
        int tMin = time.get(Calendar.MINUTE);
        if (iMin == tMin) {
            return "刚刚";
        } else {
//            return String.format("%d 分钟前", Math.abs(iMin-tMin));
            return formatTime(date, TIME_FORMAT_14);
        }

    }

    /**
     * @param date
     * @return 收藏使用
     */
    public static String formatFavoriteTime(long date) {
        Calendar instance = Calendar.getInstance();
        Calendar time = (Calendar) instance.clone();
        time.setTimeInMillis(date);

        int iYear = instance.get(Calendar.YEAR);
        int tYear = time.get(Calendar.YEAR);

        if (iYear != tYear) {
            // 超过一年
            return formatTime(date, TIME_FORMAT_16);
        }
        int iWeek = instance.get(Calendar.DAY_OF_YEAR);
        int tWeek = time.get(Calendar.DAY_OF_YEAR);

        if (iWeek - 7 >= tWeek) {
            // 超过一周
            return formatTime(date, TIME_FORMAT_15);
        }

        int iDay = instance.get(Calendar.DAY_OF_WEEK);
        int tDay = time.get(Calendar.DAY_OF_WEEK);

        //  是否一天
        if (iDay != tDay) {
            // 是否昨天
            if (iDay - tDay == 1) {
                return "昨天";
            } else {
                return formatTime(date, TIME_FORMAT_15);
            }
        } else {
            return "今天";
        }
    }

    /**
     * 判断是否同一年
     *
     * @param time
     * @return
     */
    public static boolean isOneYear(long time) {
        Calendar nowCalendar = Calendar.getInstance();
        Calendar anotherCalendar = Calendar.getInstance();
        anotherCalendar.setTimeInMillis(time);
        return nowCalendar.get(Calendar.YEAR) == anotherCalendar.get(Calendar.YEAR);
    }

    /**
     * 判断月份是否相同
     *
     * @param time
     * @return
     */
    public static boolean isOneMonth(long time) {
        Calendar nowCalendar = Calendar.getInstance();
        Calendar anotherCalendar = Calendar.getInstance();
        anotherCalendar.setTimeInMillis(time);
        return nowCalendar.get(Calendar.MONTH) == anotherCalendar.get(Calendar.MONTH);
    }

    /**
     * 获取时间戳 ，格式2010-1-4 16:21:4，如果是一位数的话，那么前面不加0
     */
    public static String getTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return (year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
    }

    /**
     * Unix时间戳转换成日期
     */
    public static String TimeStamp2Date(String timestampString, String formater) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formater, Locale.SIMPLIFIED_CHINESE).format(new Date(timestamp));
        return date;
    }

    public static long getTodayTimeMillis() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static String getTimeByLong4Msg(long tLong) {
        String strDate = "";
        tLong = tLong * 1000;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(tLong);
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd");
        // strDate = cal.getTime().toLocaleString();
        strDate = sdf.format(cal.getTime());
        return strDate;
    }

    public static String getTimeByLong(long tLong) {
        String strDate = "";
        tLong = tLong * 1000;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(tLong);
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        // strDate = cal.getTime().toLocaleString();
        strDate = sdf.format(cal.getTime());
        return strDate;
    }

    public static String getTimeByLong(long tLong, String format) {
        String strDate = "";
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(tLong);
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        strDate = sdf.format(cal.getTime());
        return strDate;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(long sdate) {
        boolean isTod = false;
        SimpleDateFormat myFormatter = new SimpleDateFormat(TIME_FORMAT_ONE);
        Date today = new Date(System.currentTimeMillis());
        String todayString = myFormatter.format(today);
        Date myDate = new Date(sdate);
        String myDateString = myFormatter.format(myDate);
        if (todayString.equals(myDateString)) {
            isTod = true;
        } else {
            isTod = false;
        }
        return isTod;
    }

    private static long lastLocalDate = 0;
    private static long lastLocalTimestamp = 0;
    private static boolean USE_NET_TIME = true;

    public static final void setUseNetTime(boolean use) {
        USE_NET_TIME = use;
    }

    /**
     * 服务器时间戳转换成本地时间
     *
     * @param timestamp
     * @return
     */
    public static long currentTimeMillis(long timestamp) {
        long ctime = System.currentTimeMillis();
        if (timestamp < 1000) return ctime;
        if (timestamp < Integer.MAX_VALUE) {
            timestamp *= 1000;
        }
        if (USE_NET_TIME) return timestamp;
//		long diff = ctime - timestamp;
//		// 允许本地的与服务器误差，待定
//		if(Math.abs(diff) > DEF_DIFF_TIME){
//			// 服务器时间与本地时间超过允许的误差值，
//			long diffDate = ctime - lastLocalDate;// 本地时间差值
//			long diffTime = timestamp - lastLocalTimestamp;	// 服务器时间差值
//			ctime = lastLocalDate + diffTime;
//		}
//		lastLocalDate = ctime;
//		lastLocalTimestamp = timestamp;
        return ctime;
    }

    public static long getTimeByGMT(String timeGMT) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date date = sdf.parse(timeGMT);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 只适用于聊天文件
     *
     * @param time
     * @return
     */
    public static String formatChatFileTime(long time) {
        if (!isOneYear(time)) {
            return formatTime(time, TIME_FORMAT_19);
        } else {
            if (!isOneMonth(time)) {
                return formatTime(time, TIME_FORMAT_20);
            } else {
                return "本月";
            }
        }
    }

    /**
     * 判断是否超过多少小时
     *
     *
     *
     * @param date1
     *
     * @param date2
     *
     * @return boolean
     *
     * @throws Exception
     */

    public static boolean judgmentDate(long date1, long date2,
                                       long delayTime){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
            String date_1 = sdf.format(new Date(date1));
            String date_2 = sdf.format(new Date(date2));
            Date start = sdf.parse(date_1);
            Date end = sdf.parse(date_2);
            long cha = end.getTime() - start.getTime();
            if (cha < 0) {
                return false;
            }
            double result = cha * 1.0 / (1000 * 60 * 60);
            if (result >= delayTime) {
                return true;
            } else {
                return false;

            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return false;
    }


    public static String circleShow(long date) {
        Calendar instance = Calendar.getInstance();
        Calendar time = (Calendar) instance.clone();
        time.setTimeInMillis(date);
        int iWeek = instance.get(Calendar.DAY_OF_YEAR);
        int tWeek = time.get(Calendar.DAY_OF_YEAR);
        int iDay = instance.get(Calendar.DAY_OF_WEEK);
        int tDay = time.get(Calendar.DAY_OF_WEEK);

        int iHour = instance.get(Calendar.HOUR_OF_DAY);
        int tHour = time.get(Calendar.HOUR_OF_DAY);


        if (iWeek - 7 >= tWeek) {
            // 超过一周
            return formatTime(date, TIME_FORMAT_11);
        }

        //  是否一天
        if (iDay != tDay) {
            // 是否昨天
            if (iDay - tDay == 1) {
                return "昨天 " + formatTime(date, TIME_FORMAT_14);
            } else {
                switch (tDay) {
                    case Calendar.MONDAY:
                        return "星期一 " + formatTime(date, TIME_FORMAT_14);
                    case Calendar.TUESDAY:
                        return "星期二 " + formatTime(date, TIME_FORMAT_14);
                    case Calendar.WEDNESDAY:
                        return "星期三 " + formatTime(date, TIME_FORMAT_14);
                    case Calendar.THURSDAY:
                        return "星期四 " + formatTime(date, TIME_FORMAT_14);
                    case Calendar.FRIDAY:
                        return "星期五 " + formatTime(date, TIME_FORMAT_14);
                    case Calendar.SATURDAY:
                        return "星期六 " + formatTime(date, TIME_FORMAT_14);
                    default:
                        return "星期日 " + formatTime(date, TIME_FORMAT_14);
                }

            }
        }

        if (iHour != tHour) {
            return "今日 "+formatTime(date, TIME_FORMAT_14);
        }

        int iMin = instance.get(Calendar.MINUTE);
        int tMin = time.get(Calendar.MINUTE);
        if (iMin == tMin) {
//            return "刚刚"
            return "今日 "+formatTime(date, TIME_FORMAT_14);
        } else {
            return "今日 "+formatTime(date, TIME_FORMAT_14);
        }
    }

}
