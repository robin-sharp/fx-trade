package com.trade.fxrates.server.source;

import com.trade.rates.FxRate;

/**
 * Class representing the source for an FxRate.
 */
public interface FxRateSource {

	String getSourceName();

	FxRate getQuote(String currencyPair);
}