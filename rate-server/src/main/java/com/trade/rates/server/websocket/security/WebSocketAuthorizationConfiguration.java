package com.trade.rates.server.websocket.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketAuthorizationConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {
	@Override
	protected void configureInbound(final MessageSecurityMetadataSourceRegistry messages) {
		// Write code to customise authorisation from keycloak
		messages.anyMessage().authenticated();
	}

	// TODO: For test purpose (and simplicity) CSRF is disabled, but you should re-enable this and provide a CRSF endpoint.
	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}
}
