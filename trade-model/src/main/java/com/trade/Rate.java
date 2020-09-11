package com.trade;

import java.math.BigDecimal;
import java.util.SortedMap;

public interface Rate {

	String getSource();

	boolean isStale();

	/**
	 * GMT timestamp
	 */
	long getTimestamp();

	/**
	 * Get a BidOffer based on the amount
	 */
	BidOffer getRate(BigDecimal amount);

	Rate spread(SortedMap<BigDecimal, Spread> spreadStructure);
}
