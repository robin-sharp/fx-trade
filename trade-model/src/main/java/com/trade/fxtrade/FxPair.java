package com.trade.fxtrade;

import java.io.Serializable;

public class FxPair implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String currencyPair;
	private final String baseCurrency;
	private final String quoteCurrency;
	private final int bigFigure;
	private final int decimalPlaces;

	public FxPair(String currencyPair,
				  int bigFigure,
				  int decimalPlaces) {
		this.currencyPair = currencyPair;
		this.baseCurrency = currencyPair.substring(0, 3);
		this.quoteCurrency = currencyPair.substring(3, 6);
		this.bigFigure = bigFigure;
		this.decimalPlaces = decimalPlaces;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public String getQuoteCurrency() {
		return quoteCurrency;
	}

	public int getBigFigure() {
		return bigFigure;
	}

	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	public boolean equals(Object object) {
		if (object instanceof FxPair) {
			return ((FxPair) object).currencyPair.equals(currencyPair);
		}

		return false;
	}
}
