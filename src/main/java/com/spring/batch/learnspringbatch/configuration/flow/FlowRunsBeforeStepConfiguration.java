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
public class FlowRunsBeforeStepConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step runsAfterFlowStep() {
		return stepBuilderFactory.get("runsAfterFlowStep").tasklet((contribution, chunkContext) -> {
			System.out.println("This is runsAfterFlowStep");
			return RepeatStatus.FINISHED;
		}).build();
	}

	@Bean
	public Job flowRunsBeforeStep(Flow flow) { // in this example, flow is running before the step
		return jobBuilderFactory.get("flowRunsBeforeStep")
				.start(flow)
				.next(runsAfterFlowStep())
				.end()
				.build();
		// QUESTION: how does Spring Batch know the argument for this method
		// is my FlowConfiguraition class, even though I'm not passing it, calling it
		// and specifying it anywhere?
	}

}
