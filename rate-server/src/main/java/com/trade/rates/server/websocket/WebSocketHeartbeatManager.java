package com.trade.rates.server.websocket;

import com.trade.rates.util.QueueSubscriptionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketHeartbeatManager checks heartbeats and will unsubscribe if the client has not sent a heartbeat with a set time.
 */
@Slf4j
public class WebSocketHeartbeatManager {

	private final Map<String, Long> heartbeatTimes = new ConcurrentHashMap<>();
	private final long heartbeatCheck;
	private final long heartbeatTimeout;

	@Autowired
	private TaskScheduler taskScheduler;

	@Autowired
	private QueueSubscriptionManager<WebSocketSubscription> subscriptionManager;

	public WebSocketHeartbeatManager() {
		this(10000, 25000);
	}

	public WebSocketHeartbeatManager(long heartbeatCheck, long heartbeatTimeout) {
		this.heartbeatCheck = heartbeatCheck;
		this.heartbeatTimeout = heartbeatTimeout;
	}

	@PostConstruct
	public void start() {
		log.info("Starting WebSocketHeartbeatManager every heartbeatCheck={} for heartbeatTimeout={}", heartbeatCheck, heartbeatTimeout);
		taskScheduler.scheduleWithFixedDelay(() -> checkHeartbeatTimeouts(), heartbeatCheck);
	}

	public long getHeartbeatCheck() {
		return heartbeatCheck;
	}

	public long getHeartbeatTimeout() {
		return heartbeatTimeout;
	}

	public void heartbeat(String serverSessionId) {
		heartbeatTimes.put(serverSessionId, System.currentTimeMillis());
	}

	public void checkHeartbeatTimeouts() {
		log.info("Checking heartbeat timeouts on sessions={}", heartbeatTimes.size());
		final long now = System.currentTimeMillis();
		heartbeatTimes.forEach((k,v) -> {
			if (now - v > heartbeatTimeout) {
				log.info("Removing all subscriptions for {}", k);
				removeSubscriptions(k);
				heartbeatTimes.remove(k);
			}
		});
	}

	void removeSubscriptions(String serverSessionId) {
		subscriptionManager.forEverySubscription(
				subscription -> {
					if (serverSessionId.equals(subscription.getServerSessionId())) {
						subscriptionManager.removeSubscription(subscription);
					}
				});
	}

	long getLastHeartbeat(String serverSessionId) {
		return heartbeatTimes.get(serverSessionId);
	}
}