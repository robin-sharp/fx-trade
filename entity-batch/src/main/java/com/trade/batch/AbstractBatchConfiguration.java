package com.trade.batch;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.Nullable;

import java.io.IOException;

import static java.lang.String.format;

@Slf4j
public class AbstractBatchConfiguration {

	@Autowired
	protected JobBuilderFactory jobBuilderFactory;

	@Autowired
	protected StepBuilderFactory stepBuilderFactory;

	private String jobName;

	private String locationPattern;

	private boolean exitOnCompletion;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getLocationPattern() {
		return locationPattern;
	}

	public void setLocationPattern(String locationPattern) {
		this.locationPattern = locationPattern;
	}

	public boolean isExitOnCompletion() {
		return exitOnCompletion;
	}

	public void setExitOnCompletion(boolean exitOnCompletion) {
		this.exitOnCompletion = exitOnCompletion;
	}

	protected Resource[] getResourcesFromLocationPattern() {
		try {
			return new PathMatchingResourcePatternResolver().getResources(getLocationPattern());
		} catch (IOException e) {
			throw new IllegalStateException(format("Failed to load locationPattern=%s", getLocationPattern()), e);
		}
	}

	@Bean
	public StepExecutionListener exitListener() {
		return new StepExecutionListener() {
			public void beforeStep(StepExecution stepExecution) {
				log.debug("Starting job={}, step={}", getJobName(), stepExecution.getStepName());
			}

			@Nullable
			public ExitStatus afterStep(StepExecution stepExecution) {
				log.debug("Completed job={}, summary={}", getJobName(), stepExecution.getSummary());
				if (isExitOnCompletion()) {
					log.debug("Exiting job={}", getJobName());
					System.exit(0);
				}
				return ExitStatus.COMPLETED;
			}
		};
	}

}
