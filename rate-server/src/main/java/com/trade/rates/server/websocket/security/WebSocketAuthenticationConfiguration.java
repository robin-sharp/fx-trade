package com.trade.rates.server.websocket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketAuthenticationConfiguration implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private WebSocketAuthenticationChannelInterceptor webSocketAuthenticationChannelInterceptor;

	@Override
	public void registerStompEndpoints(final StompEndpointRegistry registry) {
		// Endpoints are already registered on WebSocketServerConfiguration, no need to add more.
	}

	@Override
	public void configureClientInboundChannel(final ChannelRegistration registration) {
		registration.interceptors(webSocketAuthenticationChannelInterceptor);
	}
}
