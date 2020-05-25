package com.leader.ren.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class MyBeanUtils extends BeanUtils {

	public static void copyProperties(Object source, Object target) throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						if (value != null) {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}

	/**
	 * 获取JavaBean的字段名称集合，排除不需要获取的字段名
	 *
	 * @param clazz
	 *            JavaBean字节码对象
	 * @param exFieldNameList
	 *            排除指定字段名
	 * @return
	 */
	public static <T> List<String> getFieldNameList(Class<T> clazz, List<String> exFieldNameList) {
		Field[] fields = clazz.getDeclaredFields();
		List<String> filedNameList = null;
		if (fields != null && fields.length > 0) {
			filedNameList = new ArrayList<String>();
			for (Field field : fields) {
				if (exFieldNameList != null && !exFieldNameList.isEmpty()) {
					if (!exFieldNameList.contains(field.getName())) {
						filedNameList.add(field.getName());
					}
				} else {
					filedNameList.add(field.getName());
				}
			}
		}
		return filedNameList;
	}

	public static Object getProperty(Object bean, String fieldName) {
		Object value = null;
		try {
			// 创建属性描述器
			PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, bean.getClass());
			// 获得读方法
			Method readMethod = descriptor.getReadMethod();
			// 调用读方法
			value = readMethod.invoke(bean);
		} catch (Exception e) {
		}
		return value;
	}
}
