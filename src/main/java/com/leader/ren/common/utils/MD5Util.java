package com.leader.ren.common.utils;

import java.security.MessageDigest;

public class MD5Util {
    /**
     * MD5加密,需要加密的字符串以及字符编码
     *
     * @param s,charsetname
     * @return
     */
    public final static String getMD5(String s, String charsetname) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp;
            if (null == charsetname || "".equals(charsetname)) {
                strTemp = s.getBytes();
            } else {
                strTemp = s.getBytes(charsetname);
            }
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}