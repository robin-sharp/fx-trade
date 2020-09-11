package com.trade.rates;

import com.trade.Spread;
import com.trade.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.trade.CurrencyTestData.GBP_USD;
import static com.trade.TradeTestData.*;
import static com.trade.fxtrade.FxDate.SPOT;
import static com.trade.rates.TestFxRateData.*;
import static com.trade.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FxRateTest {

	@Test
	public void testFxRateConstructorWithRate() {
		FxRate FX_SPOT_RATE = new FxRate(GBP_USD, SPOT, TIMESTAMP, STALE, BID_OFFER);
		assertEquals(GBP_USD, FX_SPOT_RATE.getCurrencyPair());
		assertEquals(SPOT, FX_SPOT_RATE.getFxDate());
		assertEquals(STALE, FX_SPOT_RATE.isStale());
		assertEquals(BID_OFFER, FX_SPOT_RATE.getRate(BigDecimal.ONE));
		assertEquals(BID_OFFER, FX_SPOT_RATE.getRate(BigDecimal.TEN));
	}

	@Test
	public void testFxRateConstructorWithStructuredRates() {
		FxRate fxRate = FX_SPOT_RATE;
		assertEquals(GBP_USD, fxRate.getCurrencyPair());
		assertEquals(SPOT, fxRate.getFxDate());
		assertEquals(STALE, fxRate.isStale());

		assertEqual(ONE_DOT_FIVE, fxRate.getRate(ONE_MILLION).getBid());
		assertEqual(ONE_DOT_FIVE, fxRate.getRate(ONE_MILLION.add(ONE_HUNDREDTH)).getBid());

		assertEqual(ONE_DOT_FIVE, fxRate.getRate(TWO_MILLION.subtract(ONE_HUNDREDTH)).getBid());
		assertEqual(ONE_DOT_FOUR, fxRate.getRate(TWO_MILLION).getBid());
		assertEqual(ONE_DOT_FOUR, fxRate.getRate(TWO_MILLION.add(ONE_HUNDREDTH)).getBid());

		assertEqual(ONE_DOT_FOUR, fxRate.getRate(THREE_MILLION.subtract(ONE_HUNDREDTH)).getBid());
		assertEqual(ONE_DOT_THREE, fxRate.getRate(THREE_MILLION).getBid());
		assertEqual(ONE_DOT_THREE, fxRate.getRate(THREE_MILLION.add(ONE_HUNDREDTH)).getBid());

		assertEqual(ONE_DOT_THREE, fxRate.getRate(FOUR_MILLION.subtract(ONE_HUNDREDTH)).getBid());
		assertEqual(ONE_DOT_TWO, fxRate.getRate(FOUR_MILLION).getBid());
		assertEqual(ONE_DOT_TWO, fxRate.getRate(FOUR_MILLION.add(ONE_HUNDREDTH)).getBid());

		assertEqual(ONE_DOT_TWO, fxRate.getRate(FIVE_MILLION.subtract(ONE_HUNDREDTH)).getBid());
		assertEqual(ONE_DOT_ONE, fxRate.getRate(FIVE_MILLION).getBid());
		assertEqual(ONE_DOT_ONE, fxRate.getRate(FIVE_MILLION.add(ONE_HUNDREDTH)).getBid());
		assertEqual(ONE_DOT_ONE, fxRate.getRate(TEN_MILLION).getBid());
	}

	@Test
	public void testSpread() {
		SortedMap<BigDecimal, Spread> spreadStructure = new TreeMap<>();
		spreadStructure.put(ONE_MILLION, new Spread(ONE, TWO));
		spreadStructure.put(TWO_MILLION, new Spread(THREE, FOUR));
		spreadStructure.put(THREE_MILLION, new Spread(FIVE, SIX));

		FxRate spreadFxRate = ZERO_FX_SPOT_RATE.spread(spreadStructure);
		assertEquals(ZERO_FX_SPOT_RATE.getCurrencyPair(), spreadFxRate.getCurrencyPair());
		assertEquals(ZERO_FX_SPOT_RATE.getTimestamp(), spreadFxRate.getTimestamp());
		assertEquals(ZERO_FX_SPOT_RATE.isStale(), spreadFxRate.isStale());
		assertEquals(ZERO_FX_SPOT_RATE.getFxDate(), spreadFxRate.getFxDate());

		assertEquals(ZERO.subtract(ONE), spreadFxRate.getRate(ONE_MILLION).getBid());
		assertEquals(TWO, spreadFxRate.getRate(ONE_MILLION).getOffer());
		assertEquals(ZERO.subtract(THREE), spreadFxRate.getRate(TWO_MILLION).getBid());
		assertEquals(FOUR, spreadFxRate.getRate(TWO_MILLION).getOffer());
		assertEquals(ZERO.subtract(FIVE), spreadFxRate.getRate(THREE_MILLION).getBid());
		assertEquals(SIX, spreadFxRate.getRate(THREE_MILLION).getOffer());
	}

	@Test
	public void testPartyEquals() {
		assertEquals(FX_SPOT_RATE, FX_SPOT_RATE);
		assertNotEquals(FX_SPOT_RATE, FX_FWD_RATE);
		assertNotEquals(FX_FWD_RATE, FX_SPOT_RATE);
	}

	@Test
	public void testHashCode() {
		assertEquals(-1225828896, FX_SPOT_RATE.hashCode());
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