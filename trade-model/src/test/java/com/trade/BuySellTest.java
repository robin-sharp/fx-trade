package com.trade;

import org.junit.jupiter.api.Test;

import static com.trade.BuySell.*;
import static com.trade.TradeTestData.BUY_SELL_B;
import static com.trade.TradeTestData.BUY_SELL_S;
import static com.trade.util.TestUtil.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BuySellTest {

	@Test
	public void testBuySellConstructor() {
		assertEquals(BUY_SELL_B, BUY.getShortcut());
		assertEquals(BUY_SELL_S, SELL.getShortcut());
	}

	@Test
	public void testShortcut() {
		assertEquals(BUY, getBuySell(BUY_SELL_B));
		assertEquals(SELL, getBuySell(BUY_SELL_S));
		assertNull(getBuySell(""));
	}

	@Test
	public void testBuySellSerializable() {
		assertEquals(assertSerializable(BUY), BUY);
	}
}