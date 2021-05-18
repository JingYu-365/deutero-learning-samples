package com.xinfago.properties;

import com.xinfago.properties.pojo.Person;
import com.xinfago.properties.properties.PropertiesSourcePojo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class PropertiesSampleApplicationTests {

    @Autowired
    private Person person;

    @Autowired
    private PropertiesSourcePojo propertiesSourcePojo;

    @Test
    public void testYaml() {
        System.out.println(person);
    }

    @Test
    public void testPropertiesSource() {
        System.out.println(propertiesSourcePojo);
    }

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testForConfiguration() {
        System.out.println(applicationContext.containsBean("myService"));
    }

}
