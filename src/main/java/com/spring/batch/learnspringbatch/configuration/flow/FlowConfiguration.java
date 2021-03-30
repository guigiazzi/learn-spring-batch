package com.spring.batch.learnspringbatch.configuration.flow;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class FlowConfiguration {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step flowFoo1() {
		return stepBuilderFactory.get("flowFoo1").tasklet((contribution, chunkContext) -> {
			System.out.println("This is flowFoo1");
			return RepeatStatus.FINISHED;
		}).build();
	}
	
	@Bean
	public Step flowFoo2() {
		return stepBuilderFactory.get("flowFoo2").tasklet((contribution, chunkContext) -> {
			System.out.println("This is step flowFoo2");
			return RepeatStatus.FINISHED;
		}).build();
	}

	@Bean
	public Flow myFlow() { // creates e Flow containing steps
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("myFlow");
		
		flowBuilder.start(flowFoo1())
			.next(flowFoo2())
			.end();
		
		return flowBuilder.build();
	}

}
