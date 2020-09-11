package com.trade.fxrates.client.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

@EnableRetry
@Slf4j
public class WebSocketFxRateClient {

	private final List<String> subscriptions = new CopyOnWriteArrayList<>();

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;

	@Autowired
	private WebSocketStompClient stompClient;

	@Value("${wsurl}")
	private String url;

	private String login;
	private StompSession stompSession;
	private String clientSessionId;

	private CountDownLatch waitLatch;

	@Retryable(value = {Exception.class},
			maxAttempts = 3,
			backoff = @Backoff(delay = 1000, multiplier = 2, random = true))
	public void connect(String login, String passcode) {
		try {
			stompClient.setMessageConverter(new StringMessageConverter());
			stompClient.setTaskScheduler(taskScheduler);

			log.info("connecting to url={} with login={}", url, login);
			this.login = login;
			StompHeaders connectHeaders = new StompHeaders();
			connectHeaders.add("login", login);
			connectHeaders.add("passcode", passcode);

			waitLatch = new CountDownLatch(1);
			stompClient.connect(url, new WebSocketHttpHeaders(), connectHeaders, new FxRatesSessionHandler()).get();
			waitLatch.await(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new IllegalStateException(format("Failed to start stompSession to %s", url), e);
		}
	}

	public boolean isConnected() {
		return stompSession != null && stompSession.isConnected();
	}

	public void disconnect() {
		try {
			unsubscribeAll();
			log.info("disconnecting from url={} with login={}", url, login);
			stompSession.disconnect();
			stompSession = null;
		} catch (Exception e) {
			throw new IllegalStateException(format("Failed to disconnect stompSession from %s", url), e);
		}
	}

	public void subscribe(String topic) {
		if (stompSession == null) {
			throw new IllegalStateException("Cannot subscribe until after a connection is made");
		}
		StompHeaders headers = new StompHeaders();
		headers.setDestination("/app/fxrates/subscribe");
		headers.add("clientSessionId", clientSessionId);
		stompSession.send(headers, topic);
		log.info("fxrates/subscribe topic={}, clientSessionId={} sent to websocket server", topic, clientSessionId);
		subscriptions.add(topic);
	}

	public void unsubscribe(String topic) {
		if (stompSession == null) {
			throw new IllegalStateException("Cannot unsubscribe until after a connection is made");
		}
		StompHeaders headers = new StompHeaders();
		headers.setDestination("/app/fxrates/unsubscribe");
		headers.add("clientSessionId", clientSessionId);
		stompSession.send(headers, topic);
		log.info("fxrates/unsubscribe topic={}, clientSessionId={} sent to websocket server", topic, clientSessionId);
		subscriptions.remove(topic);
	}

	public void unsubscribeAll() {
		subscriptions.forEach(topic -> unsubscribe(topic));
	}

	@Override
	public String toString() {
		return new StringBuilder("WebSocketFxRateClient{").
				append("url=").append(url).
				append(", connected=").append(isConnected()).
				append("}").toString();
	}

	class FxRatesSessionHandler implements StompSessionHandler {

		@Override
		public Type getPayloadType(StompHeaders headers) {
			return String.class;
		}

		@Override
		public void handleFrame(StompHeaders headers, @Nullable Object payload) {
			log.info("handleFrame received payload={}  headers={} ", payload, headers);
		}

		@Override
		public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
			WebSocketFxRateClient.this.stompSession = session;
			WebSocketFxRateClient.this.clientSessionId = session.getSessionId();

			log.info("afterConnected new Session clientSessionId={}", clientSessionId);

			session.subscribe("/topic/rates/fxrates/" + clientSessionId, this);
			log.info("Subscribed to /topic/rates/fxrates/{}", clientSessionId);

			waitLatch.countDown();
		}

		@Override
		public void handleException(StompSession session, @Nullable StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
			log.error(format("Failed with exception command=%s, headers=%s, payload=%s",
					command, headers, session.getSessionId(), new String(payload)), exception);
		}

		@Override
		public void handleTransportError(StompSession session, Throwable exception) {
			log.error(format("Failed with transportError clientSessionId=%s", session.getSessionId()), exception);
		}
	}
}