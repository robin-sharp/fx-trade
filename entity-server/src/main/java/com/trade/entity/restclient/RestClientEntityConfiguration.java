package com.trade.entity.restclient;

import com.trade.restclient.RestClientConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("restclient.entity")
public class RestClientEntityConfiguration extends RestClientConfiguration {
}