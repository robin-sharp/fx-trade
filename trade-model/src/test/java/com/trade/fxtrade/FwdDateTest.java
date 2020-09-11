package com.trade.fxtrade;

import com.trade.fwd.FwdDate;
import org.junit.jupiter.api.Test;

import static com.trade.fwd.FwdDate.ONE_WEEK;
import static com.trade.fwd.FwdDate.SPOT;
import static com.trade.util.TestUtil.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FwdDateTest {
	@Test
	public void testFwdDateGetDate() {
		assertEquals(SPOT, FwdDate.getDate("SPOT"));
		assertEquals(ONE_WEEK, FwdDate.getDate("1W"));
	}

	@Test
	public void testFwdDateSerializable() {
		assertEquals(assertSerializable(SPOT), SPOT);
	}
}