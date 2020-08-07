package com.trade;

import org.junit.jupiter.api.Test;

import static com.trade.TradeStatus.*;
import static com.trade.TradeTestData.*;
import static com.trade.util.TestUtil.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TradeStatusTest {

	@Test
	public void testBuySellConstructor() {
		assertEquals(TRADE_STATUS_E, EXECUTED.getShortcut());
		assertEquals(TRADE_STATUS_A, ACCEPTED.getShortcut());
		assertEquals(TRADE_STATUS_F, FAILED.getShortcut());
		assertEquals(TRADE_STATUS_C, CANCELLED.getShortcut());
		assertEquals(TRADE_STATUS_S, SUCCESS.getShortcut());
	}

	@Test
	public void testShortcut() {
		assertEquals(EXECUTED, getTradeStatus(TRADE_STATUS_E));
		assertEquals(ACCEPTED, getTradeStatus(TRADE_STATUS_A));
		assertEquals(FAILED, getTradeStatus(TRADE_STATUS_F));
		assertEquals(CANCELLED, getTradeStatus(TRADE_STATUS_C));
		assertEquals(SUCCESS, getTradeStatus(TRADE_STATUS_S));
	}

	@Test
	public void testTradeStatusSerializable() {
		assertEquals(assertSerializable(EXECUTED), EXECUTED);
	}
}