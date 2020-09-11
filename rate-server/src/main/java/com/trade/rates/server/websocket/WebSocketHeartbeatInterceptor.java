package com.trade.rates.server.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.AuthenticationException;

/**
 * Used to recod the last heartbeat from a client for use by the WebSocketHeartbeatManager.
 */
@Slf4j
public class WebSocketHeartbeatInterceptor implements ChannelInterceptor {

	private static final String USERNAME_HEADER = "login";

	@Autowired
	private WebSocketHeartbeatManager webSocketHeartbeatManager;

	@Override
	public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {
		if (SimpMessageType.HEARTBEAT.equals(message.getHeaders().get("simpMessageType"))) {
			webSocketHeartbeatManager.heartbeat(message.getHeaders().get("simpSessionId").toString());
		}

		return message;
	}
}
