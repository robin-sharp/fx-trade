package com.trade;

import org.junit.jupiter.api.Test;

import static com.trade.Spread.ADDITION;
import static com.trade.TradeTestData.*;
import static com.trade.util.TestUtil.assertJson;
import static com.trade.util.TestUtil.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SpreadTest {

	@Test
	public void testConstructor1() {
		Spread spread = new Spread(ONE);
		assertEquals(ONE, spread.getBidSpread());
		assertEquals(ONE, spread.getOfferSpread());
		assertEquals(ADDITION, spread.getSpreadType());
	}

	@Test
	public void testConstructor2() {
		Spread spread = new Spread(ONE, TWO, ADDITION);
		assertEquals(ONE, spread.getBidSpread());
		assertEquals(TWO, spread.getOfferSpread());
		assertEquals(ADDITION, spread.getSpreadType());
	}

	@Test
	public void testNewBidOffer() {
		Spread spread = new Spread(ONE, TWO, ADDITION);
		BidOffer bidOffer = new BidOffer(ZERO, ZERO);
		BidOffer newBidOffer = spread.newBidOffer(bidOffer);
		assertEquals(ZERO.subtract(ONE), newBidOffer.getBid());
		assertEquals(ZERO.add(TWO), newBidOffer.getOffer());
	}

	@Test
	public void testJson() {
		assertJson(new Spread(ONE), Spread.class);
	}

	@Test
	public void testSpreadSerializable() {
		assertEquals(assertSerializable(new Spread(ONE)), new Spread(ONE));
	}

	@Test
	public void testEquals() {
		Spread spread12a = new Spread(ONE, TWO);
		Spread spread12b = new Spread(ONE, TWO);
		assertEquals(spread12a, spread12b);
		assertEquals(spread12b, spread12a);

		Spread spread11 = new Spread(ONE, ONE);
		assertNotEquals(spread12a, spread11);
		assertNotEquals(spread11, spread12a);

		assertEquals(1984, spread12a.hashCode());
		assertEquals(spread12a.hashCode(), spread12a.hashCode());
		assertNotEquals(spread11.hashCode(), spread12a.hashCode());
	}

	@Test
	public void testToString() {
		assertEquals("Spread{bidSpread=1, offerSpread=2, spreadType=0}", new Spread(ONE, TWO).toString());
	}
}
