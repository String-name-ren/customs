package com.leader.ren.common.utils;

import com.leader.ren.common.constant.DateFmt;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class DateUtil {

    public static final String FMT_1 = "yyyy-MM-dd";
    public static final String FMT_PATTERN_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FMT_2 = "yyyy.MM.dd HH:mm:ss";
    public static final String FMT_3 = "yyyy-MM-dd HH:mm:ss";

    private DateUtil(){}

    /** 缓存30秒 */
    public final static long CACHE_TIME = 30L;
    /** 缓存1分钟 */
    public final static long CACHE_MINUTE = 60L;
    /** 缓存半小时 */
    public final static long CACHE_HALF_HOUR = 1800L;
    /** 缓存1小时 */
    public final static long CACHE_HOUR = 3600L;
    /** 缓存1天 */
    public final static long CACHE_DAY = 3600L * 24;
    /** 缓存1周 */
    public final static long CACHE_WEEK = 3600L * 24 * 7;
    /** 缓存一月 */
    public static long CACHE_MONTH = 3600L * 24 * DateUtil.daysOfCurrentMonth();

    //int 类型缓存时间 60分钟
    public final static int CACHE_SIX_MINUTE = 6;

    //int 类型缓存时间 60分钟
    public final static int CACHE_EXPIRE_TIME = 60;

    /**
     * 按天标志
     */
    public final static String DAY = "DAY";

    /**
     * 按月标志
     */
    public final static String MONTH = "MONTH";

    /**
     * 按年标志
     */
    public final static String YEAR = "YEAR";

    private final static DateTimeFormatter dtnf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static DateTimeFormatter dnf = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static DateTimeFormatter dm = DateTimeFormatter.ofPattern("yyyy-MM");

    /**
     * 当前时间<br/>
     * format: yyyy-MM
     * */
    public final static String getCurrentYears(){
        return dm.format(LocalDateTime.now());
    }


    /**
     * 当前时间<br/>
     * format: yyyy-MM-dd HH:mm:dd
     * */
    public final static String getDateTime(){
        return dtf.format(LocalDateTime.now());
    }

    /**
     * 当前时间<br/>
     * format: yyyyMMddHHmmdd
     * */
    public final static String getDateTimeNoFormat(){
        return dtnf.format(LocalDateTime.now());
    }

    /**
     * 当天<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getToday(){
        return LocalDate.now().toString();
    }

    /**
     * 当前日期<br/>
     * format: yyyyMMdd
     * */
    public final static String getDateNoFormat(){
        return dnf.format(LocalDate.now());
    }

    /**
     * 本周开始日期<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getWeekStartDate(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusDays(localDate.getDayOfWeek().getValue() - 1).toString();
    }

    /**
     * 一周日期<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getWeekBeforeDate(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusWeeks(1).toString();
    }

    /**
     * 本周结束日期<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getWeekEndDate(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusDays(localDate.getDayOfWeek().getValue() - 7).toString();
    }

    /**
     * 当月第一天<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getMonthStartDate(){
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString();
    }

    /**
     * 一个月前日期<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getMonthBeforeDate(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusMonths(1).toString();
    }

    /**
     * 当月最后一天<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getMonthEndDate(){
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).toString();
    }


    /**
     * 当年第一天<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getYearStartDate(){
        return LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).toString();
    }

    /**
     * 当年最后一天<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getYearEndDate(){
        return LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).toString();
    }

    /**
     * 昨天<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getYesterday(){
        return LocalDate.now().minusDays(1).toString();
    }

    /**
     * 上周周开始日期<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getLastWeekStartDate(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusDays((localDate.getDayOfWeek().getValue() - 1) + 7).toString();
    }

    /**
     * 上周结束日期<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getLastWeekEndDate(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusDays((localDate.getDayOfWeek().getValue() - 1) + 1).toString();
    }


    /**
     * 上月月第一天<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getLastMonthStartDate(){
        return LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).toString();
    }

    /**
     * 上月最后一天<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getLastMonthEndDate(){
        return LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).toString();
    }

    /**
     * 去年第一天<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getLastYearStartDate(){
        return LocalDate.now().minusYears(1).with(TemporalAdjusters.firstDayOfYear()).toString();
    }

    /**
     * 去年最后一天<br/>
     * format: yyyy-MM-dd
     * */
    public final static String getLastYearEndDate(){
        return LocalDate.now().minusYears(1).with(TemporalAdjusters.lastDayOfYear()).toString();
    }


    public final static String monday(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusDays(localDate.getDayOfWeek().getValue() - 1).toString();
    }

    public final static String tuesday(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusDays(localDate.getDayOfWeek().getValue() - 2).toString();
    }

    public final static String wednesday(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusDays(localDate.getDayOfWeek().getValue() - 3).toString();
    }

    public final static String thursday(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusDays(localDate.getDayOfWeek().getValue() - 4).toString();
    }

    public final static String friday(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusDays(localDate.getDayOfWeek().getValue() - 5).toString();
    }

    public final static String saturday(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusDays(localDate.getDayOfWeek().getValue() - 6).toString();
    }

    public final static String sunday(){
        LocalDate localDate = LocalDate.now();
        return localDate.minusDays(localDate.getDayOfWeek().getValue() - 7).toString();
    }

    /**
     * 当前月的天数
     * */
    public final static int daysOfCurrentMonth(){
        return YearMonth.now().lengthOfMonth();
    }

    /**
     * 今年的天数
     * */
    public final static int daysOfCurrentYear(){
        return YearMonth.now().lengthOfYear();
    }

    /**
     * Date 转 LocalDate
     * */
    public final static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date 转 LocalDateTime
     * */
    public final static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime 转 Date
     * */
    public final static Date localDateToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate 转 Date
     * */
    public final static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 格式化日期日期字符串为LocaDate，日期格式：yyyy-MM-dd, yyyyMMdd, yyyy-MM-dd HH:mm:ss, yyyyMMdd HH:mm:ss
     * @param dateStr
     * @return
     */
    public final static LocalDate dateToLocalDate(String dateStr) {
        LocalDate localDate = null;
        SimpleDateFormat sdf = null;
        try {
            if (!CommUtils.isNull(dateStr)){
                if (dateStr.length() == 10){
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                } else if (dateStr.length() == 8){
                    sdf = new SimpleDateFormat("yyyyMMdd");
                }  else if (dateStr.length() == 19){
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }  else if (dateStr.length() == 17){
                    sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                } else {
                    return null;
                }
            }
            localDate = dateToLocalDate(sdf.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDate;
    }

    /**
     * 当前时间一个月前的日期
     * format: yyyy-MM-dd
     */
    public final static String getLastMonthDate(){
        return LocalDate.now().minus(1, ChronoUnit.MONTHS).toString();
    }

    /**
     * 当前时间一个月后的日期
     * format: yyyy-MM-dd
     */
    public final static String getNextMonthDate(){
        return LocalDate.now().plus(1, ChronoUnit.MONTHS).toString();
    }

    /**
     * 下个月的开始日期
     * format: yyyy-MM-dd
     */
    public final static String getNextMonthStartDate(){
        return LocalDate.now().minusMonths(-1).with(TemporalAdjusters.firstDayOfMonth()).toString();
    }

    /**
     * 下个月的结束日期
     * format: yyyy-MM-dd
     */
    public final static String getNextMonthEndDate(){
        return LocalDate.now().minusMonths(-1).with(TemporalAdjusters.lastDayOfMonth()).toString();
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔多少天(向下取整)
     * @param date1
     * @param date2
     * @return
     */
    public static Long differentDaysByMillisecond(Date date1,Date date2)
    {
        Long days = ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔多少小时(向下取整)
     * @param date1
     * @param date2
     * @return
     */
    public static Long differentHoursByMillisecond(Date date1,Date date2)
    {
        Long hours = ((date2.getTime() - date1.getTime()) / (1000*3600));
        return hours;
    }

    /**
     * 自定义时间范围，通过时间秒毫秒数判断两个时间的间隔多少天(向上取整)
     * @param date1
     * @param date2
     * @return
     */
    public static Long selfDifferentDaysByMillisecond(Date date1,Date date2)
    {
        double doubleDays = (((double)(date2.getTime() - date1.getTime())) / (1000*3600*24));
        //System.out.println(doubleDays);
        Long days = ((long) (Math.ceil(doubleDays)));
        //System.out.println(days);
        return days;
    }

    /**
     * 获取当日截止时间
     * @param date
     * @return
     */
    public static Date getEndDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);// 把endDate的时间赋给日历类
        // 设置为23点59分59秒
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();// 日历类-->日期类
    }

    public static LocalDate getYesterdayBeforeDate(int diff, String calFlag){
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        if (DateUtil.YEAR.equals(calFlag)){
            ca.add(Calendar.YEAR, -(diff+1));
        } else if (DateUtil.MONTH.equals(calFlag)) {
            ca.add(Calendar.MONTH, -diff);
        }else {
            ca.add(Calendar.DATE, -diff);
        }
        Date lastMonth = ca.getTime();
        System.out.println(lastMonth);
        return lastMonth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    }

    /**
     * 解析日期
     * @param text
     * @param pattern
     * @auther: yanxuewen
     * date: 2019/1/5 16:30
     * @return
     */
    public static Date parseDate(final String text, String pattern) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            date = sdf.parse(text);
        } catch (Exception e) {
            throw new RuntimeException("Parse date failed: " + text);
        }
        return date;
    }

    /**
     * 解析日期 --支持多个pattern
     * @param text
     * @param patterns
     * @auther: yanxuewen
     * date: 2019/4/9 17:30
     * @return
     */
    public static Date parseDatePlus(final String text, String ... patterns) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        if (ArrayUtils.isEmpty(patterns)) {
            patterns = new String[] {DateFmt.YMD_XG, DateFmt.YMD_MG, DateFmt.YMD_HG, DateFmt.YMD_HM, DateFmt.YMD_HMS, DateFmt.YMD_HMSS};
        }
        patterns = sortArrayDescByLen(patterns);

        Date date = null;
        for (String pattern: patterns) {
            try {
                date = parseDate(text, pattern);
                if (date != null)  {break; }
            } catch (Exception e) {}
        }
        if (date == null) {
            throw new RuntimeException("Parse date failed: " + text);
        }
        return date;
    }

    /**
     * 日期格式化
     * @param date
     * @param pattern
     * @auther: yanxuewen
     * date: 2019/1/5 16:30
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }


    /**
     * 日期格式化
     * @param date
     * @param pattern
     * @param suffix  -- 后缀，如" 00:00:00"、" 23:59:59"
     * @auther: yanxuewen
     * date: 2019/1/5 16:30
     * @return
     */
    public static String formatDate(Date date, String pattern, String suffix) {
        if (date == null) {
            return null;
        }
        if (suffix == null) {
            suffix = "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date) + suffix;
    }

    /**
     * 操作日期
     * @param date
     * @param field  --Calendar.field
     * @param offset
     * @auther: yanxuewen
     * date: 2019/1/5 16:30
     * @return
     */
    public static Date plusDate(Date date, int field, int offset) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(field, calendar.get(field) + offset);
        return calendar.getTime();
    }

    public static String getLastMonth() {
        LocalDate today = LocalDate.now();
        today = today.minusMonths(1);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM");
        return formatters.format(today);
    }

    /**
     * 获取上一个月第一天
     * @return
     */
    public static String getUpMonthDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar   cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return format.format(cal_1.getTime())+" 00:00:00";
        }

        /**
         * 获取上一个月最后一天
         * @return
         */
        public static String getLastUpMonthDate() {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cale = Calendar.getInstance();
            cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
            return format.format(cale.getTime())+" 23:59:59";
    }


    /**
     * 获取当前月第一天
     * @return
     */
    public static String getMonthDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar   cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return format.format(cal_1.getTime())+" 00:00:00";
    }

    /**
     * 获取当前月最后一天
     * @return
     */
    public static String getLastDayMonthDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(ca.getTime())+" 23:59:59";
    }

    /**
     * 将秒域设置为0
     *
     * @param date 日期
     * @return
     */
    public static Date secondSetZero(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        // 将分秒,毫秒域清零
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        return cal1.getTime();
    }

    /**
     * 将时间域设置为日终
     *
     * @param date 日期
     * @return
     */
    public static Date timeToDayEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        date = calendar.getTime();
        return date;
    }

    /**
     * 将时间域设置为日期开始
     *
     * @param date 日期
     * @return
     */
    public static Date getBeginDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        return date;
    }

    //-------------------------------------------- Private Methods
    private static String[] sortArrayDescByLen(String[] strArr) {
        if (ArrayUtils.isNotEmpty(strArr)) {
            if (strArr.length == 1) {
                return strArr;
            }

            String strTmp;
            for (int i = 0; i < strArr.length; i++) {
                for (int j = strArr.length-1; j > i; j--) {
                    if (strArr[i].length() < strArr[j].length()) {
                        strTmp = strArr[i];
                        strArr[i] = strArr[j];
                        strArr[j] = strTmp;
                    }
                }
            }
        }

        return strArr;
    }

    /**
     * 获取两个日期字符串之间的所有日期
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<LocalDate> getAllDates(String startDate, String endDate){
        //定义起始日期
        Date d1 = DateUtil.localDateToDate(DateUtil.dateToLocalDate(startDate));
        //定义结束日期
        Date d2 = DateUtil.localDateToDate(DateUtil.dateToLocalDate(endDate));
        //定义日期实例
        Calendar dd = Calendar.getInstance();
        dd.setTime(d1);//设置日期起始时间
        List<LocalDate> dates = new ArrayList<>();
        //判断是否到结束日期
        while(dd.getTime().compareTo(d2) <= 0){
            LocalDate localDate = DateUtil.dateToLocalDate(dd.getTime());
            dates.add(localDate);
            //进行当前日期月份加1
            dd.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }


    public static void main(String[] args) throws ParseException {
//        System.out.println(DateUtil.getYesterdayBeforDate(7, DateUtil.DAY));
//        System.out.println(TimeConst.Range.getObject("DAY_7").getRange());
//        System.out.println(TimeConst.Range.getObject("DAY_7"));
//
//        String dateStr = "20190313 12:12:12";


        Date date = new Date();

        System.out.println(DateUtil.formatDate(date,"yyyy-MM-dd HH:mm:ss.SSS"));

        System.out.println(getMonthDate());
        System.out.println(getLastDayMonthDate());
//        System.out.println(formatDate(parseDatePlus("20190409"), "yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("获取本周开始日期：" + getWeekStartDate());
        System.out.println("获取上周开始日期：" + getLastWeekStartDate());

        List<LocalDate> datas = getAllDates("2019-05-14", "2019-05-12");
        for (LocalDate localDate : datas) {
            System.out.println(localDate);
        }
    }

}
