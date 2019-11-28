package com.project.zhongrenweigong.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
public class TimeUtil {

    public static int getmm(int time) {
        int mm = time / 60;
        if (time % 60 > 0) {
            mm += 1;
        }
        return mm;
    }

    public static String showTimeCount(int time) {

//        m = mm 整除 60  得到分
//                s = mm 取余 60  得到秒
        int s = time / 60;
        int mm = time % 60;
        String m = "0";
        if (mm == 0) {
            m = "00";
        }
        if (mm == 1 || mm == 2 || mm == 3 || mm == 4 || mm == 5 || mm == 6 || mm == 7 || mm == 8 || mm == 9) {
            m = "0" + mm;
        }
        if (mm > 9) {
            m = mm + "";
        }
        if (s == 0) {
            return "00" + ":" + m;
        } else if (s == 1 || s == 2 || s == 3 || s == 4 || s == 5 || s == 6 || s == 7 || s == 8 || s == 9) {
            return "0" + s + ":" + m;
        } else {
            return s + ":" + m;
        }

    }

    public static String generateTimeZh(long millis) {
        int totalSeconds = (int) (millis / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.CHINA, "%02d小时%02d分钟%02d秒", hours, minutes,
                    seconds);
        } else {
            return String.format(Locale.CHINA, "%02d分钟%02d秒", minutes, seconds);
        }
    }

    public static String getTimeString(long millis) {
        int totalSeconds = (int) (millis / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.CHINA, "%02d小时%02d分钟%02d秒", hours, minutes,
                    seconds);
        } else if (minutes > 0) {
            return String.format(Locale.CHINA, "%02d分钟%02d秒", minutes, seconds);
        } else {
            return String.format(Locale.CHINA, "%02d秒", seconds);
        }
    }

    public static String generateTime(long millis) {
        int totalSeconds = (int) (millis / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.CHINA, "%02d:%02d:%02d", hours, minutes,
                    seconds);
        } else {
            return String.format(Locale.CHINA, "%02d:%02d", minutes, seconds);
        }
    }

    public static String generateTimeNoHour(long duration) {
        String time = "" ;
        long minute = duration / 60000 ;
        long seconds = duration % 60000 ;
        long second = Math.round((float)seconds/1000) ;
        if( minute < 10 ){
            time += "0" ;
        }
        time += minute+":" ;
        if( second < 10 ){
            time += "0" ;
        }
        time += second ;
        return time ;
    }

    public static String groupBookingCountDown(long millis) {
        int totalSeconds = (int) (millis / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return String.format(Locale.CHINA, "%02d:%02d:%02d", hours, minutes,
                seconds);
    }


    public static class TimeBean {
        public int hour;
        public int minite;
        public int second;

        public TimeBean(int hour, int minite, int second) {
            this.hour = hour;
            this.minite = minite;
            this.second = second;
        }
    }

    public static TimeBean cal(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
//        return d + ":" + (s < 10 ? "0" + s : s);
        return new TimeBean(h, d, s);
    }

    // 基础课程时间
    public static String formatActionTimes(int time) {
        TimeBean tb = TimeUtil.cal(time);
        return tb.minite + ":" + (tb.second < 10 ? "0" + tb.second : tb.second);
    }

    // 名师,冥想课程时间
    public static String formatLessonTimes(int totalSeconds) {
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60);
        return String.format(Locale.CHINA, "%02d分钟%02d秒", minutes, seconds);
    }

    public static String getNow() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        long now = System.currentTimeMillis();
        calendar.setTimeInMillis(now);
        Date time = calendar.getTime();
        return formatter.format(calendar.getTime());
    }

    public static long getNowInMillis() {
        return Calendar.getInstance(Locale.CHINA).getTimeInMillis();
    }

    public static String getHStimes(long m) {
        long l = m * 1000;
        Date time = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");//24小时hh:mm12小时
        return format.format(time);
    }

    public static String getHhmmss() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");//24小时hh:mm12小时
        return format.format(new Date());
    }

    public static String getHStimesm(long m) {
        Date time = new Date(m);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(time);
    }

    public static String getYMDtimes(long m) {
        long l = m * 1000;
        Date time = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(time);
    }

    public static String getYMDtimesD(long m) {
        long l = m * 1000;
        Date time = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(time);
    }

    public static String getYMDStringtimes(long m) {
        long l = m * 1000;
        Date time = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(time);
    }

    public static String getYMDtimesm(long m) {
        long l = m * 1000;
        Date time = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(time);
    }

    public static String getDay(Long m) {
        long l = m * 1000;
        Date time = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }

    public static String getMDDay(Long m) {
        long l = m * 1000;
        Date time = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
        return format.format(time);
    }

    public static String getMDDay1(Long m) {
        long l = m * 1000;
        Date time = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        return format.format(time);
    }

    public static String getYMDHMStringtimes(long m) {
        long l = m * 1000;
        Date time = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return format.format(time);
    }

    public static String getMDHMStringtimes(long m) {
        long l = m * 1000;
        Date time = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("MM.dd HH:mm");
        return format.format(time);
    }

    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }

    public static long getTodayZero() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    private static Long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }

    public static boolean isToday(long time) {
        long todayStart = getTodayZero();
        long todayEnd = getEndTime();
        if (todayStart <= time && time <= todayEnd) {
            return true;
        }
        return false;
    }

    public static long getYesterdayZero() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static String format(long timestamp, String template) {
        Date date = new Date(timestamp);
        SimpleDateFormat formater = new SimpleDateFormat(template, Locale.getDefault());
        return formater.format(date);
    }

    public static boolean isToday(String dateString) {
        //获取当前日期
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        String nowDate = sf.format(date);
        if (nowDate.equals(dateString)) {
            return true;
        }
        return false;
    }

    public static long getTomorrowZeroUnix() {
        Calendar lastCal = Calendar.getInstance();
        lastCal.add(Calendar.DAY_OF_MONTH, 1);
        lastCal.set(Calendar.HOUR_OF_DAY, 0);
        lastCal.set(Calendar.MINUTE, 0);
        lastCal.set(Calendar.SECOND, 0);
        lastCal.set(Calendar.MILLISECOND, 0);
        return lastCal.getTimeInMillis() / 1000;
    }

    public static String getWeekOfDate(long date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Date dt = new Date(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    public static int daysBetween(long time1) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long time2 = calendar.getTimeInMillis();
        double between_days = Math.ceil((time2 - time1) * 1.0f / (1000 * 3600 * 24));
        return (int) between_days;
    }

    /**
     * @param ts 获取某一时间是上午还是下午
     * @return
     */
    public static String AM_PM(long ts) {
        Date date = new Date(ts * 1000);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int type = calendar.get(GregorianCalendar.AM_PM);
        return type == 0 ? "上午" : "下午";

    }

    /**
     * <p>
     * time1 - time2 相隔几天
     * </p>
     * <p>
     * time1 > time2
     * </p>
     *
     * @param time1 milliseconds
     * @param time2 milliseconds
     * @return
     */
    public static long getDayDiff(Long time1, Long time2) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time2);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long last = cal.getTimeInMillis();
        cal.setTimeInMillis(time1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long now = cal.getTimeInMillis();
        return (now - last) / (1000 * 3600 * 24);
    }

    //毫秒换成00:00:00
    public static String getCountTimeByLong(long finishTime) {
        int totalTime = (int) (finishTime / 1000);//秒
        int hour = 0, minute = 0, second = 0;

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

        if (hour < 10) {
            sb.append("0").append(hour).append("小时");
        } else {
            sb.append(hour).append("小时");
        }
        if (minute < 10) {
            sb.append("0").append(minute).append("分");
        } else {
            sb.append(minute).append("分");
        }
        if (second < 10) {
            sb.append("0").append(second).append("秒");
        } else {
            sb.append(second).append("秒");
        }
        return sb.toString();

    }

    //毫秒换成00:00:00
    public static String getTimeByLong(double finishTime) {
        int totalTime = (int) (finishTime / 1000);//秒
        int hour = 0, minute = 0, second = 0;

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

        if (hour < 10) {
            sb.append("0").append(hour).append(":");
        } else {
            sb.append(hour).append(":");
        }
        if (minute < 10) {
            sb.append("0").append(minute).append(":");
        } else {
            sb.append(minute).append(":");
        }
        if (second < 10) {
            sb.append("0").append(second);
        } else {
            sb.append(second);
        }
        return sb.toString();

    }

    /**
     * 毫秒转化时分秒毫秒
     *
     * @param ms 毫秒数
     * @return
     * @author aoliu
     * @since JDK 1.6
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;

        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append("<h4><font color='#04cecc'>" +
                    day + "</font></h4>" + "天");
        }
        if (hour > 0) {
            sb.append("<h4><font color='#04cecc'>" +
                    hour + "</font></h4>" + "小时");
        }
        return sb.toString();
    }

    public static String formatTimeDay(Long ms) {
        long l = ms * 1000;
        Date time = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return format.format(time);
    }


}