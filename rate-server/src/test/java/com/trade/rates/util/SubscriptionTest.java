package com.trade.rates.util;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.rates.RatesTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Tag(UNIT_TEST)
class SubscriptionTest {

	@Test
	public void testConstructor() {
		Subscription subscription = new Subscription(TOPIC_GBPUSD_SPOT, FX_SUBSCRIBER);

		assertEquals(TOPIC_GBPUSD_SPOT, subscription.getTopic());
		assertEquals(FX_SUBSCRIBER, subscription.getSubscriber());
	}

	@Test
	public void testEquality() {
		Subscription subscription = new Subscription(TOPIC_GBPUSD_SPOT, FX_SUBSCRIBER);

		assertEquals(FX_GBPUSD_SUBSCRIPTION, subscription);
		assertNotEquals(OIL_GBPUSD_SUBSCRIPTION, subscription);

		assertEquals(990523476, FX_SUBSCRIBER.hashCode());
	}

	@Test
	public void testToString() {
		Subscription subscription = new Subscription(TOPIC_GBPUSD_SPOT, FX_SUBSCRIBER);
		assertEquals("Subscription{topic=GBPUSD-SPOT, subscriber=soros.fxtrader}", subscription.toString());
	}
}