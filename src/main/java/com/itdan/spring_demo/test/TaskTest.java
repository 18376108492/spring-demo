package com.itdan.spring_demo.test;

import org.apache.commons.lang.time.DateUtils;
import org.omg.CORBA.DATA_CONVERSION;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 定时条件测试
 */
public class TaskTest {

    public static void main(String[] args) throws ParseException {

//        Calendar calendar = Calendar.getInstance();
//      //   String DAY_OF_14="yyyy-MM-14 23:59:59";
//      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//      // SimpleDateFormat sdf = new SimpleDateFormat(DAY_OF_14);
//
//       // calendar.add(Calendar.MONTH, 1);
//       // calendar.set(Calendar.DAY_OF_MONTH, 0);
////        calendar.add(Calendar.MONTH, 0);
////        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        int actualMinimum = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
//        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        //设置本月起始日的年月日时分秒格式
//        //calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),actualMinimum,00,00,00);
//        Date calendarTime = calendar.getTime();
//        //设置本月结束日的年月日时分秒格式
//        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),actualMaximum,23,59,59);
//        String format = sdf.format(calendar.getTime());
//        Date parse = sdf.parse(format);
//        System.out.println("Calendar:"+calendarTime);
//        System.out.println("format:"+format);
//
//        System.out.println(new Date().getDate());
//
//        System.out.println("----------------------季度测试---------------------------");
//       // System.out.println("获取当前季度:"+ getQuarter());
//        //获取本季度第一天是时间和最后一天时间
//        Date[] dates = getCurrQuarter(1);
//        System.out.println("本季度任务开始时间:"+sdf.format(dates[0]));
//        System.out.println("本季度任务结束时间:"+sdf.format(dates[1]));
//
//        System.out.println(new Date().getMonth());


//        List<Date> nextWeekDateList = getNextWeekDateList();
//        nextWeekDateList.forEach(i ->{
//            System.out.println(dateToString(i,"yyyy-MM-dd HH:mm:ss"));
//        });

        // System.out.println("------------------------");
//         * 1.每班一次
//                * 2.每班二次
//                * 3.每天一次
//                * 4.每天二次
//                * 5.每两天一次
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j <3 ; j++) {
//
//            }
//        }

        // TimeResp timeResp = getTime1("每班一次");
        String s3 = "2020-01-25";
        String s1 = "2018-02-16";
        String s4 = "2021-02-12";
        String s2 = "2019-02-05";
        String s5 = "2022-02-01";
        String s6 = "2007-02-18";
        String s7 = "2008-02-07";
        String s8 = "2009-01-26";
        String s9 = "2010-02-14";
        String s10 = "2011-02-03";
        String s11 = "2012-01-23";
        String s12 = "2013-02-10";
        String s13 = "2014-01-31";


        getNextSpringFestival(s1);
        getNextSpringFestival(s2);
        getNextSpringFestival(s3);
        getNextSpringFestival(s4);
        getNextSpringFestival(s5);
        getNextSpringFestival(s6);
        getNextSpringFestival(s7);
        getNextSpringFestival(s8);
        getNextSpringFestival(s9);
        getNextSpringFestival(s10);
        getNextSpringFestival(s11);
        getNextSpringFestival(s12);
        getNextSpringFestival(s13);

    }

    /**
     * 生成定时任务时间为：本月一号的凌晨零点和本月十五号的凌晨零点
     *
     * @param s
     * @return
     */
    public TimeResp getTime(String s) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        // SimpleDateFormat simpleDateFormat = null;
        TimeResp timeResp = new TimeResp();

