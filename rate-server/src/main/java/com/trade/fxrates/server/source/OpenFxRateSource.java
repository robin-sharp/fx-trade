package com.trade.fxrates.server.source;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trade.BidOffer;
import com.trade.fxtrade.FxDate;
import com.trade.rates.FxRate;
import com.trade.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Class used to load fxrates for a currency using the OpenFxRateSource json standard.
 * <p>
 * All currencies are held against USD.
 *
 * @see "https://docs.openexchangerates.org"
 */
@Slf4j
public class OpenFxRateSource implements FxRateSource {

	private final static int rateScale = 6;
	private final String baseCurrency;
	private final long timestamp;
	private final Map<String, BigDecimal> fxRates = new ConcurrentHashMap<>();
	private final String USD = "USD";
	private final int roundingMode = BigDecimal.ROUND_HALF_EVEN;
	private final BigDecimal ONE = BigDecimal.ONE.setScale(rateScale);

	/**
	 * Load the rates and convert every rates into BigDecimals
	 */
	public OpenFxRateSource(String relativeFileName) {
		final OpenFxRates openFxRates = loadRates(relativeFileName);
		baseCurrency = openFxRates.base;
		timestamp = openFxRates.timestamp;

		openFxRates.rates.forEach((k, v) -> this.fxRates.put(k, new BigDecimal(v)));
	}

	/**
	 * Rates come from the file format from https://docs.openexchangerates.org/docs/latest-json
	 */
	static OpenFxRates loadRates(String relativeFileName) {
		try {
			log.info("Reading fxrates from relativeFileName={}", relativeFileName);
			final URI fileUri = OpenFxRateSource.class.getClassLoader().getResource(relativeFileName).toURI();
			final String content = Files.lines(Paths.get(fileUri)).collect(Collectors.joining(System.lineSeparator()));
			return JsonUtil.fromJson(content, OpenFxRates.class);
		} catch (Exception e) {
			throw new IllegalStateException(format("Failed toload Rates from %s", relativeFileName), e);
		}
	}

	public String getSourceName() {
		return "OpenFx";
	}

	@Override
	public FxRate getQuote(String currencyPair) {
		final String baseCurrency = currencyPair.substring(0, 3);
		final String quoteCurrency = currencyPair.substring(3, 6);

		if (baseCurrency.equals(quoteCurrency)) {
			return newFxRate(currencyPair, ONE);
		}

		final BigDecimal usdQuoteRate = USD.equals(quoteCurrency) ? ONE : getFxRate(quoteCurrency);
		if (usdQuoteRate == null) {
			throw new IllegalStateException(format("%sUSD Quote Rate not available to calculate %s%s rates", quoteCurrency, baseCurrency, quoteCurrency));
		}

		if (USD.equals(baseCurrency)) {
			return newFxRate(currencyPair, usdQuoteRate.setScale(rateScale));
		}

		final BigDecimal usdBaseRate = getFxRate(baseCurrency);
		if (usdBaseRate == null) {
			throw new IllegalStateException(format("%sUSD Base Rate not available to calculate %s%s rates", baseCurrency, baseCurrency, quoteCurrency));
		}

		final BigDecimal crossRate = usdQuoteRate.divide(usdBaseRate, rateScale, roundingMode);
		return newFxRate(currencyPair, crossRate);
	}

	BigDecimal getFxRate(String currency) {
		return fxRates.get(currency);
	}

	FxRate newFxRate(String currencyPair, BigDecimal rate) {
		return new FxRate(currencyPair, FxDate.SPOT, System.currentTimeMillis(), false, new BidOffer(rate, rate));
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Set<String> getQuotedCurrencies() {
		return this.fxRates.keySet();
	}

	@JsonIgnoreProperties
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
	static class OpenFxRates {
		String disclaimer;
		String license;
		String base;
		long timestamp;
		Map<String, String> rates;
	}
}