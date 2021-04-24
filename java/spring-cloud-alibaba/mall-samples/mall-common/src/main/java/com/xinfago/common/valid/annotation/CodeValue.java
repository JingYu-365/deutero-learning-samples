package com.xinfago.common.valid.annotation;

import com.xinfago.common.valid.validator.CodeValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 定义码表注解
 *
 * @author xinfago
 */
@Documented
@Constraint(validatedBy = {CodeValueValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface CodeValue {

    String message() default "{com.xinfago.common.valid.annotation.CodeValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] value() default {};
}
