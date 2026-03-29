package com.G5C.EduMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EduMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduMsApplication.class, args);
	}

}
