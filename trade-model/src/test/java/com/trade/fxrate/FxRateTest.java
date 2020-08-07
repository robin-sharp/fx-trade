package com.trade.fxrate;

import com.trade.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.trade.CurrencyTestData.GBP_USD;
import static com.trade.TradeTestData.*;
import static com.trade.fxrate.TestFxRateData.*;
import static com.trade.fxtrade.FxDate.SPOT;
import static com.trade.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class FxRateTest {

	@Test
	public void testFxRateConstructor() {
		FxRate fxRate = FX_SPOT_RATE;
		assertEquals(GBP_USD, fxRate.getCurrencyPair());
		assertEquals(SPOT, fxRate.getDate());
		assertEquals(STALE, fxRate.isStale());

		assertNull(fxRate.getRate(ONE_MILLION.subtract(ONE_HUNDREDTH)));
		assertEqual(ONE_DOT_FIVE, fxRate.getRate(ONE_MILLION));
		assertEqual(ONE_DOT_FIVE, fxRate.getRate(ONE_MILLION.add(ONE_HUNDREDTH)));

		assertEqual(ONE_DOT_FIVE, fxRate.getRate(TWO_MILLION.subtract(ONE_HUNDREDTH)));
		assertEqual(ONE_DOT_FOUR, fxRate.getRate(TWO_MILLION));
		assertEqual(ONE_DOT_FOUR, fxRate.getRate(TWO_MILLION.add(ONE_HUNDREDTH)));

		assertEqual(ONE_DOT_TWO, fxRate.getRate(FIVE_MILLION.subtract(ONE_HUNDREDTH)));
		assertEqual(ONE_DOT_ONE, fxRate.getRate(FIVE_MILLION));
		assertEqual(ONE_DOT_ONE, fxRate.getRate(FIVE_MILLION.add(ONE_HUNDREDTH)));
		assertEqual(ONE_DOT_ONE, fxRate.getRate(TEN_MILLION));
	}

	@Test
	public void testPartyEquals() {
		assertEquals(FX_SPOT_RATE, FX_SPOT_RATE);
		assertNotEquals(FX_SPOT_RATE, FX_FWD_RATE);
		assertNotEquals(FX_FWD_RATE, FX_SPOT_RATE);
	}

	@Test
	public void testHashCode() {
		assertEquals(Objects.hash(FX_SPOT_RATE.getCurrencyPair(), FX_SPOT_RATE.getDate()), FX_SPOT_RATE.hashCode());
	}

	@Test
	public void testFxRateSerializable() {
		assertEquals(assertSerializable(FX_SPOT_RATE), FX_SPOT_RATE);
	}

	@Test
	public void testJson() {
		assertJson(FX_SPOT_RATE, FxRate.class);
	}

	@Test
	public void validate() {
		TestUtil.assertValid(FX_SPOT_RATE);
	}
}