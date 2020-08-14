package com.trade.entity.batch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * TODO fix bug in Spring to get the batch runner to run in JUnit 5.
 */
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@ContextConfiguration(classes = {SwiftPartyBatchConfiguration.class})
class SwiftPartyBatchConfigurationTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@AfterEach
	public void cleanUp() {
		jobRepositoryTestUtils.removeJobExecutions();
	}

	@Test
	public void testBatch() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		JobInstance actualJobInstance = jobExecution.getJobInstance();
		ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

		assertThat(actualJobInstance.getJobName(), is("switchPartyBatch"));
		assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
	}
}