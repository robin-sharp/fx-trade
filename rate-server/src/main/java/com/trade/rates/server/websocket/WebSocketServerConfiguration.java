package com.trade.rates.server.websocket;

import com.trade.rates.util.QueueSubscriptionManager;
import com.trade.spring.YamlPropertySourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@PropertySource(value = "classpath:server-config/", factory = YamlPropertySourceFactory.class)
@EnableWebSocketMessageBroker
public class WebSocketServerConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp").setAllowedOrigins("*").withSockJS();
		registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {

		// use the /app prefix for incoming WebSocket communication
		config.setApplicationDestinationPrefixes("/app");

		// use the /topic prefix for outgoing WebSocket communication
		config.enableSimpleBroker("/topic").
				setTaskScheduler(heartBeatScheduler());
	}

	@Bean
	public QueueSubscriptionManager<WebSocketSubscription> webSocketSubscriptionManager() {
		return new QueueSubscriptionManager<>();
	}

	@Bean
	public TaskScheduler heartBeatScheduler() {
		return new ThreadPoolTaskScheduler();
	}

	@Bean
	public WebSocketHeartbeatManager heartbeatManager() {
		return new WebSocketHeartbeatManager();
	}

	@Bean
	public WebSocketConnectChannelInterceptor webSocketConnectChannelInterceptor() {
		return new WebSocketConnectChannelInterceptor();
	}

	@Bean
	WebSocketHeartbeatInterceptor webSocketHeartbeatInterceptor() {
		return new WebSocketHeartbeatInterceptor();
	}

	protected void customizeClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(
				webSocketHeartbeatInterceptor(),
				webSocketConnectChannelInterceptor());
	}
}