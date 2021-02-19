package com.github.labazhang.autoconfig;

import com.github.labazhang.autoconfig.service.ServiceOne;
import com.github.labazhang.autoconfig.service.ServiceThree;
import com.github.labazhang.autoconfig.service.ServiceTwo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        ServiceOne one = ctx.getBean(ServiceOne.class);
        System.out.println(one);

        ServiceTwo two = ctx.getBean(ServiceTwo.class);
        System.out.println(two);

        // 当 ServiceThreeCondition#matches 返回true时，可以获得此bean
        ServiceThree three = ctx.getBean(ServiceThree.class);
        System.out.println(three);
    }
}
