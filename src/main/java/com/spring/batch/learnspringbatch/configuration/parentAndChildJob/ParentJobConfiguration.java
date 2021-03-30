//package com.spring.batch.learnspringbatch.configuration.parentAndChildJob;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.JobStepBuilder;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//@EnableBatchProcessing
//public class ParentJobConfiguration {
//
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
//	
//	@Autowired
//	private Job childJob;
//	
//	@Autowired
//	private JobLauncher jobLauncher;
//
//	@Bean
//	public Step stepOfParentJob() {
//		return stepBuilderFactory.get("stepOfParentJob").tasklet((contribution, chunkContext) -> {
//			System.out.println("This is stepOfParentJob");
//			return RepeatStatus.FINISHED;
//		}).build();
//	}
//	
//	@Bean
//	public Job parentJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//		// creates a nested Job, containing a parent an a child job
//		
//		// NOTE: Spring Batch runs all jobs at startup by default. 
//		// We don't want the child job to be executed before (or at the same time) as the parent job,
//		// so we need do add a new config in application.properties in order to do that
//		
//		// QUESTION: how does it know how to reference my ChildJob, even though I'm not referencing it anywhere?
//		
//		Step childJobStep = new JobStepBuilder(new StepBuilder("childJobStep"))
//				.job(childJob)
//				.launcher(jobLauncher)
//				.repository(jobRepository)
//				.transactionManager(transactionManager)
//				.build();
//		
//		return jobBuilderFactory.get("parentJob")
//				.start(stepOfParentJob())
//				.next(childJobStep)
//				.build();
//	}
//
//}
