package com.leader.ren.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObjectUtils {

    public static void chgMap2Obj(Map<String, Object> map, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            System.out.println("chgMap2Bean Error:" + e);
        }

        return;
    }

    public static Map<String, Object> chgObj2Map(Object obj) {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Class<?> propertyType = property.getPropertyType();
                    Object value = getter.invoke(obj);
                    if (String.valueOf(propertyType).indexOf("java.lang.Byte") > 0 && String.valueOf(value).equals("0")) {
                        map.put(key, String.valueOf(value));
                    } else if (String.valueOf(propertyType).indexOf("java.lang.Long") > 0 && String.valueOf(value).equals("0")) {
                        map.put(key, String.valueOf(value));
                    } else if (String.valueOf(propertyType).indexOf("java.lang.Integer") > 0 && String.valueOf(value).equals("0")) {
                        map.put(key, String.valueOf(value));
                    } else {
                        map.put(key, value);
                    }

                }

            }
        } catch (Exception e) {
            System.out.println("chgBean2Map Error:" + e);
        }

        return map;
    }

    public static String[] getNullFields(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        emptyNames.add("createTime");
        emptyNames.add("updateTime");
        for (PropertyDescriptor pd : pds) {
            try {
                Object srcValue = src.getPropertyValue(pd.getName());
                if (srcValue == null) {
                    emptyNames.add(pd.getName());
                }
            } catch (Exception e) {

            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static boolean allFieldNull(Object source) {
        boolean flag = true;
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            try {
                Object srcValue = src.getPropertyValue(pd.getName());
                if (srcValue != null) {
                    flag = false;
                }
            } catch (Exception e) {

            }
        }
        return flag;
    }

    /**
     * 根据appKey + phone  + "openId" 生成唯一openId
     *
     * @param appKey
     * @param objectId
     * @return
     */
    public static String getOpenId(String appKey, String objectId) {
        String beforeOpenId = appKey + objectId + "openId";
        return DigestUtils.md5Hex(beforeOpenId);
    }
}  