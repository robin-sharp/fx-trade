package com.trade.fxrates.client;

import com.trade.EnvProfile;
import com.trade.fxrates.client.websocket.WebSocketFxRateClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication(
		scanBasePackages = {
				"com.trade.fxrates.client"}
		, exclude = {
		CassandraAutoConfiguration.class, CassandraRepositoriesAutoConfiguration.class, CassandraDataAutoConfiguration.class}
)
@ConfigurationPropertiesScan(basePackages = {
		"com.trade.fxrates.client"
})

@Slf4j
public class FxRateClient {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(FxRateClient.class)
				.web(WebApplicationType.NONE)
				.profiles(EnvProfile.DEV_PROFILE)
				.run(args);

		WebSocketFxRateClient webSocketFxRateClient = context.getBean(WebSocketFxRateClient.class);
		webSocketFxRateClient.connect("test1", "test");
		TimeUnit.SECONDS.sleep(2);
		webSocketFxRateClient.subscribe("GBPCAD/SPOT");
		webSocketFxRateClient.subscribe("GBPUSD/SPOT");
		webSocketFxRateClient.subscribe("EURUSD/SPOT");

		log.info("{}", webSocketFxRateClient);

		//Sleep while the rates come in the background
		TimeUnit.SECONDS.sleep(20);

		//Enable to simulate a kill before unsubscribing
		//System.exit(0);

		//Disable to simulate being a bad citizen
		webSocketFxRateClient.unsubscribe("GBPCAD/SPOT");
		webSocketFxRateClient.unsubscribe("GBPUSD/SPOT");
		webSocketFxRateClient.unsubscribe("EURUSD/SPOT");

		//Disable to simulate being a bad citizen
		webSocketFxRateClient.disconnect();
		System.exit(0);
	}
}
