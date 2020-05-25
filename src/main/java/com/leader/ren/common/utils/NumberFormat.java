package com.leader.ren.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字格式化工具栏
 *
 * @author 刘娇
 * date: 2018/12/28 20:15
 */
public class NumberFormat {

    /**
     * 格式化Double类型，返回 #############.00
     * @param doubleValue
     * @return
     */
    public static Double foramtDouble(Double doubleValue){
        Double foramtDouble = 0.00;
        DecimalFormat decimalFormat = new DecimalFormat("###########0.00");
        try {
            if(CommUtils.isNull(doubleValue)){
                return 0.00;
            }else{
                foramtDouble = Double.valueOf(decimalFormat.format(doubleValue));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return foramtDouble;
    }

    /**
     * 格式化Double类型，返回字符串 #############.00
     * @param doubleValue
     * @return
     */
    public static String foramtDoubleStr(Double doubleValue){
        String foramtDouble = "0.00";
        DecimalFormat decimalFormat = new DecimalFormat("###########0.00");
        try {
            if(CommUtils.isNull(doubleValue)){
                return "0.00";
            }else{
                foramtDouble = decimalFormat.format(doubleValue);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return foramtDouble;
    }

    /**
     * 格式化Double类型，返回字符串 #############.00
     * @param bigDecimal
     * @return
     */
    public static String foramtBigDecimalStr(BigDecimal bigDecimal){
        String foramtDouble = "0.00";
        DecimalFormat decimalFormat = new DecimalFormat("###########0.00");
        try {
            if(CommUtils.isNull(bigDecimal)){
                return "0.00";
            }else{
                foramtDouble = decimalFormat.format(bigDecimal);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return foramtDouble;
    }

    /**
     * 电子券将用与创建券码前缀，勿随意修改顺序
     * */
    public static final String radix62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";

    public static final String radix16 = "0123456789abcdef";
    public static final String radix10 = "0123456789";
    public static final String radix8 = "01234567";
    public static final String radix2 = "01";

    /**
     * Long类型数据转换为任意进制字符串
     *
     * @author: 兰祥建
     * date 2019/2/22 15:28
     * @param val 数据值
     * @param radixVal 进制定义字符串，如2进制为：“01”，10进制为“0123456789”，16进制为“0123456789abcdef”
     * @param minLength 最小长度，不足则左侧补零值字符
     * @return
     */
    public static String longToRadix(Long val, String radixVal, int minLength){
        StringBuffer str = new StringBuffer(longToRadix(val, radixVal));
        while(str.length() < minLength){
            if(str.length() > 0 && str.charAt(0) == '-'){
                str.insert(1, radixVal.charAt(0));
            } else {
                str.insert(0, radixVal.charAt(0));
            }
        }
        return str.toString();
    }

    /**
     * 将整数数值val转换成radixVal.length()进制数据
     *
     * @author: 兰祥建
     * date 2019/2/22 9:56
     * @param val 数据值
     * @param radixVal 进制定义字符串，如2进制为：“01”，10进制为“0123456789”，16进制为“0123456789abcdef”
     * @return
     */
    public static String longToRadix(Long val, String radixVal){
        if(radixVal == null || radixVal.length() == 0){
            throw new RuntimeException("the param radixVal is necessary, like '0123456789' or '0123456789abcdef' and so on");
        }
        if(val == null){
            return "";
        }

        String rst = "";
        if(val < 0){
            rst = "-";
            val = 0-val;
        }

        if(val == 0){
            return String.valueOf(radixVal.charAt(0));
        } else {
            if(val >= radixVal.length()){
                return rst + longToRadix(val/radixVal.length(), radixVal) + longToRadix(val%radixVal.length(), radixVal);
            } else {
                return rst + radixVal.charAt(Integer.valueOf(val.toString()));
            }
        }
    }

    /**
     * 将进制数据字符串转换为Long数据
     *
     * @author: 兰祥建
     * date 2019/2/22 15:04
     * @param val 特殊进制数据值
     * @param radixVal 进制定义字符串，如2进制为：“01”，10进制为“0123456789”，16进制为“0123456789abcdef”
     * @return
     */
    public static Long radixTolong(String val, String radixVal){
        if(radixVal == null || radixVal.length() == 0){
            throw new RuntimeException("the param radixVal is necessary, like '0123456789' or '0123456789abcdef' and so on");
        }

        if(val == null|| val.length() == 0){
            return 0L;
        }
        boolean isNegative = val.startsWith("-");
        if(isNegative) {
            val = val.substring(1);
        }

        while(val.length() > 0 && val.charAt(0) == radixVal.charAt(0)){
            val = val.substring(1);
        }

        if(val.length() == 0){
            return 0L;
        }

        char c = val.charAt(0);
        val = val.substring(1);
        int idx = radixVal.indexOf(String.valueOf(c));
        if(idx == -1){
            throw new RuntimeException("val is illegal!");
        }
        Long baseValue = 0L;
        if(idx > 0){
            baseValue = BigDecimal.valueOf(Math.pow(radixVal.length(), val.length())).longValue() * idx;
        }

        Long rst = 0L;
        if(val.length() == 0){
            rst = isNegative?0-baseValue:baseValue;
        } else {
            rst = baseValue + radixTolong(val, radixVal);
        }
        return isNegative?0-rst:rst;
    }

}
