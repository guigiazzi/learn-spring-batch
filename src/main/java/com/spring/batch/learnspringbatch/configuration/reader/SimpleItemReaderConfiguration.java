package com.spring.batch.learnspringbatch.configuration.reader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleItemReaderConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public SimpleItemReader getSimpleItemReader() {
		List<String> data = new ArrayList<>(3);
		
		data.add("Foo");
		data.add("Bar");
		data.add("Baz");
		
		return new SimpleItemReader(data);
	}
	
	@Bean
	public Step stepInsideSimpleItemReader() {
		return stepBuilderFactory.get("stepInsideSimpleItemReader")
				.<String, String>chunk(2)
				.reader(getSimpleItemReader())
				.writer(list -> {
					for(String item : list) {
						System.out.println("item: " + item);
					}
				}).build();
	}
	
	@Bean
	public Job simpleItemReaderJob() { // creates a Job containing an item reader
		return jobBuilderFactory.get("simpleItemReaderJob")
				.start(stepInsideSimpleItemReader())
				.build();
	}

}
