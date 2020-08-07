package com.trade.batch;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class AbstractBatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	//@Value("${jobName}")
	private String jobName = "swiftparty";

	public String getJobName() {
		return jobName;
	}

	@Bean
	public Step exit() {
		System.exit(0);
		return null;
	}

}
