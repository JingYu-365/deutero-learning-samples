package com.github.labazhang.anno.autoconfig.condition;

import com.github.labazhang.anno.autoconfig.service.ServiceThree;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Bean {@link ServiceThree} condition
 *
 * @author laba zhang
 */
public class ServiceThreeCondition implements Condition {
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return false;
    }
}
