package com.trade.rates.server.websocket;

import com.trade.rates.util.QueueSubscriptionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.AuthenticationException;

/**
 * Interceptor to clear out any subscrptions on disconnect, if the user has been a bad citizen and not unsubscribed.
 */
@Slf4j
public class WebSocketConnectChannelInterceptor implements ChannelInterceptor {

	private static final String USERNAME_HEADER = "login";

	@Autowired
	private QueueSubscriptionManager<WebSocketSubscription> subscriptionManager;

	@Override
	public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
		final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if (StompCommand.CONNECT == accessor.getCommand() || StompCommand.DISCONNECT == accessor.getCommand()) {
			final String username = accessor.getFirstNativeHeader(USERNAME_HEADER);
			log.debug("preSend command={} username={}", accessor.getCommand(), username);
			if (username != null) {
				subscriptionManager.removeAllSubscriptions(username);
			}
		}

		return message;
	}
}
