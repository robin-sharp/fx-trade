package com.trade;

import org.junit.jupiter.api.Test;

import static com.trade.TradeTestData.*;
import static com.trade.util.TestUtil.assertJson;
import static com.trade.util.TestUtil.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BifOfferTest {

	@Test
	public void testConstructor() {
		BidOffer bidOffer = BID_OFFER;
		assertEquals(BID, bidOffer.getBid());
		assertEquals(OFFER, bidOffer.getOffer());
		assertEquals(OFFER.subtract(BID), bidOffer.getSpread());
	}

	@Test
	public void testJson() {
		assertJson(BID_OFFER, BidOffer.class);
	}

	@Test
	public void testBidOfferSerializable() {
		assertEquals(assertSerializable(BID_OFFER), BID_OFFER);
	}

	@Test
	public void testEquals() {
		assertEquals(BID_OFFER, new BidOffer(BID, OFFER));
		assertEquals(111447640, BID_OFFER.hashCode());
	}

	@Test
	public void testToString() {
		assertEquals("BidOffer{bid=1.12345, offer=1.12354}", BID_OFFER.toString());
	}
}
