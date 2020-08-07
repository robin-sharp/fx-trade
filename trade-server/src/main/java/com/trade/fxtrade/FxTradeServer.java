package com.trade.fxtrade;

import com.trade.EnvProfile;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Note: Set Environment Variable SPRING_PROFILES_ACTIVE=dev
 */

@SpringBootApplication(scanBasePackages = {
		"com.trade.fxtrade.controller",
		"com.trade.fxtrade.cache",
		"com.trade.fxtrade.cassandra",
		"com.trade.security.oauth2"
})
@ConfigurationPropertiesScan(basePackages = {
		"com.trade.fxtrade.cassandra",
		"com.trade.fxtrade.controller"
})
public class FxTradeServer {
	public static void main(String[] args) {
		new SpringApplicationBuilder(FxTradeServer.class)
				.profiles(EnvProfile.DEV_PROFILE)
				.run(args);
		;
	}
}
