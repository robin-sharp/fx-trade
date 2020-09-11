package com.trade.rates;

import com.trade.BidOffer;

import java.math.BigDecimal;
import java.util.TreeMap;

import static com.trade.CurrencyTestData.GBP_USD;
import static com.trade.TradeTestData.*;
import static com.trade.fwd.FwdDate.ONE_MONTH;
import static com.trade.fxtrade.FxDate.SPOT;

public class TestFxRateData {
	public static TreeMap<BigDecimal, BidOffer> FX_RATE_STRUCTURE = new TreeMap<>();
	public static TreeMap<BigDecimal, BidOffer> ZERO_FX_RATE_STRUCTURE = new TreeMap<>();
	public static long TIMESTAMP = 1;
	public static boolean STALE = false;
	public final static FxRate FX_SPOT_RATE = new FxRate(GBP_USD, SPOT, TIMESTAMP, STALE, FX_RATE_STRUCTURE);
	public final static FxRate ZERO_FX_SPOT_RATE = new FxRate(GBP_USD, SPOT, TIMESTAMP, STALE, ZERO_FX_RATE_STRUCTURE);
	public final static FwdRate FX_FWD_RATE = new FwdRate(GBP_USD, ONE_MONTH, TIMESTAMP, STALE, FX_RATE_STRUCTURE);

	static {
		FX_RATE_STRUCTURE.put(ONE_MILLION, new BidOffer(ONE_DOT_FIVE, ONE_DOT_FIVE));
		FX_RATE_STRUCTURE.put(TWO_MILLION, new BidOffer(ONE_DOT_FOUR, ONE_DOT_FOUR));
		FX_RATE_STRUCTURE.put(THREE_MILLION, new BidOffer(ONE_DOT_THREE, ONE_DOT_THREE));
		FX_RATE_STRUCTURE.put(FOUR_MILLION, new BidOffer(ONE_DOT_TWO, ONE_DOT_TWO));
		FX_RATE_STRUCTURE.put(FIVE_MILLION, new BidOffer(ONE_DOT_ONE, ONE_DOT_ONE));

		ZERO_FX_RATE_STRUCTURE.put(ONE, new BidOffer(ZERO, ZERO));
	}

}