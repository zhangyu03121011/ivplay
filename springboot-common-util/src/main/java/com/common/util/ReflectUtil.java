package com.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 反射工具类
 * 
 * @author: HuiJunLuo
 * @date:2016年1月7日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
@SuppressWarnings("unchecked")
public class ReflectUtil {

	private static ReflectUtil reflectUtil = null;

	public synchronized static ReflectUtil getInstance() {
		if (reflectUtil == null) {
			reflectUtil = new ReflectUtil();
		}
		return reflectUtil;
	}

	private ReflectUtil() {
	}

	/**
	 * 获取当前调用的方法名称
	 * 
	 * @return
	 */
	public String getCurrentMethod() {
		try {
			Throwable t = new Throwable();
			StackTraceElement[] stes = t.getStackTrace();
			return stes[0].getMethodName();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取当前调用的方法名称
	 * 
	 * @return
	 */
	public String getCurrentMethod(Exception e) {
		try {
			StackTraceElement[] stes = e.getStackTrace();
			return stes[0].getMethodName();
		} catch (Exception e1) {
			return null;
		}
	}

	/**
	 * 获得超类的参数类型，取第一个参数类型
	 * 
	 * @param <T>
	 *            类型参数
	 * @param clazz
	 *            超类类型
	 */
	@SuppressWarnings("rawtypes")
	public <T> Class<T> getClassGenricType(final Class clazz) {
		return getClassGenricType(clazz, 0);
	}

	/**
	 * 获取父类的泛型类
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getSuperClassGenericType(Class<?> clazz) {
		ParameterizedType pType = (ParameterizedType) clazz.getGenericSuperclass();
		Class<?> entityClass = (Class<?>) pType.getActualTypeArguments()[0];
		return entityClass;
	}

	/**
	 * 根据索引获得超类的参数类型
	 * 
	 * @param clazz
	 *            超类类型
	 * @param index
	 *            索引
	 */
	@SuppressWarnings("rawtypes")
	public Class getClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	public String validObjField(Object obj) {

		String msg = null;

		try {
			// 验证请求数据是否正确
			boolean flag = true;

			StringBuffer buffer = new StringBuffer();

			if (obj != null) {
				// 获取所有需要验证的字段
				List<Field> list = AnnotationUtil.getInstance().getAnnotationValidField(obj.getClass());
				for (Field field : list) {
					Object fieldValue = field.getDeclaringClass()
							.getDeclaredMethod("get" + StringUtils.capitalize(field.getName())).invoke(obj);
					if (fieldValue == null || StringUtils.isEmpty(fieldValue.toString())) {
						buffer.append(field.getName()).append(",");
						flag = false;
					}
				}
			} else {
				flag = false;
				buffer.append("obj=null");
			}

			if (!flag) {
				msg = "[" + obj.getClass().getSimpleName() + "][validate field]["
						+ StringUtils.substringBeforeLast(buffer.toString(), ",") + "]";
			}
		} catch (Exception e) {
			return null;
		}
		return msg;
	}

}
