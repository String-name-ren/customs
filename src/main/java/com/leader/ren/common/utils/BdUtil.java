package com.leader.ren.common.utils;

import java.math.BigDecimal;

/**
 * BigDecimal工具类
 * @auther: yanxuewen
 * date: 2019/1/21 13:15
 */
public class BdUtil {

    /**
     * 判断对象是否为Null
     * @param obj
     * @param objs
     * @return
     */
    public static boolean isNull(Object obj, Object ... objs) {
        if (obj != null) { return false; }
        if (objs == null) { return true; }
        for (Object itemObj: objs) {
            if (itemObj != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断对象不为Null
     * @param obj
     * @param objs
     * @return
     */
    public static boolean notNull(Object obj, Object ... objs) {
        if (obj == null) { return false; }
        if (objs == null) { return false; }
        for (Object itemObj: objs) {
            if (itemObj == null) {
                return false;
            }
        }
        return true;
    }

    //------------------------------------------ java.math.BigDecimal

    /**
     * 判断BigDecimal是否为0
     * @param bd
     * @return
     */
    public static boolean isZero(BigDecimal bd) {
        return bd != null && bd.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 判断BigDecimal是否为null或者0
     * @param bd
     * @return
     */
    public static boolean isNullOrZero(BigDecimal bd) {
        return bd == null || isZero(bd);
    }

    /**
     * 判断BigDecimal不为null或者0
     * @param bd
     * @return
     */
    public static boolean notNullOrZero(BigDecimal bd) {
        return !isNullOrZero(bd);
    }

    /**
     * 判断BigDecimal是否大于0
     * @param bd
     * @return
     */
    public static boolean isPositive(BigDecimal bd) {
        return bd != null && bd.compareTo(BigDecimal.ZERO) == 1;
    }

    /**
     * 判断BigDecimal是否小于0
     * @param bd
     * @return
     */
    public static boolean isNegative(BigDecimal bd) {
        return bd != null && bd.compareTo(BigDecimal.ZERO) == -1;
    }

    /**
     * 判断BigDecimal是否为空或负数
     * @param val
     * @return
     */
    public static boolean isNullOrNegative(BigDecimal val) {
        return val == null || isNegative(val);
    }

    //------------------------------------------java.lang.Byte
    public static boolean isZero(Byte val) {
        return val != null && val.intValue() == 0;
    }

    public static boolean isNullOrZero(Byte val) {
        return val == null || isZero(val);
    }

    public static boolean notNullOrZero(Byte val) {
        return !isNullOrZero(val);
    }

    /**
     * 判断Byte是否为大于0的正数
     * @param val
     * @return
     */
    public static boolean isPositive(Byte val) {
        return val != null && val.intValue() > 0;
    }

    /**
     * 判断Byte是否为小于0的负数
     * @param val
     * @return
     */
    public static boolean isNegative(Byte val) {
        return val != null && val.intValue() < 0;
    }

    /**
     * 判断Byte是否为空或负数
     * @param val
     * @return
     */
    public static boolean isNullOrNegative(Byte val) {
        return val == null || isNegative(val);
    }


    //------------------------------------------ java.lang.Integer

    public static boolean isZero(Integer val) {
        return val != null && val.intValue() == 0;
    }

    public static boolean isNullOrZero(Integer val) {
        return val == null || isZero(val);
    }

    public static boolean notNullOrZero(Integer val) {
        return !isNullOrZero(val);
    }

    /**
     * 判断Integer是否为大于0的正数
     * @param val
     * @return
     */
    public static boolean isPositive(Integer val) {
        return val != null && val.intValue() > 0;
    }

    /**
     * 判断Integer是否为小于0的负数
     * @param val
     * @return
     */
    public static boolean isNegative(Integer val) {
        return val != null && val.intValue() < 0;
    }

    /**
     * 判断Integer是否为空或负数
     * @param val
     * @return
     */
    public static boolean isNullOrNegative(Integer val) {
        return val == null || isNegative(val);
    }

    //------------------------------------------ java.lang.Long

    public static boolean isZero(Long val) {
        return val != null && val.longValue() == 0L;
    }

    public static boolean isNullOrZero(Long val) {
        return val == null || isZero(val);
    }

    public static boolean notNullOrZero(Long val) {
        return !isNullOrZero(val);
    }

    /**
     * 判断Long是否为大于0的正数
     * @param val
     * @return
     */
    public static boolean isPositive(Long val) {
        return val != null && val.longValue() > 0L;
    }

    /**
     * 判断Long是否为小于0的负数
     * @param val
     * @return
     */
    public static boolean isNegative(Long val) {
        return val != null && val.longValue() < 0L;
    }

    /**
     * 判断Long是否为空或负数
     * @param val
     * @return
     */
    public static boolean isNullOrNegative(Long val) {
        return val == null || isNegative(val);
    }

    /**
     * 金额格式化
     * @param amt
     * @param fomatType
     * @return
     */
    public static BigDecimal getFormatCommonAMT(BigDecimal amt,int fomatType){
        if (amt == null) {
            return null;
        }

        return amt.setScale(fomatType,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * null -> ZERO
     * @param bd
     * @return
     */
    public static BigDecimal nullToZero(BigDecimal bd) {
        if (bd == null) { return BigDecimal.ZERO; }
        return bd;
    }

}
