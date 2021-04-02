//package com.spring.batch.learnspringbatch.configuration.trigger;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.JobRegistry;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
//import org.springframework.batch.core.converter.DefaultJobParametersConverter;
//import org.springframework.batch.core.explore.JobExplorer;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.launch.JobOperator;
//import org.springframework.batch.core.launch.support.SimpleJobOperator;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableBatchProcessing
//public class JobTriggerConfiguration implements ApplicationContextAware {

// this spring.batch.job.enabled=false must go in application.properties so that jobs only run when we trigger them
//
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
//	
//	@Autowired
//	private JobRegistry jobRegistry;
//	
//	@Autowired
//	private JobLauncher jobLauncher;
//	
//	@Autowired
//	private JobRepository jobRepository;
//	
//	@Autowired
//	private JobExplorer jobExplorer;
//	
//	private ApplicationContext applicationContext;
//	
//	// --------------------- code for JobLauncher ---------------------
//	
//	@Bean
//	@StepScope
//	public Tasklet getTasklet(@Value("#{jobParameters['name']}") String name) {
//		return (contribution, chunkContext) -> {
//			System.out.println(String.format("Job ran with name %s", name));
//			return RepeatStatus.FINISHED;
//		};
//	}
//	
//	@Bean
//	public Step stepInsideTriggeredJob() {
//		return stepBuilderFactory.get("stepInsideTriggeredJob").tasklet(getTasklet(null)).build();
//	}
//
//	@Bean
//	public Job triggeredJob() { // creates a Job to be triggered via HTTP request
//		return jobBuilderFactory.get("triggeredJob")
//				.start(stepBuilderFactory.get("stepInsideTriggeredJob")
//					.tasklet(getTasklet(null))
//					.build())
//				.build();		
//	}
//
//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		this.applicationContext = applicationContext;
//	}
//	
//	// ------------------------------------------
//	
//	// --------------------- additional code for JobOperator ---------------------
//	@Bean
//	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() throws Exception {
//		JobRegistryBeanPostProcessor jobRegistry = new JobRegistryBeanPostProcessor();
//		
//		jobRegistry.setJobRegistry(this.jobRegistry);
//		jobRegistry.setBeanFactory(this.applicationContext.getAutowireCapableBeanFactory());
//		jobRegistry.afterPropertiesSet();
//		
//		return jobRegistry;
//	}
//	
//	@Bean
//	public JobOperator JobOperator() throws Exception {
//		SimpleJobOperator simpleJobOperator = new SimpleJobOperator();
//		
//		simpleJobOperator.setJobLauncher(this.jobLauncher);
//		simpleJobOperator.setJobParametersConverter(new DefaultJobParametersConverter());
//		simpleJobOperator.setJobRepository(this.jobRepository);
//		simpleJobOperator.setJobExplorer(this.jobExplorer);
//		simpleJobOperator.setJobRegistry(this.jobRegistry);
//		
//		simpleJobOperator.afterPropertiesSet();
//		
//		return simpleJobOperator;
//	}
//	
//	// ------------------------------------------
//	
//}
