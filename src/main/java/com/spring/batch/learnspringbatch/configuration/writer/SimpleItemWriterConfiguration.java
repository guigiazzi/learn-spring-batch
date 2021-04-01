package com.spring.batch.learnspringbatch.configuration.writer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleItemWriterConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public ListItemReader<String> getItemReader() {
		List<String> items = new ArrayList<>(100);
		
		for (int i = 1; i <= 100; i++) {
			items.add(String.valueOf(i));
		}
		
		return new ListItemReader<>(items);
	}
	
	@Bean
	public SimpleItemWriter getSimpleItemWriter() {
		return new SimpleItemWriter();
	}
	
	@Bean
	public Step stepInsideSimpleItemWriter() {
		return stepBuilderFactory.get("stepInsideSimpleItemWriter")
				.<String, String>chunk(10)
				.reader(getItemReader())
				.writer(getSimpleItemWriter())
				.build();
	}
	
	@Bean
	public Job simpleItemWriterJob() { // creates a Job containing an item reader
		return jobBuilderFactory.get("simpleItemWriterJob")
				.start(stepInsideSimpleItemWriter())
				.build();
	}

}
