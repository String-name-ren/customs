package com.leader.ren.common.constant;

/**
 * 日期格式化【静态常量类】
 * @auther: yanxuewen
 * date: 2019/5/9 18:00
 */
public class DateFmt {

    /** 常用格式 **/
    public static final String YMD_HG = "yyyy-MM-dd";
    public static final String YMD_XG = "yyyy/MM/dd";
    public static final String YMD_MG = "yyyyMMdd";

    public static final String YMD_HM = "yyyy-MM-dd HH:mm";
    public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_HMSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /** 不太常用的格式 **/
    public static final String YMD_HMS_MG1 = "yyyyMMddHHmmss";
    public static final String YMD_HMSS_MG1 = "yyyyMMddHHmmssSSS";
    public static final String H12M = "hh:mm";
    public static final String H24M = "HH:mm";
    public static final String H12 = "hh";
    public static final String H24 = "HH";
    public static final String MIN = "mm";
    // public static final String YMD_HMS_DOT = "yyyy.MM.dd HH:mm:ss";

    /** 包装 **/
    public static final String PACK_S_START_TIME = " 00:00:00";
    public static final String PACK_S_END_TIME = " 23:59:59";

    /** 无效日期 **/
    public static final String INVALID_DATE_STR1 = "0000-00-00 00:00:00";

}
