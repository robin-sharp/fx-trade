package com.trade.rates.server.websocket;

import com.trade.Rate;
import com.trade.rates.util.QueueSubscriptionManager;
import com.trade.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static java.lang.String.format;

@Slf4j
public class WebSocketRatePublisher<T extends Rate> {

	private final String topic;

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private QueueSubscriptionManager<WebSocketSubscription> subscriptionManager;

	public WebSocketRatePublisher(String topic) {
		this(topic, WebSocketRatePublisher.class.getSimpleName());
	}

	public WebSocketRatePublisher(String topic, String name) {
		this.topic = topic;
	}

	public boolean send(T rate) {
		log.debug("Send rate={}", rate);
		try {
			final String jsonRate = JsonUtil.toJson(rate);
			subscriptionManager.forEachSubscription(rate.getSource(), subscription -> sendToSession(subscription, jsonRate));
		} catch (Exception e) {
			log.error(format("Failed to send rate=%s", rate), e);
			return false;
		}
		return true;
	}

	boolean sendToSession(WebSocketSubscription subscription, String jsonRate) {
		log.debug("Send subscription={}, jsonRate={}", subscription, jsonRate);
		try {
			template.convertAndSend(topic + subscription.getClientSessionId(), jsonRate);
		} catch (Throwable e) {
			log.error(format("Failed to send jsonRate=%s to subscription=%s ", jsonRate, subscription), e);
			return false;
		}
		return true;
	}

	public MessageHandler directHandler() {
		return message -> send((T) message.getPayload());
	}
}
