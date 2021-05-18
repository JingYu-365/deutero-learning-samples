package com.xinfago.anno.autoconfig.config;

import com.xinfago.anno.autoconfig.condition.ServiceThreeCondition;
import com.xinfago.anno.autoconfig.service.ServiceOne;
import com.xinfago.anno.autoconfig.service.ServiceThree;
import com.xinfago.anno.autoconfig.service.ServiceTwo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * BeanConfig
 *
 * @author laba zhang
 */
@Configuration
@Import(value = {ServiceOne.class, ServiceTwo.class})
public class BeanConfig {

    @Bean
    @Conditional(ServiceThreeCondition.class)
    public ServiceThree serviceThree() {
        return new ServiceThree();
    }
}
