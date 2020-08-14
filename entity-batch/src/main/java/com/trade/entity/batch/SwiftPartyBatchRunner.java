package com.trade.entity.batch;

import com.trade.EnvProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableBatchProcessing
@SpringBootApplication(scanBasePackages = {"com.trade.entity", "com.trade.entity.cassandra"})
@Slf4j
public class SwiftPartyBatchRunner {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("SwiftPartyBatchJob")
	private Job job;

	public static void main(String[] args) {
		new SpringApplicationBuilder(JobLauncher.class)
				.profiles(EnvProfile.DEV_PROFILE)
				.run(args);
		SpringApplication.run(SwiftPartyBatchRunner.class, args);
	}
}
