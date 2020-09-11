package com.trade.fxtrade;

import org.junit.jupiter.api.Test;

import static com.trade.fxtrade.FxDate.ONE_WEEK;
import static com.trade.fxtrade.FxDate.SPOT;
import static com.trade.util.TestUtil.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FxDateTest {
	@Test
	public void testFxDateGetDate() {
		assertEquals(SPOT, FxDate.getDate("SPOT"));
		assertEquals(ONE_WEEK, FxDate.getDate("1W"));
	}

	@Test
	public void testFxDateSerializable() {
		assertEquals(assertSerializable(SPOT), SPOT);
	}
}