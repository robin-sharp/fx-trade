package com.trade.fxrate;

import java.math.BigDecimal;
import java.util.TreeMap;

import static com.trade.CurrencyTestData.GBP_USD;
import static com.trade.TradeTestData.*;
import static com.trade.fxtrade.FxDate.ONE_MONTH;
import static com.trade.fxtrade.FxDate.SPOT;

public class TestFxRateData {
	public static TreeMap<BigDecimal, BigDecimal> FX_RATE_STRUCTURE = new TreeMap<>();
	public static boolean STALE = false;

	public final static FxRate FX_SPOT_RATE = new FxRate(GBP_USD, SPOT, STALE, FX_RATE_STRUCTURE);
	public final static FxRate FX_FWD_RATE = new FxRate(GBP_USD, ONE_MONTH, STALE, FX_RATE_STRUCTURE);

	static {
		FX_RATE_STRUCTURE.put(ONE_MILLION, ONE_DOT_FIVE);
		FX_RATE_STRUCTURE.put(TWO_MILLION, ONE_DOT_FOUR);
		FX_RATE_STRUCTURE.put(THREE_MILLION, ONE_DOT_THREE);
		FX_RATE_STRUCTURE.put(FOUR_MILLION, ONE_DOT_TWO);
		FX_RATE_STRUCTURE.put(FIVE_MILLION, ONE_DOT_ONE);
	}
}