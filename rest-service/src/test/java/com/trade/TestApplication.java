package com.trade;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {CassandraAutoConfiguration.class, CassandraDataAutoConfiguration.class})

public class TestApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TestApplication.class)
				.profiles(EnvProfile.TEST_PROFILE)
				.run(args);
	}
}
