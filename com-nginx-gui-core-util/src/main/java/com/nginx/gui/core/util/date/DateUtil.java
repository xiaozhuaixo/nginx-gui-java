package com.nginx.gui.core.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: hengbin_wu
 * @Date: 2019/1/3 15:20
 * @Description:
 */
public class DateUtil {
    public static final String DATE_FORMAT_1 = "yy-MM-dd";

    public static final String DATE_FORMAT_2 = "yyyy-MM-dd";

    public static final String DATE_FORMAT_3 = "yyMMddHHmmss";

    public static final String DATE_FORMAT_4 = "yyyyMMdd";

    public static final String DATE_FORMAT_5 = "yyyy年MM月dd日 HH:mm:ss";

    public static final String DATE_FORMAT_6 = "yyyy年MM月dd日";

    public static final String DATE_FORMAT_7 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期加减
     * @param date
     * @param amount
     * @return
     */
    public static Date dateAdd(Date date , int amount){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE , amount);
        return cal.getTime();
    }

    /**
     * 月份加减
     * @param date
     * @param amount
     * @return
     */
    public static Date monthAdd(Date date , int amount){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH , amount);
        return cal.getTime();
    }

    /**
     * 获取当前时间字符串
     * @param dateFormat
     * @return
     */
    public static String getNowDateStr(String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat((dateFormat));
        return sdf.format(new Date());
    }

    /**
     * date转String
     * @param date
     * @param dateFormat
     * @return
     */
    public static String formatDateToStr(Date date , String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    /**
     * String 转 Date
     * @param date
     * @param dateFormat
     * @return
     */
    public static Date parseStrToDate(String date , String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date dt = null;
        try {
            dt = sdf.parse(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return dt;
    }

    /**
     * 从开始时间计算到结束时间天数
     * @param begin
     * @param end
     * @return
     */
    public static List<String> getBetweenDates(Date begin , Date end){
        List<String> result = new ArrayList();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while(begin.getTime() <= end.getTime()){
            result.add(formatDateToStr(tempStart.getTime() , DATE_FORMAT_2));
            tempStart.add(Calendar.DATE , 1);
            begin = tempStart.getTime();
        }
        return result;
    }

    /**
     * @Describe 获取定期事件的时间列表
     * @Param startDate 开始 endDate 结束
     * @Param type = 0 按日 1 按周 2 按月
     * @Param start 周期开始
     * @Param duration 周期长度 单位为日
     */
    public static List<String> getDateCycle(Date startDate, Date endDate, Integer type,
                                            Integer interval, Integer start, Integer duration) {

        if (!(interval > 0 || start > 0 || duration > 0)) {
            throw new IllegalArgumentException("Invalid Interval Start Duration ");
        }

        List<String> result = new ArrayList<String>();

        Date tmp = startDate;

        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startDate);
        switch (type) {
            case 0: {   //Calendar.DATE
                while (startDate.getTime() <= dateAdd(endDate,  duration).getTime()) {
                    for (int i = 1; i < duration + 1; i++) {
                        if (tempStart.getTime().after(endDate)) {
                            break;
                        }
                        result.add(formatDateToStr(tempStart.getTime(), DATE_FORMAT_2));
                        tempStart.add(Calendar.DATE, 1);
                    }
                    tempStart.add(Calendar.DATE, interval);
                    startDate = tempStart.getTime();
                }
                break;
            }
            case 1: { //Calendar.DAY_OF_WEEK
                while (startDate.getTime() <= dateAdd(endDate,  duration).getTime()) {
                    tempStart.set(Calendar.DAY_OF_WEEK, start + 1 > 7 ? 1 : start + 1);

                    Integer roundSWeek = tempStart.get(Calendar.WEEK_OF_YEAR);
                    for (int i = 1; i < duration + 1; i++) {
                        if (tempStart.getTime().after(endDate)) {
                            break;
                        }
                        if (tempStart.getTime().before(tmp)) {
                            tempStart.add(Calendar.DATE, 1);
                            continue;
                        }
                        result.add(formatDateToStr(tempStart.getTime(), DATE_FORMAT_2));
                        tempStart.add(Calendar.DATE, 1);
                    }
                    Integer roundEWeek = tempStart.get(Calendar.WEEK_OF_YEAR);

                    tempStart.add(Calendar.WEEK_OF_YEAR, roundEWeek - roundSWeek > 0 ? interval - 1 : interval);
                    startDate = tempStart.getTime();
                }
                break;
            }
            case 2: { //Calendar.DAY_OF_MONTH
                while (startDate.getTime() <= dateAdd(endDate, duration).getTime()) {
                    tempStart.set(Calendar.DAY_OF_MONTH, start);
                    Integer roundSMonth = tempStart.get(Calendar.MONTH);
                    for (int i = 1; i < duration + 1; i++) {
                        if (tempStart.getTime().after(endDate)) {
                            break;
                        }
                        if (tempStart.getTime().before(tmp)) {
                            tempStart.add(Calendar.DATE, 1);
                            continue;
                        }
                        result.add(formatDateToStr(tempStart.getTime(), DATE_FORMAT_2));
                        tempStart.add(Calendar.DATE, 1);
                    }
                    Integer roundEMonth = tempStart.get(Calendar.MONTH);

                    tempStart.add(Calendar.MONTH, roundEMonth - roundSMonth > 0 ? interval - 1 :
                            interval);
                    startDate = tempStart.getTime();
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid Type ! ");
            }

        }

        return result;
    }

    /**
     * 获取两天相差天数
     * @param start
     * @param end
     * @return
     */
    public static int dateDiffer(Date start , Date end){
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;

        Long diff = start.getTime() - end.getTime();

        Long day = diff / nd;
        return day.intValue();
    }

    /**
     * @param startDate      开始
     * @param endDate        结束
     * @param isRegular      是否定期
     * @param regularType    频率类型 0 按天 1 按周 2 按月
     * @param frequency      频率值
     * @param frequencyStart 频率开始
     * @param duration       持续天数
     * @param prioritySort   优先级 0 为已完成
     * @return 本轮剩余天数  本轮剩余日期
     * @Describe 计算 本轮剩余日 和 本轮结束日期
     */
    public static Map<String, Object> getRoundDate(Date startDate, Date endDate,
                                                   boolean isRegular,
                                                   Integer regularType,
                                                   Integer frequency,
                                                   Integer frequencyStart,
                                                   Integer duration,
                                                   Integer prioritySort) {
        Integer lastDay = null;
        Date lastDate = null;

        Date tmp = startDate;
        Date now = new Date();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startDate);
        List<Integer> differDays = new ArrayList<>();
        if (isRegular) {
            switch (regularType) {
//                日
                case 0: {
                    while (startDate.getTime() <= dateAdd(endDate,  duration).getTime()) {
                        if (tempStart.getTime().after(endDate)) {
                            break;
                        }
                        tempStart.add(Calendar.DATE, duration);
                        tempStart.add(Calendar.DATE, frequency);
                        int differD = dateDiffer(tempStart.getTime(), now);
                        if (differD >= 0) {
                            differDays.add(differD);
                        }
                        startDate = tempStart.getTime();
                    }
                    break;
                }
//                周
                case 1: {
                    while (startDate.getTime() <= dateAdd(endDate,  duration).getTime()) {
                        tempStart.set(Calendar.DAY_OF_WEEK, duration + 1 > 7 ? 1 : duration + 1);

                        Integer roundSWeek = tempStart.get(Calendar.WEEK_OF_YEAR);
                        if (tempStart.getTime().after(endDate)) {
                            break;
                        }
                        tempStart.add(Calendar.DATE, duration);
                        int differD = dateDiffer(tempStart.getTime(), now);
                        if (differD >= 0) {
                            differDays.add(differD);
                        }

                        Integer roundEWeek = tempStart.get(Calendar.WEEK_OF_YEAR);
                        tempStart.add(Calendar.WEEK_OF_YEAR, roundEWeek - roundSWeek > 0 ? frequency - 1
                                : frequency);
                        startDate = tempStart.getTime();
                    }

                    break;
                }
//                月
                case 2: {
                    while (startDate.getTime() <= dateAdd(endDate,  duration).getTime()) {
                        tempStart.set(Calendar.DAY_OF_MONTH, frequencyStart);
                        Integer roundSMonth = tempStart.get(Calendar.MONTH);
                        if (tempStart.getTime().after(endDate)) {
                            break;
                        }
                        tempStart.add(Calendar.DATE, duration);
                        int differD = dateDiffer(tempStart.getTime(), now);
                        if (differD >= 0) {
                            differDays.add(differD);
                        }
                        Integer roundEMonth = tempStart.get(Calendar.MONTH);
                        tempStart.add(Calendar.MONTH, roundEMonth - roundSMonth > 0 ? frequency - 1 :
                                frequency);
                        startDate = tempStart.getTime();
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Invalid Type ! ");
                }

            }
            if (!differDays.isEmpty()) {
                lastDay = Collections.min(differDays);
                lastDate = dateAdd(now, lastDay);
            } else {
                lastDay = 0;
                lastDate = endDate;
            }
        } else {
            lastDay = dateDiffer(endDate, now);
            lastDate = endDate;
        }
        Map<String, Object> resultMap = new HashMap();
        resultMap.put("lastDay", lastDay);
        resultMap.put("lastDate", formatDateToStr(lastDate, DATE_FORMAT_2));
        return resultMap;
    }
}
