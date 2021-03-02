package com.github.labazhang.properties;

import com.github.labazhang.properties.pojo.Person;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class PropertiesSampleApplicationTests {

	@Autowired
	private Person person;

	@Test
	public void testYaml() {
		System.out.println(person);
	}

}
