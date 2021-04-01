package com.spring.batch.learnspringbatch.configuration.writer.flatFileItemWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class FlatFileItemWriterConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public ListItemReader<String> getFlatFileItemReader() {
		List<String> items = new ArrayList<>(100);
		
		for (int i = 1; i <= 100; i++) {
			items.add(String.valueOf(i));
		}
		
		return new ListItemReader<>(items);
	}
	
	@Bean
	public FlatFileItemWriter<String> getFlatFileItemWriter() throws Exception {
		FlatFileItemWriter<String> itemWriter = new FlatFileItemWriter<>();
		
		itemWriter.setLineAggregator(new PassThroughLineAggregator<>());
		String outputPath = File.createTempFile("outputOfMyJob", ".txt").getAbsolutePath();
		System.out.println("Output path: " + outputPath);
		itemWriter.setResource(new FileSystemResource(outputPath));
		itemWriter.afterPropertiesSet();
		
		return itemWriter;
	}
	
	@Bean
	public Step stepInsideFlatFileItemWriter() throws Exception {
		return stepBuilderFactory.get("stepInsideFlatFileItemWriter")
				.<String, String>chunk(10)
				.reader(getFlatFileItemReader())
				.writer(getFlatFileItemWriter())
				.build();
	}
	
	@Bean
	public Job flatFileItemWriterJob() throws Exception { // creates a Job containing a flat file item reader
		return jobBuilderFactory.get("flatFileItemWriterJob")
				.start(stepInsideFlatFileItemWriter())
				.build();
	}

}
