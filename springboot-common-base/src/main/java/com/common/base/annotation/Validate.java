package com.common.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证注解
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {

}
