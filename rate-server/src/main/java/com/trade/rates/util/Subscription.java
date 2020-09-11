package com.trade.rates.util;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Subscription to a topic by a subscriber.
 */
public class Subscription {
	private final String topic;
	private final String subscriber;

	public Subscription(String topic, String subscriber) {
		requireNonNull(topic, "topic cannot be null");
		requireNonNull(subscriber, "subscriber cannot be null");
		this.topic = topic;
		this.subscriber = subscriber;
	}

	public String getTopic() {
		return topic;
	}

	public String getSubscriber() {
		return subscriber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Subscription that = (Subscription) o;
		return Objects.equals(topic, that.topic) &&
				Objects.equals(subscriber, that.subscriber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(topic, topic);
	}

	@Override
	public String toString() {
		return new StringBuilder("Subscription{")
				.append("topic=").append(topic)
				.append(", subscriber=").append(subscriber)
				.append('}').toString();
	}
}