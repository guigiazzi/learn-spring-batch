package com.spring.batch.learnspringbatch.configuration.processor;

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

import com.spring.batch.learnspringbatch.configuration.writer.SimpleItemWriter;

@Configuration
public class SimpleItemProcessorConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public ListItemReader<String> getItemProcessorReader() {
		List<String> items = new ArrayList<>(100);
		
		for (int i = 1; i <= 100; i++) {
			items.add(String.valueOf(i));
		}
		
		return new ListItemReader<>(items);
	}
	
	@Bean
	public SimpleItemWriter getSimpleItemProcessorWriter() {
		return new SimpleItemWriter();
	}
	
	@Bean
	public SimpleItemProcessor getSimpleItemProcessor() {
		return new SimpleItemProcessor();
	}
	
	@Bean
	public Step stepInsideSimpleItemProcessor() {
		return stepBuilderFactory.get("stepInsideSimpleItemProcessor")
				.<String, String>chunk(10)
				.reader(getItemProcessorReader())
				.processor(getSimpleItemProcessor())
				.writer(getSimpleItemProcessorWriter())
				.build();
		
		// NOTE: an ItemProcessor is where we should implement our business logic.
		// It can receive any object as input, and return any object as output
	}
	
	@Bean
	public Job simpleItemProcessorJob() { // creates a Job containing an item processor
		return jobBuilderFactory.get("simpleItemProcessorJob")
				.start(stepInsideSimpleItemProcessor())
				.build();
	}

}
