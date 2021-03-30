//package com.spring.batch.learnspringbatch.configuration.parentAndChildJob;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableBatchProcessing
//public class ChildJobConfiguration {
//
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
//	
//	@Bean
//	public Step stepOfChildJob() {
//		return stepBuilderFactory.get("stepOfChildJob").tasklet((contribution, chunkContext) -> {
//			System.out.println("This is stepOfChildJob");
//			return RepeatStatus.FINISHED;
//		}).build();
//	}
//	
//	@Bean
//	public Job childJob() {
//		return jobBuilderFactory.get("childJob")
//				.start(stepOfChildJob())
//				.build();
//	}
//	
//}
