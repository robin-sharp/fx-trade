package com.trade.entity;

import com.trade.EnvProfile;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Note: Set Environment Variable SPRING_PROFILES_ACTIVE=dev
 */

@SpringBootApplication(
		scanBasePackages = {
				"com.trade.entity.cassandra",
				"com.trade.entity.cache",
				"com.trade.entity.controller",
				"com.trade.security.oauth2"
		})
//,exclude = {CassandraAutoConfiguration.class, CassandraRepositoriesAutoConfiguration.class, CassandraDataAutoConfiguration.class})

//"com.trade.entity.restclient",
@ConfigurationPropertiesScan(basePackages = {
		"com.trade.security.oauth2",
		"com.trade.entity.cassandra",
		"com.trade.entity.controller"
})
public class EntityServer {
	public static void main(String[] args) {
		new SpringApplicationBuilder(EntityServer.class)
				.profiles(EnvProfile.DEV_PROFILE)
				.run(args);
	}
}
