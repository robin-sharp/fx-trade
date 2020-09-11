package com.trade.rates.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import static com.trade.fxrates.FxRateHeaders.FX_TOPIC;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * QueueSubscriptionManager that allows other components to listen to changes to subscriptions.
 */

@Slf4j
public class SubscribableQueueSubscriptionManager<S extends Subscription> extends QueueSubscriptionManager<S> implements MessageProducer {

	private MessageChannel outputChannel;
	private boolean sendFirstOrLast;

	private boolean getSendFirstOrLast() {
		return sendFirstOrLast;
	}

	/**
	 * Only send messages on the first or last subscription
	 *
	 * @param sendFirstOrLast
	 */
	private void setSendFirstOrLast(boolean sendFirstOrLast) {
		this.sendFirstOrLast = sendFirstOrLast;
	}

	public MessageChannel getOutputChannel() {
		return outputChannel;
	}

	public void setOutputChannel(MessageChannel outputChannel) {
		this.outputChannel = outputChannel;
	}

	public boolean addSubscription(S subscription) {
		requireNonNull(subscription, "subscription cannot be null");
		return send(subscription, super.addSubscription(subscription), true);
	}

	public boolean removeSubscription(S subscription) {
		requireNonNull(subscription, "subscription cannot be null");
		return send(subscription, super.addSubscription(subscription), true);
	}

	boolean send(S subscription, boolean firstOrLast, boolean subscribe) {
		if (sendFirstOrLast && !firstOrLast) {
			return firstOrLast;
		}

		try {
			final Message<? extends Subscription> message = MessageBuilder.
					withPayload(subscription).
					setHeader(FX_TOPIC, subscription.getTopic()).
					build();
			getOutputChannel().send(message);
			log.debug("Sent subscription={}", subscription);
		} catch (Throwable t) {
			log.error(format("Failed to send subscription=%s", subscription), t);
		}

		return firstOrLast;
	}
}
