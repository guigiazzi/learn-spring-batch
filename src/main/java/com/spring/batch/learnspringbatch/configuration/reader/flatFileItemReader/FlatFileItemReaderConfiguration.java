//package com.spring.batch.learnspringbatch.configuration.reader.flatFileItemReader;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//@Configuration
//public class FlatFileItemReaderConfiguration {
//
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
//	
//	@Bean
//	public FlatFileItemReader<Customer> customItemReader() {
//		FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
//		
//		reader.setLinesToSkip(1);
//		reader.setResource(new ClassPathResource("src/main/resources/data/customer.csv"));
//		
//		DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();
//		
//		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
//		tokenizer.setNames(new String[] {"id", "firstName", "lastName", "birthdate"});
//		
//		customerLineMapper.setLineTokenizer(tokenizer);
//		customerLineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
//		customerLineMapper.afterPropertiesSet();
//		
//		reader.setLineMapper(customerLineMapper);
//		
//		return reader;
//	}
//	
//	@Bean
//	public ItemWriter<Customer> customItemWriter() {
//		return items -> {
//			for(Customer item : items) {
//				System.out.println("item: " + item);
//			}
//		};
//	}
//	
//	@Bean
//	public Step stepInsideFlatFileItemReader() {
//		return stepBuilderFactory.get("stepInsideFlatFileItemReader")
//				.<Customer, Customer>chunk(10)
//				.reader(customItemReader())
//				.writer(customItemWriter())
//				.build();
//	}
//	
//	@Bean
//	public Job simpleItemReaderJob() { // creates a Job containing an item reader that reads from a file
//		return jobBuilderFactory.get("stepInsideFlatFileItemReader")
//				.start(stepInsideFlatFileItemReader())
//				.build();
//	}
//
//}
