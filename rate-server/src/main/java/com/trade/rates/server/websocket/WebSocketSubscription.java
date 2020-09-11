package com.trade.rates.server.websocket;

import com.trade.rates.util.Subscription;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class that extends Subscription to include the clientSessionId, and serverSessionId, so that messages
 * can be sent back to clients.
 * <p>
 * Note: Equaity is set on the clientSessionId, as there can onlybe one principal per clientSession, but
 * a principal might have multiple sessions.
 */
public class WebSocketSubscription extends Subscription {

	private final Map<String, Object> headers = new HashMap();
	private String clientSessionId;
	private String serverSessionId;

	public WebSocketSubscription(String topic, String principal, String clientSessionId, String serverSessionId) {
		super(topic, principal);
		this.clientSessionId = clientSessionId;
		this.serverSessionId = serverSessionId;
		headers.put("", "");
	}

	public String getClientSessionId() {
		return clientSessionId;
	}

	public String getServerSessionId() {
		return serverSessionId;
	}

	public Map<String, Object> getHeaders() {
		return headers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		WebSocketSubscription that = (WebSocketSubscription) o;
		return Objects.equals(getTopic(), that.getTopic()) &&
				Objects.equals(clientSessionId, that.clientSessionId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTopic(), clientSessionId);
	}

	@Override
	public String toString() {
		return new StringBuilder("WebSocketSubscription{")
				.append("topic=").append(getTopic())
				.append(", principal=").append(getSubscriber())
				.append(", clientSessionId=").append(clientSessionId)
				.append(", serverSessionId=").append(serverSessionId)
				.append(", headers=").append(headers)
				.append('}').toString();
	}
}