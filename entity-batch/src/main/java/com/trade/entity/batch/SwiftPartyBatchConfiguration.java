package com.trade.entity.batch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trade.batch.AbstractBatchConfiguration;
import com.trade.entity.EntityStatus;
import com.trade.entity.Party;
import com.trade.entity.PartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

/**
 * Batch job to loadup Swift Parties.
 */

@EnableBatchProcessing
@Configuration
@ConfigurationProperties("batch.swiftparty")
@Slf4j
public class SwiftPartyBatchConfiguration extends AbstractBatchConfiguration {

	@Autowired
	@Qualifier("CassandraPartyService")
	private PartyService partyService;

	@Value("classpath:batch/party/swiftparty/GB.json")
	//@Value("classpath:batch/party/swiftparty/*.json")
	private Resource[] classpathResources;

	private Resource[] batchResources;

	@Bean("SwiftPartyBatchJob")
	public Job readSwiftPartyJob() {
		log.info("Getting job {}", getJobName());
		batchResources = buildBatchResources(classpathResources);
		return jobBuilderFactory
				.get(getJobName())
				.preventRestart()
				.flow(step1())
				.next(exit())
				.end()
				.build();
	}

	@Bean
	public Step step1() {
		log.info("Getting step1");
		return stepBuilderFactory.get("step1").<SwiftParty, Party>chunk(10)
				.reader(multiResourceItemReader())
				.processor(processor())
				.writer(writer())
				.build();
	}

	@Bean
	@StepScope
	public MultiResourceItemReader<SwiftParty> multiResourceItemReader() {
		log.info("Getting multiResourceItemReader");
		MultiResourceItemReader resourceItemReader = new MultiResourceItemReader();
		resourceItemReader.setResources(batchResources);
		resourceItemReader.setDelegate(reader());
		return resourceItemReader;
	}

	public JsonItemReader<SwiftParty> reader() {
		log.info("Getting itemReader");
		return new JsonItemReaderBuilder<SwiftParty>().
				name(getJobName()).
				jsonObjectReader(new JacksonJsonObjectReader(SwiftParty.class)).
				resource(classpathResources[0]). //Fake to get round bug
				build();
	}

	@Bean
	ItemProcessor<SwiftParty, Party> processor() {
		log.info("Getting processor");
		return (sp) -> {
			log.debug("Processing party={}", sp.swift_code);
			return new Party(UUID.randomUUID(),
					sp.swift_code,
					sp.bank + (sp.branch != null ? (" " + sp.branch) : ""),
					sp.countryCode,
					LocalDateTime.now(),
					EntityStatus.CREATED,
					LocalDateTime.now(),
					Collections.emptyList());
		};
	}

	@Bean
	public ItemWriter<Party> writer() {
		log.info("Getting writer");
		return (parties) -> {
			log.debug("Writing parties={}", parties);
			partyService.saveAll(parties);
		};
	}

	/**
	 * Ensure the Resource[] supplies the filtered ArrayBatchInputStream
	 */
	private Resource[] buildBatchResources(Resource[] inputResources) {
		Resource[] outputResources = new Resource[inputResources.length];
		for (int index = 0; index < inputResources.length; index++) {

			Resource resource = inputResources[index];
			outputResources[index] = new ClassPathResource(((ClassPathResource) resource).getPath()) {
				public InputStream getInputStream() throws IOException {
					return new ArrayBatchInputStream(super.getInputStream(),
							"\n,\"countryCode\": \"" + getFilename().substring(0, getFilename().indexOf(".")) + "\"");
				}
			};
		}

		return outputResources;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class SwiftParty {
		@JsonProperty("swift_code")
		private String swift_code;
		@JsonProperty("bank")
		private String bank;
		@JsonProperty("branch")
		private String branch;
		@JsonProperty("countryCode")
		private String countryCode;
	}

	/**
	 * Stream to filter in the array part and insert countryCode json at the end of every json object
	 */
	public static class ArrayBatchInputStream extends InputStream {
		private final InputStream is;
		private final String jsonInsert;
		private boolean inArray;
		private boolean inInsert;
		private int jsonInsertCount;

		public ArrayBatchInputStream(InputStream is, String jsonInsert) {
			this.is = is;
			this.jsonInsert = jsonInsert;
		}

		@Override
		public int read() throws IOException {

			if (inInsert) {
				if (jsonInsertCount == jsonInsert.length()) {
					inInsert = false;
					jsonInsertCount = 0;
					return '}';
				} else {
					return jsonInsert.charAt(jsonInsertCount++);
				}
			}

			int c;
			while ((c = is.read()) != -1) {
				if (!inArray) {
					if (c != '[') {
						continue;
					}
					inArray = true;
					return c;
				}

				if (c == ']') {
					inArray = false;
					return c;
				}

				if (c == '}') {
					inInsert = true;
					return '\n';
				}
				return c;
			}

			return -1;
		}
	}
}