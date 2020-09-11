package com.trade.fxrates.client.websocket;

import com.trade.spring.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "wsserver.fxrates")
@PropertySource(value = "classpath:client-config/", factory = YamlPropertySourceFactory.class)
public class WebSocketClientConfiguration {

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(2);
		threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		return threadPoolTaskScheduler;
	}

	@Bean
	public WebSocketClient getWebSocketClient() {
		StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();

		Map<String, Object> userProperties = new HashMap<>();
		userProperties.put("org.apache.tomcat.websocket.WS_AUTHENTICATION_USER_NAME", "user");
		userProperties.put("org.apache.tomcat.websocket.WS_AUTHENTICATION_PASSWORD", "password");
		standardWebSocketClient.setUserProperties(userProperties);

		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(standardWebSocketClient));
		return new SockJsClient(transports);
	}

	@Bean
	public WebSocketStompClient getWebSocketStompClient(WebSocketClient webSocketClient) {
		return new WebSocketStompClient(webSocketClient);
	}

	@Bean
	public WebSocketFxRateClient getWebSocketFxRateClient() {
		return new WebSocketFxRateClient();
	}
}