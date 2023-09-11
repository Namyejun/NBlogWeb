package com.ssamz.jblog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class NBlogWebApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void passwordEncode() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "abc123@@";
		String encodedPassword = encoder.encode(rawPassword);
		System.out.println(rawPassword);
		System.out.println(encodedPassword);
		System.out.println(encoder.matches(rawPassword, encodedPassword));
	}

}
