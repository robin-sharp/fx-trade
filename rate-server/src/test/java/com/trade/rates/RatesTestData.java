package com.trade.rates;

import com.trade.Spread;
import com.trade.rates.product.RateProduct;
import com.trade.rates.util.Subscription;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.SortedMap;
import java.util.TreeMap;

public class RatesTestData {

	public final static String TOPIC_GBPUSD_SPOT = "GBPUSD-SPOT";
	public final static String TOPIC_EURUSD_SPOT = "EURUSD-SPOT";

	public final static String OIL_SUBSCRIBER = "greta.oiltrader";
	public final static String FX_SUBSCRIBER = "soros.fxtrader";

	public final static Subscription OIL_GBPUSD_SUBSCRIPTION = new Subscription(TOPIC_GBPUSD_SPOT, OIL_SUBSCRIBER);
	public final static Subscription FX_GBPUSD_SUBSCRIPTION = new Subscription(TOPIC_GBPUSD_SPOT, FX_SUBSCRIBER);

	public final static Subscription OIL_EURUSD_SUBSCRIPTION = new Subscription(TOPIC_EURUSD_SPOT, OIL_SUBSCRIBER);
	public final static Subscription FX_EURUSD_SUBSCRIPTION = new Subscription(TOPIC_EURUSD_SPOT, FX_SUBSCRIBER);

	public final static BigDecimal ZERO_SIX_ONE = new BigDecimal("0.000001");
	public final static BigDecimal ZERO_SIX_TWO = new BigDecimal("0.000002");
	public final static BigDecimal ZERO_SIX_FIVE = new BigDecimal("0.000005");

	public final static BigDecimal ONE_SIX = new BigDecimal("1.000000");

	public final static BigDecimal ONE_HUNDRED = new BigDecimal("100");
	public final static BigDecimal ONE_THOUSAND = new BigDecimal("10000");
	public final static BigDecimal TEM_THOUSAND = new BigDecimal("10000");
	public final static BigDecimal ONE_HUNDRED_THOUSAND = new BigDecimal("100000");

	public final static BigDecimal ONE_MILLION = new BigDecimal("1000000");
	public final static BigDecimal TWO_MILLION = new BigDecimal("2000000");
	public final static BigDecimal THREE_MILLION = new BigDecimal("3000000");
	public final static BigDecimal FOUR_MILLION = new BigDecimal("4000000");

	public final static String INTERNAL_PRODUCT_NAME = "Internal";
	public final static String INTERBANK_PRODUCT_NAME = "Interbank";
	public final static String CORPORATE_PRODUCT_NAME = "Corporate";
	public final static String COMPANY_PRODUCT_NAME = "Company";
	public final static String RETAIL_PRODUCT_NAME = "Retail";

	public final static RateProduct INTERNAL_PRODUCT = newRateProduct(INTERNAL_PRODUCT_NAME, 2, ChronoUnit.SECONDS, "1000000", "0.000005", "2000000", "0.000002", "3000000", "0.000001");
	public final static RateProduct INTERBANK_PRODUCT = newRateProduct(INTERBANK_PRODUCT_NAME, 1, ChronoUnit.SECONDS, "1000000", "0.000007", "2000000", "0.000006", "3000000", "0.000005");
	public final static RateProduct CORPORATE_PRODUCT = newRateProduct(CORPORATE_PRODUCT_NAME, 1, ChronoUnit.MINUTES, "10000", "0.004", "200000", "0.003", "3000000", "0.002", "40000000", "0.001");
	public final static RateProduct COMPANY_PRODUCT = newRateProduct(COMPANY_PRODUCT_NAME, 1, ChronoUnit.MINUTES, "1000", "0.005", "10000", "0.004", "100000", "0.003", "1000000", "0.002");
	public final static RateProduct RETAIL_PRODUCT = newRateProduct(RETAIL_PRODUCT_NAME, 1, ChronoUnit.HOURS, "100", "0.05", "1000", "0.04");

	static RateProduct newRateProduct(String productName, int throttleDuration, TemporalUnit throttleTimeUnit, String... spreads) {
		SortedMap<BigDecimal, Spread> spreadStructure = new TreeMap();
		for (int index = 0; index < spreads.length; index += 2) {
			spreadStructure.put(new BigDecimal(spreads[index]), new Spread(new BigDecimal(spreads[index + 1])));
		}
		return new RateProduct(productName, throttleDuration, throttleTimeUnit, spreadStructure);
	}
}
