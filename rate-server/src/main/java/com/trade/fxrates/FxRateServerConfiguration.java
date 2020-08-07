package com.trade.fxrates;

import com.trade.fxrates.publisher.FxRatePublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FxRateServerConfiguration {

	@Bean
	public FxRatePublisher getFxRatePublisher() {
		return null;
	}
}
