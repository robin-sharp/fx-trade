package com.trade.rates.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

/**
 * A class for managing subscriptions to a RotatingQueue.
 */
public class QueueSubscriptionManager<S extends Subscription> {

	//TODO make this rotate to be fair to consumers
	private Map<String, ConcurrentLinkedQueue<S>> sessions = new ConcurrentHashMap<>();

	/**
	 * Add a new subscription for a topic and
	 * return true if this is the first element to be added.
	 */
	public boolean addSubscription(String topic, String subscriber) {
		requireNonNull(topic, "topic cannot be null");
		requireNonNull(subscriber, "subscriber cannot be null");
		return getQueue(topic).add((S) new Subscription(topic, subscriber));
	}

	/**
	 * Add a new subscription for a topic and
	 * return true if this is the first element to be added.
	 */
	public boolean addSubscription(S subscription) {
		requireNonNull(subscription, "subscription cannot be null");
		return getQueue(subscription.getTopic()).add(subscription);
	}

	/**
	 * Remove All subscriptions for a subscriber and
	 * return true if this is the last element to be removed.
	 */
	public boolean removeSubscription(String topic, String subscriber) {
		requireNonNull(topic, "topic cannot be null");
		requireNonNull(subscriber, "subscriber cannot be null");
		return getQueue(topic).remove(new Subscription(topic, subscriber));
	}

	/**
	 * Remove a particular subscription based on its topics.
	 * and return true if this is the last element to be removed.
	 */
	public boolean removeSubscription(S subscription) {
		requireNonNull(subscription, "subscription cannot be null");
		return getQueue(subscription.getTopic()).remove(subscription);
	}

	/**
	 * Remove All subscriptions for a subscriber.
	 */
	public void removeAllSubscriptions(String subscriber) {
		requireNonNull(subscriber, "subscriber cannot be null");
		sessions.keySet().forEach(topic -> {
			removeSubscription(topic, subscriber);
		});
	}

	/**
	 * Is this topic being subscribed to.
	 */
	public boolean isSubscribed(String topic) {
		requireNonNull(topic, "topic cannot be null");
		return getQueue(topic).size() > 0;
	}

	/**
	 * Perform this action on every topic with subscriptions.
	 */
	public void forEachSubscribedTopic(Consumer<? super String> action) {
		sessions.forEach((k,v) -> {
			if (v.size() > 0) {
				action.accept(k);
			}
		});
	}

	/**
	 * Perform this action on every Subscription across all the topics
	 */
	public void forEverySubscription(Consumer<? super S> action) {
		sessions.values().forEach(queue -> {
			queue.forEach(subscription -> action.accept(subscription));
		});
	}

	/**
	 * Perform this action on Subscription for a topic
	 */
	public void forEachSubscription(String topic, Consumer<? super S> action) {
		requireNonNull(topic, "topic cannot be null");
		getQueue(topic).forEach(action);
	}

	/**
	 * Rotate the subscription queue to enable a fair distribution of rates.
	 */
	public void doAction(String topic, Consumer<? super ConcurrentLinkedQueue<S>> action) {
		requireNonNull(topic, "topic cannot be null");
		action.accept(getQueue(topic));
	}

	ConcurrentLinkedQueue<S> getQueue(String topic) {
		requireNonNull(topic, "topic cannot be null");
		return sessions.computeIfAbsent(topic, k -> new ConcurrentLinkedQueue<>());
	}


}