package com.github.labazhang.autoconfig.config;

import com.github.labazhang.autoconfig.condition.ServiceThreeCondition;
import com.github.labazhang.autoconfig.service.ServiceOne;
import com.github.labazhang.autoconfig.service.ServiceThree;
import com.github.labazhang.autoconfig.service.ServiceTwo;
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
