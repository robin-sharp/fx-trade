package com.trade.fxtrade;

import org.junit.jupiter.api.Test;

import static com.trade.CurrencyTestData.*;
import static com.trade.fxtrade.FxTradeTestData.FX_PAIR;
import static com.trade.util.TestUtil.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FxPairTest {

	@Test
	public void testFXPairConstructor() {
		FxPair fxPair = FX_PAIR;

		assertEquals(GBP_USD, fxPair.getCurrencyPair());
		assertEquals(GBP, fxPair.getBaseCurrency());
		assertEquals(USD, fxPair.getQuoteCurrency());
		assertEquals(4, fxPair.getBigFigure());
		assertEquals(6, fxPair.getDecimalPlaces());
	}

	@Test
	public void testFxPairSerializable() {
		assertEquals(assertSerializable(FX_PAIR), FX_PAIR);
	}
}
