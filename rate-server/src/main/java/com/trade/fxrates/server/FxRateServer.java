package com.trade.fxrates.server;

import com.trade.EnvProfile;
import org.keycloak.adapters.springboot.KeycloakAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
		scanBasePackages = {
				"com.trade.rates.server",
				"com.trade.fxrates.server"}
		, exclude = {
		KeycloakAutoConfiguration.class,
		CassandraAutoConfiguration.class, CassandraRepositoriesAutoConfiguration.class, CassandraDataAutoConfiguration.class
}
)
@ConfigurationPropertiesScan(basePackages = {
		"com.trade.security.basic",
		"com.trade.rates.server",
		"com.trade.fxrates.server"
})
@EnableScheduling
public class FxRateServer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(FxRateServer.class)
				.profiles(EnvProfile.DEV_PROFILE)
				.run(args);
	}
}