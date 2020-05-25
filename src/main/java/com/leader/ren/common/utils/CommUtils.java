package com.leader.ren.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共工具类
 *
 * @author Hui Wang
 */
public class CommUtils {

	/**
	 * 判断对象是否为NULL
	 *
	 * @param obj 任意对象
	 * @return boolean true 对象为NULL false 对象不为空
	 */
	public static boolean isNull(Object obj) {
		boolean result = false;
		if (obj != null) {
			if (obj instanceof String) {
				if (((String) obj).trim().isEmpty()) {
					result = true;
				}
			} else if (obj instanceof Collection) {
				if (((Collection) obj).isEmpty()) {
					result = true;
				}
			} else if (obj instanceof Map) {
				if (((Map) obj).isEmpty()) {
					result = true;
				}
			} else if (obj.getClass().isArray()) {
				if (Array.getLength(obj) <= 0) {
					return true;
				}
			}

		} else {
			result = true;
		}
		return result;
	}

	public static boolean isNotNull(Object obj){
		return !isNull(obj);
	}

	/**
	 * 获得一个UUID
	 *
	 * @return String UUID
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}

	/**
	 * MD5加密
	 *
	 * @param source 待产生MD5的byte数组
	 * @return String MD5值
	 */
	public static String getMD5(byte[] source) {
		String s = null;
		// 用来将字节转换成16进制表示的字符
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char ch[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				ch[k++] = hexDigits[byte0 >>> 4 & 0xf];
				ch[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(ch);
		} catch (Exception ex) {
			Logger.getLogger(CommUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
		return s;
	}

	/**
	 * 根据给定的日期格式将日期字符串解析为日期对象
	 *
	 * @param dateString 日期字符串
	 * @param pattern 给定的日期格式,如果为NULL则默认使用yyyy-MM-dd
	 * @return Date 解析后的日期
	 */
	public static Date convertStringToDate(String dateString, String pattern) {
		Date date = null;
		if (pattern == null || pattern.trim().equals("")) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			date = sdf.parse(dateString);
		} catch (ParseException ex) {
			Logger.getLogger(CommUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
		return date;
	}

	/**
	 * 根据给定的日期格式将日期解析为日期字符串
	 *
	 * @param date 日期
	 * @param pattern 给定的日期格式,如果为NULL则默认使用yyyy-MM-dd
	 * @return String 解析后的日期字符串
	 */
	public static String convertDateToString(Date date, String pattern) {
		String dateString;
		if (pattern == null || pattern.trim().equals("")) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		dateString = sdf.format(date);
		return dateString;
	}

	/**
	 * 比较两个日期是否相等
	 *
	 * @param d1 日期
	 * @param d2 日期
	 * @param pattern 给定的日期格式,如果为NULL则默认使用yyyy-MM-dd hh:mm:ss yyyy-MM-dd hh:mm:ss
	 * @return true 相等，false 不等
	 */
	public static boolean compareDate(Date d1, Date d2, String pattern) {
		boolean result = false;
		if (d1 != null && d2 != null) {
			String date1 = convertDateToString(d1, pattern);
			String date2 = convertDateToString(d2, pattern);

			if (date1.equals(date2)) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 *
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 正则表达式验证
	 *
	 * @param dataType 1:邮箱 2:日期 3:电话号码 4:数字
	 * @param data
	 * @return
	 */
	public static boolean validate(int dataType, String data) {
		String regexStr = "";
		switch (dataType) {
		case 1:
			regexStr = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
			break;
		case 2:
			regexStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
			break;
		case 3:
			regexStr = "(\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$";
			break;
		case 4:
			regexStr = "-?(0|([1-9][0-9]*))(.[0-9]+)?";
			break;
		}
		Pattern pattern = Pattern.compile(regexStr);
		Matcher matcher = pattern.matcher(data);
		return matcher.matches();
	}

	/**
	 * 验证手机格式
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile){
		String regexStr = "0?1[0-9]{10}";
		if(StringUtils.isBlank(mobile) || !Pattern.matches(regexStr,mobile)){
			return false;
		}
		return true;
	}

	/**
	 * 验证密码格式
	 * @param password
	 * @return
	 */
	public static boolean checkPwd(String password){
		String regexStr = "[a-zA-Z0-9]{6,20}";
		if(StringUtils.isBlank(password) || !Pattern.matches(regexStr,password)){
			return false;
		}
		return true;
	}

	/**
	 * 验证id
	 * @param primaryIdStr
	 * @author yanxuewen
	 * date 2019-05-30 09:15
	 * @return
	 */
	public static boolean checkPrimaryId(String primaryIdStr) {
		final String regexStr = "\\d+";
		if (StringUtils.isBlank(primaryIdStr)) { return false; }
		boolean checkFlag = Pattern.matches(regexStr, primaryIdStr);
		if (checkFlag) {
			Long primaryId = Long.parseLong(primaryIdStr);
			if (!isPrimaryId(primaryId)) {
				checkFlag = false;
			}
		}
		return checkFlag;
	}

	/**
	 * 生成随机多位数字
	 *
	 * @return
	 */
	public static String getRandomNumber(int num) {
		Random random = new Random();
		StringBuilder randomCode = new StringBuilder();
		for (int i = 0; i < num; i++) {
			// 得到随机产生的验证码数字。
			String strRand = String.valueOf(random.nextInt(10));
			// 将产生的多个随机数组合在一起。
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}

	public static String formatterPhone(String phone) {
		if (!CommUtils.isNull(phone)) {

			return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
		}
		return "";
	}

	public static String formatterIdCard(String idCard) {
		if (!CommUtils.isNull(idCard)) {

			return idCard.substring(0, 3) + "****" + idCard.substring(idCard.length() - 4);
		}
		return "";
	}

	public static String toString(Object obj) {
		if (obj != null) {
			return obj.toString();
		} else {
			return "";
		}
	}

	/**
	 * 判断是否是合法id
     * @param id
	 * @param ids
	 * @author yanxuewen
	 * date 2019-01-28 17:30
	 * @return
	 */
	public static boolean isPrimaryId(Long id, Long ... ids) {
		if (notPrimaryId(id)) { return false; }
		if (ids == null) { return false; }
		for (Long itemId : ids) {
			if (notPrimaryId(itemId)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否是非法id
	 * @param id
     * @author yanxuewen
     * date 2019-05-24 10:50
	 * @return
	 */
	public static boolean notPrimaryId(Long id) {
		return !BdUtil.isPositive(id);
	}

	/**
	 * 判断是否有null对象
	 * @param objs
	 * @author yanxuewen
	 * date 2019-01-28 17:30
	 * @return
	 */
	public static boolean hasNull(Object ... objs) {
		for (Object obj : objs) {
			if (obj == null) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		// System.out.println(CommUtils.getMD5("123456".getBytes()));
		// String uuid = CommUtils.getUUID();
		/*
		 * System.out.println(uuid + ".length = " + uuid.length()); String signature = SecurityUtils.sign(
		 * "15623060179|0002900F0041080|11032302065863805555|http://www.baidu.com" ); System.out.println(signature);
		 * System.out.println(CommUtils.getRandomNumber(6));
		 */
		String phone = "111      ";

		System.out.println(CommUtils.checkPwd(phone));
	}
}
