package com.trade.security.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecureMethodConfiguration {

	@Bean
	public SecureMethodStore getSecureServiceStore() {
		return new SecureMethodStore();
	}

	@Bean
	public SecureMethodAuthorisationService getSecureServiceContext() {
		return new SecureMethodAuthorisationService();
	}
}
