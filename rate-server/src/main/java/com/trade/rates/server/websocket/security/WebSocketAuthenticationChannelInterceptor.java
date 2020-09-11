package com.trade.rates.server.websocket.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


/**
 * Note that: preSend() MUST return a UsernamePasswordAuthenticationToken, another element in the spring
 * security chain test this. Note that: If your UsernamePasswordAuthenticationToken was built without
 * passing GrantedAuthority, the authentication will fail, because the constructor without granted
 * authorities auto set authenticated = false THIS IS AN IMPORTANT DETAIL which is not documented in
 * spring-security.
 */
@Component
@Slf4j
public class WebSocketAuthenticationChannelInterceptor implements ChannelInterceptor {

	private static final String USERNAME_HEADER = "login";
	private static final String PASSWORD_HEADER = "passcode";

	private final WebSocketAuthenticationService webSocketAuthenticationService;

	@Autowired
	public WebSocketAuthenticationChannelInterceptor(final WebSocketAuthenticationService webSocketAuthenticationService) {
		this.webSocketAuthenticationService = webSocketAuthenticationService;
	}

	@Override
	public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
		final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if (StompCommand.CONNECT == accessor.getCommand()) {
			final String username = accessor.getFirstNativeHeader(USERNAME_HEADER);
			final String password = accessor.getFirstNativeHeader(PASSWORD_HEADER);

			final UsernamePasswordAuthenticationToken user = webSocketAuthenticationService.getAuthenticatedOrFail(username, password);

			accessor.setUser(user);
		}
		return message;
	}
}