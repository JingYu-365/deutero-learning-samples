package com.github.labazhang.autoconfig.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Bean {@link com.github.labazhang.autoconfig.service.ServiceThree} condition
 *
 * @author laba zhang
 */
public class ServiceThreeCondition implements Condition {
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return false;
    }
}
