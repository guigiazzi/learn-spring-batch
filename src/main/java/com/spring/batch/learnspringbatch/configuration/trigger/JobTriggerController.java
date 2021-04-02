//package com.spring.batch.learnspringbatch.configuration.trigger;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.launch.JobOperator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class JobTriggerController {
//	
////	@Autowired
////	private JobLauncher jobLauncher; // worse implementation option
//	
//	@Autowired
//	private JobOperator jobOperator; // better implementation option
//	
////	@Autowired
////	private Job job;
//	
//	@PostMapping(value = "/trigger")
//	public void triggerJob(@RequestParam String name) throws Exception {
////		JobParameters jobParameters = new JobParametersBuilder()
////				.addString("name", name)
////				.toJobParameters();
//		
////		this.jobLauncher.run(job, jobParameters); // worse implementation option
//		this.jobOperator.start("triggeredJob", String.format("name=%s", name));	
//	}
//
//}