        if (s.equals("每月一次")) {
            //每月一次，将本月一号作为任务开始时间，本月的最后一天作为任务结束时间
            Date taskTime = getTaskTime(calendar);
            timeResp.setTaskTime(taskTime);
            Date finishDate = getFinishDate(calendar);
            timeResp.setFinishTime(finishDate);
            System.out.println(taskTime.toString());
        } else if (s.equals("每月两次")) {
            //每月两次，分别将本月一号和十五号作为任务开始日期，分别把本月十四号和月底的最后一天作为结束时间
            //因为每月需要生产两次任务,生成任务创建时间之前，需要判断当前时间为本月一号还是十五号，再根据情况来生成任务开始和结束时间
            //判断当前时间是否为本月一号
            if (new Date().getDate() == 1) {
                Date taskTime = getTaskTime(calendar);
                timeResp.setTaskTime(taskTime);
                //本月14号23点59分59秒的模板
                String DAY_FOR_14 = "yyyy-MM-14 23:59:59";
                Date finishDate = getFinishDate(calendar);
                timeResp.setFinishTime(parseTempleDate(finishDate, DAY_FOR_14));
            } else {
                //本月15号00点00分00秒模板
                String DAY_FOR_15 = "yyyy-MM-15 00:00:00";
                Date taskTime = getTaskTime(calendar);
                timeResp.setTaskTime(parseTempleDate(taskTime, DAY_FOR_15));
                timeResp.setFinishTime(getFinishDate(calendar));
            }
        } else if (s.equals("一个季度一次")) {
            //一个季度一次，任务开始时间本季度第一天，任务结束为本季度的最后一天
            Date[] currQuarter = getCurrQuarter(getQuarter());
            if (currQuarter.length == 0) {
                throw new RuntimeException("出错啦");
            }
            timeResp.setTaskTime(currQuarter[0]);
            timeResp.setFinishTime(currQuarter[1]);
        } else if (s.equals("半年一次")) {
            //半年一次，认为开始时间分别为1月1日和6月1日，任务结束时间为五月底和年底的最后一天
            if (new Date().getMonth() <= 4) {
                timeResp.setTaskTime(parseTempleDate(calendar.getTime(), "yyyy-01-01 00:00:00"));
                timeResp.setFinishTime(parseTempleDate(calendar.getTime(), "yyyy-05-31 23:59:59"));
            } else {
                timeResp.setTaskTime(parseTempleDate(calendar.getTime(), "yyyy-06-01 00:00:00"));
                timeResp.setFinishTime(parseTempleDate(calendar.getTime(), "yyyy-012-31 23:59:59"));
            }
        } else if (s.equals("国庆前一次")) {

        } else if (s.equals("春节前一次")) {

        }
        return timeResp;
    }

    /**
     * 1.每班一次
     * 2.每班二次
     * 3.每天一次
     * 4.每天二次
     * 5.每两天一次
     * <p>
     * 每周四生成下周的任务
     *
     * @return
     */
    public static TimeResp getTime1(String s) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        // SimpleDateFormat simpleDateFormat = null;
        TimeResp timeResp = new TimeResp();
        //获取下一周周一到周日时间集合
        List<Date> nextWeekDateList = getNextWeekDateList();
        int count = 0;
        if (s.equals("每班一次")) {
            for (int i = 0; i < 7; i++) {
                Date date = nextWeekDateList.get(i);//获取星期数
                System.out.println("前一天:" + dateToString(date, "yyyy-MM-dd HH:mm:ss"));
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                Date date1 = calendar.getTime();//获取星期数
                System.out.println("后一天:" + dateToString(date1, "yyyy-MM-dd HH:mm:ss"));
                //String s1 = dateToString(date, "yyyy-MM-dd");
                for (int j = 0; j < 3; j++) {
                    if (j == 0) {
                        timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 02:00:00"));
                        timeResp.setFinishTime(parseTempleDate(date, "yyyy-MM-dd 10:00:00"));
                    } else if (j == 1) {
                        timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 10:00:00"));
                        timeResp.setFinishTime(parseTempleDate(date, "yyyy-MM-dd 18:00:00"));
                    } else if (j == 2) {
                        timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 18:00:00"));
                        //Date转Integer
                        timeResp.setFinishTime(parseTempleDate(date1, "yyyy-MM-dd 02:00:00"));
                    }
                    //添加任务
                    // saveCheckTask();
                    count += 1;
                    System.out.println("第" + count + "次:" + dateToString(timeResp.getTaskTime(), "yyyy-MM-dd HH:mm:ss"));
                    System.out.println("第" + count + "次:" + dateToString(timeResp.getFinishTime(), "yyyy-MM-dd HH:mm:ss"));
                }
            }
        } else if (s.equals("每班两次")) {
            for (int i = 0; i < 7; i++) {
                Date date = nextWeekDateList.get(i);//获取星期数
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                Date date1 = calendar.getTime();//获取星期数
                //String s1 = dateToString(date, "yyyy-MM-dd");
                for (int j = 0; j < 6; j++) {
                    if (j == 0) {
                        timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 02:00:00"));
                        timeResp.setFinishTime(parseTempleDate(date, "yyyy-MM-dd 06:00:00"));
                    } else if (j == 1) {
                        timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 06:00:00"));
                        timeResp.setFinishTime(parseTempleDate(date, "yyyy-MM-dd 10:00:00"));
                    } else if (j == 2) {
                        timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 10:00:00"));
                        timeResp.setFinishTime(parseTempleDate(date, "yyyy-MM-dd 14:00:00"));
                    } else if (j == 3) {
                        timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 14:00:00"));
                        timeResp.setFinishTime(parseTempleDate(date, "yyyy-MM-dd 18:00:00"));
                    } else if (j == 4) {
                        timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 18:00:00"));
                        timeResp.setFinishTime(parseTempleDate(date, "yyyy-MM-dd 22:00:00"));
                    } else if (j == 5) {
                        timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 22:00:00"));
                        timeResp.setFinishTime(parseTempleDate(date1, "yyyy-MM-dd 02:00:00"));
                    }
                    //添加任务
                    // saveCheckTask();
                    count += 1;
                    System.out.println("第" + count + "次:" + dateToString(timeResp.getTaskTime(), "yyyy-MM-dd HH:mm:ss"));
                    System.out.println("第" + count + "次:" + dateToString(timeResp.getFinishTime(), "yyyy-MM-dd HH:mm:ss"));
                }
            }
        } else if (s.equals("每天一次")) {
            for (int i = 0; i < 7; i++) {
                Date date = nextWeekDateList.get(i);//获取星期数
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                Date date1 = calendar.getTime();//获取星期数
                timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 02:00:00"));
                timeResp.setFinishTime(parseTempleDate(date1, "yyyy-MM-dd 02:00:00"));
                //添加数据
                count += 1;
                System.out.println("第" + count + "次:" + dateToString(timeResp.getTaskTime(), "yyyy-MM-dd HH:mm:ss"));
                System.out.println("第" + count + "次:" + dateToString(timeResp.getFinishTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        } else if (s.equals("每天两次")) {
            for (int i = 0; i < 7; i++) {
                Date date = nextWeekDateList.get(i);//获取星期数
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                Date date1 = calendar.getTime();//获取星期数
                for (int j = 0; j < 2; j++) {
                    if (j == 0) {
                        timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 02:00:00"));
                        timeResp.setFinishTime(parseTempleDate(date, "yyyy-MM-dd 12:00:00"));
                    } else if (j == 1) {
                        timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 12:00:00"));
                        timeResp.setFinishTime(parseTempleDate(date1, "yyyy-MM-dd 02:00:00"));
                    }
                    count += 1;
                    System.out.println("第" + count + "次:" + dateToString(timeResp.getTaskTime(), "yyyy-MM-dd HH:mm:ss"));
                    System.out.println("第" + count + "次:" + dateToString(timeResp.getFinishTime(), "yyyy-MM-dd HH:mm:ss"));
                }
            }

        } else if (s.equals("每两天一次")) {
            for (int i = 0; i < 3; i++) {
                Date date = null;
                if (i == 0) {
                    date = nextWeekDateList.get(i);//获取星期数
                } else {
                    date = nextWeekDateList.get(i * 2);//获取星期数
                }
                Date date1 = nextWeekDateList.get((i + 1) * 2);//获取星期数
                timeResp.setTaskTime(parseTempleDate(date, "yyyy-MM-dd 02:00:00"));
                if (i == 2) {
                    //获取下周一的时间
                    Date nextWeekMonday = getNextWeekMonday(date);
                    timeResp.setFinishTime(parseTempleDate(nextWeekMonday, "yyyy-MM-dd 02:00:00"));
                } else {
                    timeResp.setFinishTime(parseTempleDate(date1, "yyyy-MM-dd 02:00:00"));
                }
                //添加数据
                count += 1;
                System.out.println("第" + count + "次:" + dateToString(timeResp.getTaskTime(), "yyyy-MM-dd HH:mm:ss"));
                System.out.println("第" + count + "次:" + dateToString(timeResp.getFinishTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        }
        return timeResp;
    }

    /**
     * 获取当前日期的下周一到下周日的所有日期集合
     *
     * @return
     */
    public static List<Date> getNextWeekDateList() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(new Date());

        cal2.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal1.get(Calendar.DAY_OF_WEEK);

        if (dayWeek == 1) {
            cal1.add(Calendar.DAY_OF_MONTH, 1);
            cal2.add(Calendar.DAY_OF_MONTH, 7);
        } else {
            cal1.add(Calendar.DAY_OF_MONTH, 1 - dayWeek + 8);
            cal2.add(Calendar.DAY_OF_MONTH, 1 - dayWeek + 14);
        }
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(cal1.getTime());

        List<Date> dateList = new ArrayList();
        //别忘了，把起始日期加上
        dateList.add(cal1.getTime());
        // 此日期是否在指定日期之后
        while (cal2.getTime().after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(cStart.getTime());
        }
        return dateList;
    }

    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    public static Date stringToDate(String s, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(s);
    }

    /**
     * 获取本月的起始日期
     *
     * @param calendar
     * @return
     */
    private Date getTaskTime(Calendar calendar) {
        if (calendar == null) {
            throw new RuntimeException("参数为空");
        }
        int actualMinimum = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置本月起始日的年月日时分秒格式(设置为凌晨零点)
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), actualMinimum, 00, 00, 00);
        return calendar.getTime();
    }

    /**
     * 获取本月的结束日期
     *
     * @param calendar
     * @return
     */
    private Date getFinishDate(Calendar calendar) {
        if (calendar == null) {
            throw new RuntimeException("参数为空");
        }
        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置本月结束日的年月日时分秒格式
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), actualMaximum, 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * 获取当前季度
     */
    public static int getQuarter() {
        Calendar c = Calendar.getInstance();
        int month = c.get(c.MONTH) + 1;
        int quarter = 0;
        if (month >= 1 && month <= 3) {
            quarter = 1;
        } else if (month >= 4 && month <= 6) {
            quarter = 2;
        } else if (month >= 7 && month <= 9) {
            quarter = 3;
        } else {
            quarter = 4;
        }
        return quarter;
    }


    /**
     * 获取某季度的第一天和最后一天
     *
     * @param
     */
    public static Date[] getCurrQuarter(int num) throws ParseException {
        Date[] dates = new Date[2];
        String str = "";
        // 设置本年的季
        Calendar quarterCalendar = null;
        //季度第一天模板
        //季度最后一天模板
        String QUARTER_Finish_DAY = "yyyy-MM-dd 23:59:59";

        switch (num) {
            case 1: // 本年到现在经过了一个季度，在加上前4个季度
                quarterCalendar = Calendar.getInstance();
                quarterCalendar.set(Calendar.MONTH, 3);
                quarterCalendar.set(Calendar.DATE, 1);
                quarterCalendar.add(Calendar.DATE, -1);
                //获取本季度最后一天
                System.out.println(quarterCalendar.getTime().toString());
                dates[0] = parseTempleDate(quarterCalendar.getTime(), "yyyy-01-01 00:00:00");
                dates[1] = parseTempleDate(quarterCalendar.getTime(), QUARTER_Finish_DAY);
                break;
            case 2: // 本年到现在经过了二个季度，在加上前三个季度
                quarterCalendar = Calendar.getInstance();
                quarterCalendar.set(Calendar.MONTH, 6);
                quarterCalendar.set(Calendar.DATE, 1);
                quarterCalendar.add(Calendar.DATE, -1);
                //季度第一天模板
                dates[0] = parseTempleDate(quarterCalendar.getTime(), "yyyy-04-01 00:00:00");
                dates[1] = parseTempleDate(quarterCalendar.getTime(), QUARTER_Finish_DAY);
                break;
            case 3:// 本年到现在经过了三个季度，在加上前二个季度
                quarterCalendar = Calendar.getInstance();
                quarterCalendar.set(Calendar.MONTH, 9);
                quarterCalendar.set(Calendar.DATE, 1);
                quarterCalendar.add(Calendar.DATE, -1);
                //季度第一天模板
                System.out.println();
                dates[0] = parseTempleDate(quarterCalendar.getTime(), "yyyy-07-01 00:00:00");
                dates[1] = parseTempleDate(quarterCalendar.getTime(), QUARTER_Finish_DAY);
                break;
            case 4:// 本年到现在经过了四个季度，在加上前一个季度
                quarterCalendar = Calendar.getInstance();
                String MONTH_FOR_10 = "yyyy-10-01 00:00:00";
                String MONTH_FOR_12 = "yyyy-12-21 23:59:59";
                dates[0] = parseTempleDate(quarterCalendar.getTime(), MONTH_FOR_10);
                dates[1] = parseTempleDate(quarterCalendar.getTime(), MONTH_FOR_12);
                break;
        }
        return dates;
    }


    /**
     * 用途：以指定的格式格式化日期字符串
     *
     * @param pattern     字符串的格式
     * @param currentDate 被格式化日期
     * @return Date 已格式化的日期字符串
     * @throws NullPointerException 如果参数为空
     */
    public static Date parseTempleDate(Date currentDate, String pattern) throws ParseException {
        if (currentDate == null || "".equals(pattern) || pattern == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String format = sdf.format(currentDate);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(format);
    }

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    static class TimeResp {
        private Date taskTime;
        private Date finishTime;

        public Date getTaskTime() {
            return taskTime;
        }

        public void setTaskTime(Date taskTime) {
            this.taskTime = taskTime;
        }

        public Date getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(Date finishTime) {
            this.finishTime = finishTime;
        }

        @Override
        public String toString() {
            return "TimeResp{" +
                    "taskTime=" + taskTime +
                    ", finishTime=" + finishTime +
                    '}';
        }
    }


    /**
     * @param date
     */
    public static void getNextSpringFestival(String date) throws ParseException {
        String date1 = date;//今年春节时间
        Date date2 = stringToDate(date, "yyyy-MM-dd");
        Date date3 = new Date();
//        long time = (date3.getTime() - date2.getTime()) / 1000;
//        long day = time / (3600 * 24);

        Calendar calendar = Calendar.getInstance();
//        int year=calendar.get(Calendar.YEAR);
//        System.out.println("date2"+dateToString(date2,"yyyy-MM-dd"));
        int year = Integer.valueOf(dateToString(date2, "yyyy"));
        String s = (year + 1) + "-" + dateToString(date2, "MM-dd");
        Date date4 = stringToDate(s, "yyyy-MM-dd");
//        System.out.println("date4"+dateToString(date4,"yyyy-MM-dd"));
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            //上一年是闰年，下一春节会比今年春节晚19天
            calendar.setTime(date4);
            calendar.add(Calendar.DAY_OF_MONTH, 19 );
        } else {
            //上一年是平年，下一个春节会提前11天
            calendar.setTime(date4);
            calendar.add(Calendar.DAY_OF_MONTH, -11);

        }
        Date calendarTime = calendar.getTime();
        System.out.println("明年春节时间为:" + dateToString(calendarTime, "yyyy-MM-dd"));
    }


    /**
     * 获取两个时间的时间间隔
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public static int getDaysBetween(Calendar beginDate, Calendar endDate) {
        if (beginDate.after(endDate)) {
            Calendar swap = beginDate;
            beginDate = endDate;
            endDate = swap;
        }
        int days = endDate.get(Calendar.DAY_OF_YEAR)
                - beginDate.get(Calendar.DAY_OF_YEAR) + 1;
        int year = endDate.get(Calendar.YEAR);
        if (beginDate.get(Calendar.YEAR) != year) {
            beginDate = (Calendar) beginDate.clone();
            do {
                days += beginDate.getActualMaximum(Calendar.DAY_OF_YEAR);
                beginDate.add(Calendar.YEAR, 1);
            } while (beginDate.get(Calendar.YEAR) != year);
        }
        return days;
    }

}
