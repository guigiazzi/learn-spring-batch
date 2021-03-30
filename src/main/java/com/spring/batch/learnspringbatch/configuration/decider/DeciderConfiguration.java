package com.spring.batch.learnspringbatch.configuration.decider;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class DeciderConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step startStep() {
		return stepBuilderFactory.get("startStep").tasklet((contribution, chunkContext) -> {
			System.out.println("This is startStep");
			return RepeatStatus.FINISHED;
		}).build();
	}
	
	@Bean
	public Step evenStep() {
		return stepBuilderFactory.get("evenStep").tasklet((contribution, chunkContext) -> {
			System.out.println("This is evenStep");
			return RepeatStatus.FINISHED;
		}).build();
	}
	
	@Bean
	public Step oddStep() {
		return stepBuilderFactory.get("oddStep").tasklet((contribution, chunkContext) -> {
			System.out.println("This is oddStep");
			return RepeatStatus.FINISHED;
		}).build();
	}
	
	@Bean
	public JobExecutionDecider decider() {
		return new OddDecider();
	}

	@Bean
	public Job deciderJob() { // creates a Job containing a decider
		return jobBuilderFactory.get("deciderJob")
				.start(startStep())
				.next(decider())
				.from(decider()).on("ODD").to(oddStep())
				.from(decider()).on("EVEN").to(evenStep())
				.from(oddStep()).on("*").to(decider())
				.end()
				.build();
		
		// QUESTION: why is it calling both ODD and EVEN steps, even though I only call it once?
	}
	
	public static class OddDecider implements JobExecutionDecider {
		private int count = 0;
		
		@Override
		public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
			count++;
			
			if(count % 2 == 0) {
				return new FlowExecutionStatus("EVEN");
			} else {
				return new FlowExecutionStatus("ODD");
			}
			
		}
	}

}
