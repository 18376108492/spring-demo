package com.itdan.spring_demo.test;

import org.apache.commons.lang.time.DateUtils;
import org.omg.CORBA.DATA_CONVERSION;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 定时条件测试
 */
public class TaskTest {

    public static void main(String[] args) throws ParseException {

        Calendar calendar = Calendar.getInstance();
      //   String DAY_OF_14="yyyy-MM-14 23:59:59";
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      // SimpleDateFormat sdf = new SimpleDateFormat(DAY_OF_14);

       // calendar.add(Calendar.MONTH, 1);
       // calendar.set(Calendar.DAY_OF_MONTH, 0);
//        calendar.add(Calendar.MONTH, 0);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int actualMinimum = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置本月起始日的年月日时分秒格式
        //calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),actualMinimum,00,00,00);
        Date calendarTime = calendar.getTime();
        //设置本月结束日的年月日时分秒格式
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),actualMaximum,23,59,59);
        String format = sdf.format(calendar.getTime());
        Date parse = sdf.parse(format);
        System.out.println("Calendar:"+calendarTime);
        System.out.println("format:"+format);

        System.out.println(new Date().getDate());

        System.out.println("----------------------季度测试---------------------------");
       // System.out.println("获取当前季度:"+ getQuarter());
        //获取本季度第一天是时间和最后一天时间
        Date[] dates = getCurrQuarter(1);
        System.out.println("本季度任务开始时间:"+sdf.format(dates[0]));
        System.out.println("本季度任务结束时间:"+sdf.format(dates[1]));

        System.out.println(new Date().getMonth());


    }

    /**
     * 生成定时任务时间为：本月一号的凌晨零点和本月十五号的凌晨零点
     * @param s
     * @return
     */
    public TimeResp getTime(String s) throws ParseException {
        Calendar calendar = Calendar.getInstance();
       // SimpleDateFormat simpleDateFormat = null;
        TimeResp timeResp=new TimeResp();

        if (s.equals("每月一次")){
           //每月一次，将本月一号作为任务开始时间，本月的最后一天作为任务结束时间
            Date taskTime = getTaskTime(calendar);
            timeResp.setTaskTime(taskTime);
            Date finishDate = getFinishDate(calendar);
            timeResp.setFinishTime(finishDate);
            System.out.println(taskTime.toString());
        }else  if(s.equals("每月两次")){
           //每月两次，分别将本月一号和十五号作为任务开始日期，分别把本月十四号和月底的最后一天作为结束时间
           //因为每月需要生产两次任务,生成任务创建时间之前，需要判断当前时间为本月一号还是十五号，再根据情况来生成任务开始和结束时间
            //判断当前时间是否为本月一号
            if(new Date().getDate()==1){
                Date taskTime = getTaskTime(calendar);
                timeResp.setTaskTime(taskTime);
                //本月14号23点59分59秒的模板
                String DAY_FOR_14="yyyy-MM-14 23:59:59";
                Date finishDate = getFinishDate(calendar);
                timeResp.setFinishTime(parseTempleDate(finishDate,DAY_FOR_14));
            }else {
                //本月15号00点00分00秒模板
                String DAY_FOR_15="yyyy-MM-15 00:00:00";
                Date taskTime = getTaskTime(calendar);
                timeResp.setTaskTime(parseTempleDate(taskTime,DAY_FOR_15));
                timeResp.setFinishTime(getFinishDate(calendar));
            }
        }else if(s.equals("一个季度一次")){
            //一个季度一次，任务开始时间本季度第一天，任务结束为本季度的最后一天
            Date[] currQuarter = getCurrQuarter(getQuarter());
            if(currQuarter.length==0){
                throw new RuntimeException("出错啦");
            }
            timeResp.setTaskTime(currQuarter[0]);
            timeResp.setFinishTime(currQuarter[1]);
        }else if(s.equals("半年一次")){
           //半年一次，认为开始时间分别为1月1日和6月1日，任务结束时间为五月底和年底的最后一天
           if(new Date().getMonth()<=4){
               timeResp.setTaskTime(parseTempleDate(calendar.getTime(), "yyyy-01-01 00:00:00"));
               timeResp.setFinishTime(parseTempleDate(calendar.getTime(), "yyyy-05-31 23:59:59"));
           }else {
               timeResp.setTaskTime(parseTempleDate(calendar.getTime(), "yyyy-06-01 00:00:00"));
               timeResp.setFinishTime(parseTempleDate(calendar.getTime(), "yyyy-012-31 23:59:59"));
           }
        }else if(s.equals("国庆前一次")){
           
        }else if(s.equals("春节前一次")){

        }
        return timeResp;
    }

    /**
     * 获取本月的起始日期
     * @param calendar
     * @return
     */
    private Date getTaskTime(Calendar calendar){
        if(calendar==null){
            throw new RuntimeException("参数为空");
        }
        int actualMinimum = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置本月起始日的年月日时分秒格式(设置为凌晨零点)
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),actualMinimum,00,00,00);
        return calendar.getTime();
     }

    /**
     * 获取本月的结束日期
     * @param calendar
     * @return
     */
     private Date getFinishDate(Calendar calendar){
         if(calendar==null){
             throw new RuntimeException("参数为空");
         }
         int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
         //设置本月结束日的年月日时分秒格式
         calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),actualMaximum,23,59,59);
         return calendar.getTime();
     }

    /**
     * 获取当前季度
     *
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
        return quarter ;
    }


    /**
     * 获取某季度的第一天和最后一天
     *	@param
     */
    public static Date[] getCurrQuarter(int num) throws ParseException {
       Date [] dates=new Date[2];
        String str = "";
        // 设置本年的季
        Calendar quarterCalendar = null;
        //季度第一天模板
        //季度最后一天模板
        String QUARTER_Finish_DAY="yyyy-MM-dd 23:59:59";

        switch (num) {
            case 1: // 本年到现在经过了一个季度，在加上前4个季度
                quarterCalendar = Calendar.getInstance();
                quarterCalendar.set(Calendar.MONTH, 3);
                quarterCalendar.set(Calendar.DATE, 1);
                quarterCalendar.add(Calendar.DATE, -1);
                //获取本季度最后一天
                System.out.println(quarterCalendar.getTime().toString());
                dates[0]=parseTempleDate(quarterCalendar.getTime(), "yyyy-01-01 00:00:00");
                dates[1] = parseTempleDate(quarterCalendar.getTime(),QUARTER_Finish_DAY);
                break;
            case 2: // 本年到现在经过了二个季度，在加上前三个季度
                quarterCalendar = Calendar.getInstance();
                quarterCalendar.set(Calendar.MONTH, 6);
                quarterCalendar.set(Calendar.DATE, 1);
                quarterCalendar.add(Calendar.DATE, -1);
                //季度第一天模板
                dates[0]=parseTempleDate(quarterCalendar.getTime(), "yyyy-04-01 00:00:00");
                dates[1] = parseTempleDate(quarterCalendar.getTime(),QUARTER_Finish_DAY);
                break;
            case 3:// 本年到现在经过了三个季度，在加上前二个季度
                quarterCalendar = Calendar.getInstance();
                quarterCalendar.set(Calendar.MONTH, 9);
                quarterCalendar.set(Calendar.DATE, 1);
                quarterCalendar.add(Calendar.DATE, -1);
                //季度第一天模板
                System.out.println();
                dates[0]=parseTempleDate(quarterCalendar.getTime(), "yyyy-07-01 00:00:00");
                dates[1] = parseTempleDate(quarterCalendar.getTime(),QUARTER_Finish_DAY);
                break;
            case 4:// 本年到现在经过了四个季度，在加上前一个季度
                quarterCalendar = Calendar.getInstance();
                String MONTH_FOR_10="yyyy-10-01 00:00:00";
                String MONTH_FOR_12="yyyy-12-21 23:59:59";
                dates[0]=parseTempleDate(quarterCalendar.getTime(), MONTH_FOR_10);
                dates[1] = parseTempleDate(quarterCalendar.getTime(),MONTH_FOR_12);
                break;
        }
        return dates;
    }


    /**
     * 用途：以指定的格式格式化日期字符串
     * @param pattern 字符串的格式
     * @param currentDate 被格式化日期
     * @return Date 已格式化的日期字符串
     * @throws NullPointerException 如果参数为空
     */
    public static Date parseTempleDate(Date currentDate, String pattern) throws ParseException {
        if(currentDate == null || "".equals(pattern) || pattern == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String format = sdf.format(currentDate);
        sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.parse(format) ;
    }


    class TimeResp{
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
}
