package com.github.labazhang.spring.ioc.overview.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author JKong
 * @version v1.0
 * @description Bean 注解，参照{@link org.springframework.stereotype.Component}
 * @date 2020-03-15 08:37.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Super {
}
