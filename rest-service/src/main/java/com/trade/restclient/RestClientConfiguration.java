package com.trade.restclient;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotEmpty;

@Validated
@Configuration
public class RestClientConfiguration {

	@NotEmpty
	private String uri;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.
				rootUri(uri).
				build();
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
