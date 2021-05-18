package com.xinfago.anno.autoconfig;

import com.xinfago.anno.autoconfig.service.ServiceOne;
import com.xinfago.anno.autoconfig.service.ServiceThree;
import com.xinfago.anno.autoconfig.service.ServiceTwo;
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
