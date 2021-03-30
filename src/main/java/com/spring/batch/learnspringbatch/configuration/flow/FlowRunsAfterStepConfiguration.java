package com.spring.batch.learnspringbatch.configuration.flow;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class FlowRunsAfterStepConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step runsBeforeFlowStep() {
		return stepBuilderFactory.get("runsBeforeFlowStep").tasklet((contribution, chunkContext) -> {
			System.out.println("This is runsBeforeFlowStep");
			return RepeatStatus.FINISHED;
		}).build();
	}

	@Bean
	public Job flowRunsAfterStep(Flow flow) { // creates a Job where the flow is running after the step
		return jobBuilderFactory.get("flowRunsAfterStep")
				.start(runsBeforeFlowStep())
				.on("COMPLETED").to(flow)
				.end()
				.build();
		// QUESTION: how does Spring Batch know the argument for this method
		// is my FlowConfiguraition class, even though I'm not passing it, calling it
		// and specifying it anywhere?
		
		// NOTE: when we want to execute the flow after the step, we can't use next().
		// For these instances, we need to use .on(STATUS).to(flow)
	}

}
