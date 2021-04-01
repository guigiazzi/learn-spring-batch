package com.spring.batch.learnspringbatch.configuration.processor.compositeItemProcessor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.batch.learnspringbatch.configuration.writer.SimpleItemWriter;

@Configuration
public class CompositeItemProcessorConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public ListItemReader<String> getCompositeItemProcessorReader() {
		List<String> items = new ArrayList<>(100);
		
		for (int i = 1; i <= 100; i++) {
			items.add(String.valueOf(i));
		}
		
		return new ListItemReader<>(items);
	}
	
	@Bean
	public SimpleItemWriter getCompostieItemProcessorWriter() {
		return new SimpleItemWriter();
	}
	
	@Bean
	public CompositeItemProcessor<String, String> getCompositeItemProcessor() throws Exception {
		List<ItemProcessor<String, String>> delegates = new ArrayList<>(2);
		
		delegates.add(new FilterItemProcessor());
		delegates.add(new AddSuffixItemProcessor());
		
		CompositeItemProcessor<String, String> compositeItemProcessor = new CompositeItemProcessor<>();
		
		compositeItemProcessor.setDelegates(delegates);
		compositeItemProcessor.afterPropertiesSet();
		
		return compositeItemProcessor;
	}
	
	@Bean
	public Step stepInsideCompositeItemProcessor() throws Exception {
		return stepBuilderFactory.get("stepInsideCompositeItemProcessor")
				.<String, String>chunk(10)
				.reader(getCompositeItemProcessorReader())
				.processor(getCompositeItemProcessor())
				.writer(getCompostieItemProcessorWriter())
				.build();
	}
	
	@Bean
	public Job compositeItemProcessorJob() throws Exception { 
		// creates a Job containing a composite item processor
		return jobBuilderFactory.get("compositeItemProcessorJob")
				.start(stepInsideCompositeItemProcessor())
				.build();
	}

}
