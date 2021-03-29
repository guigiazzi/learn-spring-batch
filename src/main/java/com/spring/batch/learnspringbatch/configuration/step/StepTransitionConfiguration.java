package com.spring.batch.learnspringbatch.configuration.step;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StepTransitionConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {
			System.out.println("This is step 1");
			return RepeatStatus.FINISHED;
		}).build();
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2").tasklet((contribution, chunkContext) -> {
			System.out.println("This is step 2");
			return RepeatStatus.FINISHED;
		}).build();
	}

	@Bean
	public Step step3() {
		return stepBuilderFactory.get("step3").tasklet((contribution, chunkContext) -> {
			System.out.println("This is step 3");
			return RepeatStatus.FINISHED;
		}).build();
	}

	@Bean
	public Job simpleNextTransitionJob() {
		return jobBuilderFactory.get("simpleNextTransitionJob")
				.start(step1())
				.next(step2())
				.next(step3())
				.build();
	}
	
	@Bean
	public Job OnStatusTransitionJob() {
		return jobBuilderFactory.get("onStatusTransitionJob")
				.start(step1())
				.on("COMPLETED").to(step2())
				.from(step2()).on("COMPLETED").to(step3())
				.from(step3()).end()
				.build();
	}

}
