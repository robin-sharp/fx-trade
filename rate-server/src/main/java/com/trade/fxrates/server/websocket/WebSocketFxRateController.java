package com.trade.fxrates.server.websocket;

import com.trade.rates.server.websocket.WebSocketSubscription;
import com.trade.rates.util.QueueSubscriptionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Rest endpoint to enable a websocket client to subscribe by sending the topic and clientSessionId.
 */
@Controller
@Slf4j
public class WebSocketFxRateController {

	@Autowired
	private QueueSubscriptionManager<WebSocketSubscription> subscriptionManager;

	@MessageMapping("/fxrates/subscribe")
	public void subscribe(String topic, Principal principal,
						  @Header("clientSessionId") String clientSessionId,
						  @Header("simpSessionId") String serverSessionId) {
		WebSocketSubscription subscription = new WebSocketSubscription(topic, principal.getName(), clientSessionId, serverSessionId);
		log.info("subscribe subscription={}", subscription);
		subscriptionManager.addSubscription(subscription);
	}

	@MessageMapping("/fxrates/unsubscribe")
	public void unsubscribe(String topic, Principal principal,
							@Header("clientSessionId") String clientSessionId,
							@Header("simpSessionId") String serverSessionId) {
		WebSocketSubscription subscription = new WebSocketSubscription(topic, principal.getName(), clientSessionId, serverSessionId);
		log.info("unsubscribe subscription={}", subscription);
		subscriptionManager.removeSubscription(subscription);
	}
}
