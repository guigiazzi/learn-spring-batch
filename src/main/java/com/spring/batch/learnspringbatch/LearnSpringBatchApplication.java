package com.spring.batch.learnspringbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class LearnSpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnSpringBatchApplication.class, args);
	}

}
