package com.bezkoder.spring.datajpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootDataJpaApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Complete Load Main Class");
	}

}
