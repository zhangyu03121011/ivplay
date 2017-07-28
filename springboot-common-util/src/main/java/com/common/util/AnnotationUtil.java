package com.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.common.base.annotation.Primary;
import com.common.base.annotation.Validate;
import com.common.base.common.BaseLogger;

/**
 * 注解工具
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class AnnotationUtil extends BaseLogger {

    private static AnnotationUtil annotationUtil = null;

    public synchronized static AnnotationUtil getInstance() {
        if (annotationUtil == null) {
            annotationUtil = new AnnotationUtil();
        }
        return annotationUtil;
    }

    private AnnotationUtil() {

    }

    /**
     * 获取主键属性
     * 
     * @param obj
     * @return
     */
    public <T> Field getAnnotationPrimaryField(Class<T> obj) {
        try {

            // 如果当前类为Object，则不查询主键
            if (obj.getName().equals(Object.class.getName())) {
                return null;
            }

            // 获取所有字段
            Field[] fields = obj.getDeclaredFields();
            if (!ArrayUtils.isEmpty(fields)) {
                for (Field field : fields) {

                    // 判断是否包含主键注解
                    if (field.isAnnotationPresent(Primary.class)) {
                        return field;
                    }

                }
            }

            return this.getAnnotationPrimaryField(obj.getSuperclass());

        } catch (SecurityException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取验证字段
     * 
     * @param obj
     * @return
     */
    public <T> List<Field> getAnnotationValidField(Class<T> obj) {

        List<Field> list = new ArrayList<Field>();

        try {

            // 如果当前类为Object，则不查询主键
            if (obj.getName().equals(Object.class.getName())) {
                return list;
            }

            // 获取所有字段
            Field[] fields = obj.getDeclaredFields();
            if (!ArrayUtils.isEmpty(fields)) {
                for (Field field : fields) {

                    // 判断是否包含验证注解
                    if (field.isAnnotationPresent(Validate.class)) {
                        list.add(field);
                    }

                }
            }

            // 向父级查找
            List<Field> listParent = this.getAnnotationValidField(obj.getSuperclass());
            list.addAll(listParent);

        } catch (SecurityException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return list;
    }

    /**
     * 获取所有字段
     * 
     * @param obj
     * @return
     */
    public <T> List<Field> getAllField(Class<T> obj) {

        List<Field> list = new ArrayList<Field>();

        try {

            // 如果当前类为Object，则不查询主键
            if (obj.getName().equals(Object.class.getName())) {
                return list;
            }

            // 获取所有字段
            Field[] fields = obj.getDeclaredFields();
            if (!ArrayUtils.isEmpty(fields)) {
                for (Field field : fields) {
                    list.add(field);
                }
            }

            // 向父级查找
            List<Field> listParent = this.getAllField(obj.getSuperclass());
            list.addAll(listParent);

        } catch (SecurityException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return list;
    }

    /**
     * 获取指定字段
     * 
     * @param obj
     * @return
     */
    public <T> Field getField(Class<T> obj, String fieldName) {
        Field field = null;
        try {

            // 如果当前类为Object，则不查询主键
            if (obj.getName().equals(Object.class.getName())) {
                return field;
            }

            // 获取所有字段
            Field[] fields = obj.getDeclaredFields();
            if (!ArrayUtils.isEmpty(fields)) {
                for (Field tmpField : fields) {
                    if (tmpField.getName().equalsIgnoreCase(fieldName)) {
                        return tmpField;
                    }
                }
            }

            // 向父级查找
            field = this.getField(obj.getSuperclass(), fieldName);

        } catch (SecurityException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return field;
    }

    /**
     * 设置指定字段值
     * 
     * @param obj
     * @param fieldName
     * @param fieldValue
     * @param overFlag
     */
    public <T> void setFieldValue(Object obj, String fieldName, Object fieldValue, boolean overFlag) {
        try {

            // 判断当前对象是否有userId属性
            Field field = getField(obj.getClass(), fieldName);
            if (field != null) {
                // 判断是否有值
                Object userIdObj = field.getDeclaringClass()
                        .getDeclaredMethod("get" + StringUtils.capitalize(field.getName())).invoke(obj);
                if ((userIdObj == null || StringUtils.isEmpty(userIdObj.toString())) || overFlag) {
                    field.getDeclaringClass()
                            .getDeclaredMethod("set" + StringUtils.capitalize(field.getName()), field.getType())
                            .invoke(obj, fieldValue);
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
